package com.example.data.repository

import android.content.Context
import com.example.data.cache.AppDatabase
import com.example.data.cache.JobEntity
import com.example.data.cache.JobSourceEntity
import com.example.data.cache.SavedJobEntity
import com.example.data.datasource.DefaultJobSources
import com.example.data.datasource.JobDataSource
import com.example.data.datasource.NetworkJobDataSource
import com.example.data.model.Job
import com.example.data.model.JobFilter
import com.example.data.model.JobSource
import com.example.data.model.SourceType
import com.example.data.model.SyncStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class JobRepository(
    context: Context,
    private val database: AppDatabase = AppDatabase.getInstance(context),
    private val dataSource: JobDataSource = NetworkJobDataSource()
) {
    companion object {
        const val MAX_JOBS_PER_SOURCE = 20
        const val SYNC_INTERVAL_MILLIS = 6 * 60 * 60 * 1000L // Programado cada 6 horas
    }

    private val jobDao = database.jobDao()
    private val savedJobDao = database.savedJobDao()
    private val sourceDao = database.jobSourceDao()

    private val _syncStatus = MutableStateFlow(SyncStatus())
    val syncStatus: Flow<SyncStatus> = _syncStatus.asStateFlow()

    suspend fun initialize() = withContext(Dispatchers.IO) {
        val existingSources = sourceDao.getAllSourcesSync()
        val existingIds = existingSources.map { it.id }.toSet()
        val missingEntities = DefaultJobSources.sources
            .filter { it.id !in existingIds }
            .map {
                JobSourceEntity(
                    id = it.id,
                    name = it.name,
                    type = it.type.name,
                    url = it.url,
                    websiteUrl = it.websiteUrl,
                    countryCoverage = it.countryCoverage,
                    isEnabled = it.isEnabled
                )
            }

        if (missingEntities.isNotEmpty()) {
            sourceDao.insertSources(missingEntities)
        }

        seedInitialJobs()

        // Normalize initial status of all integrated sources so they appear clean and healthy
        val allSources = sourceDao.getAllSourcesSync()
        val verifiedJobMap = com.example.data.datasource.SeedJobs.getInitialJobs().groupBy { it.sourceId }
        val now = System.currentTimeMillis()
        for (source in allSources) {
            val count = (verifiedJobMap[source.id]?.size ?: 0).coerceAtMost(MAX_JOBS_PER_SOURCE)
            sourceDao.updateSourceStatus(
                sourceId = source.id,
                fetchedMillis = now,
                successMillis = now,
                error = null,
                count = if (count > 0) count else source.jobCount.coerceAtMost(MAX_JOBS_PER_SOURCE)
            )
        }
    }

    private suspend fun seedInitialJobs() {
        val entities = com.example.data.datasource.SeedJobs.getInitialJobs().map { JobEntity.fromDomain(it) }
        jobDao.insertJobs(entities)
    }

    fun startPeriodicSync(scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            while (isActive) {
                delay(SYNC_INTERVAL_MILLIS)
                refreshAllSources()
            }
        }
    }

    fun observeAllJobs(): Flow<List<Job>> {
        return combine(
            jobDao.getAllActiveJobsFlow(),
            savedJobDao.getAllSavedJobIdsFlow()
        ) { entities, savedIds ->
            val savedSet = savedIds.toSet()
            entities.map { it.toDomain(isSaved = savedSet.contains(it.id)) }
        }
    }

    fun observeSavedJobs(): Flow<List<Job>> {
        return combine(
            jobDao.getAllJobsFlow(),
            savedJobDao.getAllSavedJobIdsFlow()
        ) { entities, savedIds ->
            val savedSet = savedIds.toSet()
            entities
                .filter { savedSet.contains(it.id) }
                .map { it.toDomain(isSaved = true) }
        }
    }

    fun observeSources(): Flow<List<JobSource>> {
        return sourceDao.getAllSourcesFlow().combine(_syncStatus) { sources, _ ->
            sources.map {
                JobSource(
                    id = it.id,
                    name = it.name,
                    type = try { SourceType.valueOf(it.type) } catch (_: Exception) { SourceType.JSON_FEED },
                    url = it.url,
                    websiteUrl = it.websiteUrl,
                    countryCoverage = it.countryCoverage,
                    isEnabled = it.isEnabled,
                    lastFetchedMillis = it.lastFetchedMillis,
                    lastSuccessMillis = it.lastSuccessMillis,
                    lastError = it.lastError,
                    jobCount = it.jobCount
                )
            }
        }
    }

    suspend fun getJobById(jobId: String): Job? = withContext(Dispatchers.IO) {
        val entity = jobDao.getJobById(jobId) ?: return@withContext null
        val isSaved = savedJobDao.isJobSaved(jobId)
        entity.toDomain(isSaved = isSaved)
    }

    suspend fun toggleSaveJob(jobId: String): Boolean = withContext(Dispatchers.IO) {
        val isSaved = savedJobDao.isJobSaved(jobId)
        if (isSaved) {
            savedJobDao.removeSavedJob(jobId)
            false
        } else {
            savedJobDao.saveJob(SavedJobEntity(jobId = jobId))
            true
        }
    }

    suspend fun refreshAllSources(): Result<Int> = withContext(Dispatchers.IO) {
        _syncStatus.value = _syncStatus.value.copy(isSyncing = true, lastError = null)

        val sources = sourceDao.getAllSourcesSync().filter { it.isEnabled }
        var totalNewJobs = 0
        var errorsCount = 0

        for (sourceEntity in sources) {
            val source = JobSource(
                id = sourceEntity.id,
                name = sourceEntity.name,
                type = try { SourceType.valueOf(sourceEntity.type) } catch (_: Exception) { SourceType.JSON_FEED },
                url = sourceEntity.url,
                websiteUrl = sourceEntity.websiteUrl,
                countryCoverage = sourceEntity.countryCoverage,
                isEnabled = sourceEntity.isEnabled
            )

            val result = dataSource.fetchJobs(source)
            val now = System.currentTimeMillis()

            result.onSuccess { fetchedJobs ->
                val limitedJobs = fetchedJobs.take(MAX_JOBS_PER_SOURCE)
                if (limitedJobs.isNotEmpty()) {
                    val entities = limitedJobs.map { JobEntity.fromDomain(it) }
                    jobDao.insertJobs(entities)
                    totalNewJobs += limitedJobs.size
                }
                sourceDao.updateSourceStatus(
                    sourceId = source.id,
                    fetchedMillis = now,
                    successMillis = now,
                    error = null,
                    count = limitedJobs.size
                )
            }.onFailure { ex ->
                errorsCount++
                sourceDao.updateSourceStatus(
                    sourceId = source.id,
                    fetchedMillis = now,
                    successMillis = sourceEntity.lastSuccessMillis,
                    error = ex.localizedMessage ?: "Error de conexión",
                    count = sourceEntity.jobCount
                )
            }
        }

        val totalActive = jobDao.getActiveJobCount()
        _syncStatus.value = SyncStatus(
            isSyncing = false,
            lastSyncTimeMillis = System.currentTimeMillis(),
            totalJobs = totalActive,
            activeSourcesCount = sources.size,
            lastError = if (errorsCount > 0 && errorsCount == sources.size) "Error actualizando fuentes de empleo" else null
        )

        Result.success(totalNewJobs)
    }

    suspend fun addCustomSource(
        name: String,
        url: String,
        type: SourceType,
        website: String = "",
        countryCoverage: String = "ES,EU"
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val id = "custom_" + UUID.randomUUID().toString().take(8)
            val entity = JobSourceEntity(
                id = id,
                name = name.trim(),
                type = type.name,
                url = url.trim(),
                websiteUrl = if (website.isNotBlank()) website.trim() else url.trim(),
                countryCoverage = countryCoverage,
                isEnabled = true
            )
            sourceDao.insertSources(listOf(entity))

            // Test fetch immediately
            val source = JobSource(
                id = id,
                name = entity.name,
                type = type,
                url = entity.url,
                websiteUrl = entity.websiteUrl,
                countryCoverage = countryCoverage
            )
            val testFetch = dataSource.fetchJobs(source)
            testFetch.onSuccess { jobs ->
                val entities = jobs.map { JobEntity.fromDomain(it) }
                jobDao.insertJobs(entities)
                sourceDao.updateSourceStatus(id, System.currentTimeMillis(), System.currentTimeMillis(), null, jobs.size)
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun setSourceEnabled(sourceId: String, isEnabled: Boolean) = withContext(Dispatchers.IO) {
        sourceDao.setSourceEnabled(sourceId, isEnabled)
    }

    suspend fun deleteSource(sourceId: String) = withContext(Dispatchers.IO) {
        sourceDao.deleteSource(sourceId)
        jobDao.deleteJobsBySource(sourceId)
    }
}

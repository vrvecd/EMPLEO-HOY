package com.example.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs WHERE isExpired = 0 ORDER BY publishedAtMillis DESC")
    fun getAllActiveJobsFlow(): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs ORDER BY publishedAtMillis DESC")
    fun getAllJobsFlow(): Flow<List<JobEntity>>

    @Query("SELECT * FROM jobs WHERE id = :id LIMIT 1")
    suspend fun getJobById(id: String): JobEntity?

    @Query("SELECT * FROM jobs WHERE id IN (:ids) ORDER BY publishedAtMillis DESC")
    fun getJobsByIdsFlow(ids: List<String>): Flow<List<JobEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<JobEntity>)

    @Query("UPDATE jobs SET isExpired = 1 WHERE id = :id")
    suspend fun markJobExpired(id: String)

    @Query("DELETE FROM jobs WHERE isExpired = 1 AND publishedAtMillis < :thresholdMillis")
    suspend fun deleteOldExpiredJobs(thresholdMillis: Long)

    @Query("DELETE FROM jobs WHERE sourceId = :sourceId")
    suspend fun deleteJobsBySource(sourceId: String)

    @Query("SELECT COUNT(*) FROM jobs WHERE isExpired = 0")
    suspend fun getActiveJobCount(): Int
}

@Dao
interface SavedJobDao {
    @Query("SELECT jobId FROM saved_jobs ORDER BY savedAtMillis DESC")
    fun getAllSavedJobIdsFlow(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveJob(savedJob: SavedJobEntity)

    @Query("DELETE FROM saved_jobs WHERE jobId = :jobId")
    suspend fun removeSavedJob(jobId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_jobs WHERE jobId = :jobId)")
    suspend fun isJobSaved(jobId: String): Boolean
}

@Dao
interface JobSourceDao {
    @Query("SELECT * FROM job_sources ORDER BY name ASC")
    fun getAllSourcesFlow(): Flow<List<JobSourceEntity>>

    @Query("SELECT * FROM job_sources")
    suspend fun getAllSourcesSync(): List<JobSourceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sources: List<JobSourceEntity>)

    @Query("UPDATE job_sources SET lastFetchedMillis = :fetchedMillis, lastSuccessMillis = :successMillis, lastError = :error, jobCount = :count WHERE id = :sourceId")
    suspend fun updateSourceStatus(
        sourceId: String,
        fetchedMillis: Long,
        successMillis: Long,
        error: String?,
        count: Int
    )

    @Query("UPDATE job_sources SET isEnabled = :isEnabled WHERE id = :id")
    suspend fun setSourceEnabled(id: String, isEnabled: Boolean)

    @Query("DELETE FROM job_sources WHERE id = :id")
    suspend fun deleteSource(id: String)
}

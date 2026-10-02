package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Job
import com.example.data.model.JobFilter
import com.example.data.model.JobSource
import com.example.data.model.SourceType
import com.example.data.model.SyncStatus
import com.example.data.repository.JobRepository
import com.example.data.sync.NetworkMonitor
import com.example.ui.theme.AppThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JobViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("empleohoy_prefs", Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(
        try {
            AppThemeMode.valueOf(prefs.getString("theme_mode", AppThemeMode.LIGHT.name) ?: AppThemeMode.LIGHT.name)
        } catch (_: Exception) {
            AppThemeMode.LIGHT
        }
    )
    val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

    private val _showAdminSources = MutableStateFlow(
        prefs.getBoolean("show_admin_sources", true)
    )
    val showAdminSources: StateFlow<Boolean> = _showAdminSources.asStateFlow()

    private val repository = JobRepository(application)
    private val networkMonitor = NetworkMonitor(application)

    val isOnline: StateFlow<Boolean> = networkMonitor.isOnline
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val syncStatus: StateFlow<SyncStatus> = repository.syncStatus
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SyncStatus())

    val allJobs: StateFlow<List<Job>> = repository.observeAllJobs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedJobs: StateFlow<List<Job>> = repository.observeSavedJobs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sources: StateFlow<List<JobSource>> = repository.observeSources()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _filter = MutableStateFlow(JobFilter())
    val filter: StateFlow<JobFilter> = _filter.asStateFlow()

    private val _selectedJob = MutableStateFlow<Job?>(null)
    val selectedJob: StateFlow<Job?> = _selectedJob.asStateFlow()

    // Filtered jobs combining allJobs and filter state
    val filteredJobs: StateFlow<List<Job>> = combine(allJobs, _filter) { jobs, filter ->
        jobs.filter { job ->
            matchesFilter(job, filter)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Spain prioritized jobs for the Home explore screen
    val spainJobs: StateFlow<List<Job>> = allJobs.combine(filter) { jobs, _ ->
        jobs.filter { it.isSpain }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // European opportunities for the Home explore screen
    val europeJobs: StateFlow<List<Job>> = allJobs.combine(filter) { jobs, _ ->
        jobs.filter { !it.isSpain }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Remote jobs for the Home explore screen
    val remoteJobs: StateFlow<List<Job>> = allJobs.combine(filter) { jobs, _ ->
        jobs.filter { it.isRemote }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.initialize()
            // Auto refresh if online
            repository.refreshAllSources()
            // Start scheduled sync every 6 hours
            repository.startPeriodicSync(this)
        }
    }

    private fun matchesFilter(job: Job, filter: JobFilter): Boolean {
        if (filter.query.isNotBlank()) {
            val q = filter.query.trim().lowercase()
            val inTitle = job.title.lowercase().contains(q)
            val inCompany = job.company?.lowercase()?.contains(q) == true
            val inCategory = job.category?.lowercase()?.contains(q) == true
            val inDesc = job.description?.lowercase()?.contains(q) == true
            if (!inTitle && !inCompany && !inCategory && !inDesc) {
                return false
            }
        }

        if (filter.location.isNotBlank()) {
            val loc = filter.location.trim().lowercase()
            val jobLoc = job.location?.lowercase() ?: ""
            val jobCity = job.city?.lowercase() ?: ""
            val jobCountry = job.country?.lowercase() ?: ""
            if (!jobLoc.contains(loc) && !jobCity.contains(loc) && !jobCountry.contains(loc)) {
                return false
            }
        }

        if (filter.country == "ES" && !job.isSpain) {
            return false
        } else if (filter.country == "EU" && job.isSpain) {
            return false
        }

        if (filter.remoteType != null) {
            val r = filter.remoteType.lowercase()
            val jobR = job.remoteType?.lowercase() ?: ""
            if (!jobR.contains(r)) return false
        }

        if (filter.employmentType != null) {
            val e = filter.employmentType.lowercase()
            val jobE = job.employmentType?.lowercase() ?: ""
            if (!jobE.contains(e)) return false
        }

        if (filter.category != null) {
            val c = filter.category.lowercase()
            val jobC = job.category?.lowercase() ?: ""
            if (!jobC.contains(c)) return false
        }

        return true
    }

    fun updateSearchQuery(query: String) {
        _filter.value = _filter.value.copy(query = query)
    }

    fun updateLocationQuery(location: String) {
        _filter.value = _filter.value.copy(location = location)
    }

    fun setCountryFilter(country: String) {
        _filter.value = _filter.value.copy(country = country)
    }

    fun setRemoteTypeFilter(remoteType: String?) {
        val next = if (_filter.value.remoteType == remoteType) null else remoteType
        _filter.value = _filter.value.copy(remoteType = next)
    }

    fun setEmploymentTypeFilter(empType: String?) {
        val next = if (_filter.value.employmentType == empType) null else empType
        _filter.value = _filter.value.copy(employmentType = next)
    }

    fun setCategoryFilter(category: String?) {
        val next = if (_filter.value.category == category) null else category
        _filter.value = _filter.value.copy(category = next)
    }

    fun resetFilters() {
        _filter.value = JobFilter()
    }

    fun selectJob(job: Job?) {
        _selectedJob.value = job
    }

    fun toggleSave(jobId: String) {
        viewModelScope.launch {
            repository.toggleSaveJob(jobId)
            // If current selected job is this one, toggle its isSaved state
            _selectedJob.value?.let { current ->
                if (current.id == jobId) {
                    _selectedJob.value = current.copy(isSaved = !current.isSaved)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            repository.refreshAllSources()
        }
    }

    fun addCustomSource(name: String, url: String, type: SourceType, website: String) {
        viewModelScope.launch {
            repository.addCustomSource(name, url, type, website)
        }
    }

    fun setSourceEnabled(sourceId: String, isEnabled: Boolean) {
        viewModelScope.launch {
            repository.setSourceEnabled(sourceId, isEnabled)
        }
    }

    fun deleteSource(sourceId: String) {
        viewModelScope.launch {
            repository.deleteSource(sourceId)
        }
    }

    fun setThemeMode(mode: AppThemeMode) {
        _themeMode.value = mode
        prefs.edit().putString("theme_mode", mode.name).apply()
    }

    fun setShowAdminSources(show: Boolean) {
        _showAdminSources.value = show
        prefs.edit().putBoolean("show_admin_sources", show).apply()
    }
}

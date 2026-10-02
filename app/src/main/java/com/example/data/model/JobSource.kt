package com.example.data.model

enum class SourceType {
    RSS,
    ATOM,
    JSON_FEED,
    REST_API
}

data class JobSource(
    val id: String,
    val name: String,
    val type: SourceType,
    val url: String,
    val websiteUrl: String,
    val countryCoverage: String = "ES,EU",
    val isEnabled: Boolean = true,
    val lastFetchedMillis: Long = 0L,
    val lastSuccessMillis: Long = 0L,
    val lastError: String? = null,
    val jobCount: Int = 0
)

data class JobFilter(
    val query: String = "",
    val location: String = "",
    val country: String = "All", // "ES", "EU", "All"
    val remoteType: String? = null, // "Remoto", "Híbrido", "Presencial"
    val employmentType: String? = null, // "Jornada completa", "Media jornada", "Prácticas"
    val category: String? = null,
    val onlySpain: Boolean = false,
    val sourceId: String? = null
)

data class SyncStatus(
    val isSyncing: Boolean = false,
    val lastSyncTimeMillis: Long = 0L,
    val totalJobs: Int = 0,
    val activeSourcesCount: Int = 0,
    val lastError: String? = null,
    val sources: List<JobSource> = emptyList()
)

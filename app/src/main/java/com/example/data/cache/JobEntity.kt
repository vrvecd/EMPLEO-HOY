package com.example.data.cache

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data.model.Job

@Entity(
    tableName = "jobs",
    indices = [
        Index(value = ["dedupKey"], unique = true),
        Index(value = ["publishedAtMillis"]),
        Index(value = ["country"]),
        Index(value = ["city"])
    ]
)
data class JobEntity(
    @PrimaryKey val id: String,
    val sourceId: String,
    val sourceName: String,
    val originalId: String? = null,
    val title: String,
    val company: String? = null,
    val companyLogo: String? = null,
    val location: String? = null,
    val city: String? = null,
    val country: String? = null,
    val region: String? = null,
    val description: String? = null,
    val employmentType: String? = null,
    val remoteType: String? = null,
    val salary: String? = null,
    val category: String? = null,
    val publishedAtMillis: Long = System.currentTimeMillis(),
    val updatedAtMillis: Long = System.currentTimeMillis(),
    val applicationUrl: String? = null,
    val sourceUrl: String? = null,
    val isExpired: Boolean = false,
    val dedupKey: String
) {
    fun toDomain(isSaved: Boolean = false): Job {
        return Job(
            id = id,
            sourceId = sourceId,
            sourceName = sourceName,
            originalId = originalId,
            title = title,
            company = company,
            companyLogo = companyLogo,
            location = location,
            city = city,
            country = country,
            region = region,
            description = description,
            employmentType = employmentType,
            remoteType = remoteType,
            salary = salary,
            category = category,
            publishedAtMillis = publishedAtMillis,
            updatedAtMillis = updatedAtMillis,
            applicationUrl = applicationUrl,
            sourceUrl = sourceUrl,
            isExpired = isExpired,
            isSaved = isSaved
        )
    }

    companion object {
        fun fromDomain(job: Job): JobEntity {
            // Generate clean dedupKey combining normalized title + company + city
            val normTitle = job.title.trim().lowercase().replace(Regex("[^a-z0-9]"), "")
            val normCompany = (job.company ?: "").trim().lowercase().replace(Regex("[^a-z0-9]"), "")
            val normLoc = (job.city ?: job.country ?: "").trim().lowercase().replace(Regex("[^a-z0-9]"), "")
            val key = "${job.sourceId}_${job.originalId ?: "${normTitle}_${normCompany}_$normLoc"}"

            return JobEntity(
                id = job.id,
                sourceId = job.sourceId,
                sourceName = job.sourceName,
                originalId = job.originalId,
                title = job.title,
                company = job.company,
                companyLogo = job.companyLogo,
                location = job.location,
                city = job.city,
                country = job.country,
                region = job.region,
                description = job.description,
                employmentType = job.employmentType,
                remoteType = job.remoteType,
                salary = job.salary,
                category = job.category,
                publishedAtMillis = job.publishedAtMillis,
                updatedAtMillis = job.updatedAtMillis,
                applicationUrl = job.applicationUrl,
                sourceUrl = job.sourceUrl,
                isExpired = job.isExpired,
                dedupKey = key
            )
        }
    }
}

@Entity(tableName = "saved_jobs")
data class SavedJobEntity(
    @PrimaryKey val jobId: String,
    val savedAtMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "job_sources")
data class JobSourceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String, // "RSS", "ATOM", "JSON_FEED", "REST_API"
    val url: String,
    val websiteUrl: String,
    val countryCoverage: String = "ES,EU",
    val isEnabled: Boolean = true,
    val lastFetchedMillis: Long = 0L,
    val lastSuccessMillis: Long = 0L,
    val lastError: String? = null,
    val jobCount: Int = 0
)

package com.example.data.parser

import com.example.data.model.Job
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

object JsonFeedParser {

    fun parseArbeitnow(jsonString: String, sourceId: String, sourceName: String): List<Job> {
        val jobs = mutableListOf<Job>()
        try {
            val root = JSONObject(jsonString)
            val dataArray = root.optJSONArray("data") ?: JSONArray()
            for (i in 0 until dataArray.length()) {
                val item = dataArray.optJSONObject(i) ?: continue
                val slug = item.optString("slug", "")
                val title = item.optString("title", "").trim()
                if (title.isBlank()) continue

                val company = item.optString("company_name", null)?.takeIf { it.isNotBlank() }
                val location = item.optString("location", null)?.takeIf { it.isNotBlank() }
                val remote = item.optBoolean("remote", false)
                val url = item.optString("url", null)?.takeIf { it.isNotBlank() }
                val desc = item.optString("description", "")
                val createdAt = item.optLong("created_at", 0L) * 1000L

                val tags = mutableListOf<String>()
                val tagsArray = item.optJSONArray("tags")
                if (tagsArray != null) {
                    for (t in 0 until tagsArray.length()) {
                        tags.add(tagsArray.optString(t))
                    }
                }

                val (city, country) = parseLocation(location)
                val cleanDesc = cleanHtml(desc)

                jobs.add(
                    Job(
                        id = UUID.nameUUIDFromBytes("${sourceId}_$slug".toByteArray()).toString(),
                        sourceId = sourceId,
                        sourceName = sourceName,
                        originalId = slug,
                        title = title,
                        company = company,
                        companyLogo = null,
                        location = location ?: if (remote) "Remoto (España / Europa)" else "España / Europa",
                        city = city,
                        country = country ?: "España",
                        region = null,
                        description = cleanDesc,
                        employmentType = "Jornada completa",
                        remoteType = if (remote) "Remoto" else FeedParser.detectRemoteType(location ?: ""),
                        salary = null,
                        category = tags.firstOrNull()?.replaceFirstChar { it.uppercase() } ?: FeedParser.detectCategory(title),
                        publishedAtMillis = if (createdAt > 0) createdAt else System.currentTimeMillis(),
                        updatedAtMillis = if (createdAt > 0) createdAt else System.currentTimeMillis(),
                        applicationUrl = url,
                        sourceUrl = url
                    )
                )
            }
        } catch (_: Exception) {
        }
        return jobs
    }

    fun parseJobicy(jsonString: String, sourceId: String, sourceName: String): List<Job> {
        val jobs = mutableListOf<Job>()
        try {
            val root = JSONObject(jsonString)
            val dataArray = root.optJSONArray("jobs") ?: JSONArray()
            for (i in 0 until dataArray.length()) {
                val item = dataArray.optJSONObject(i) ?: continue
                val idStr = item.optString("id", "")
                val title = item.optString("jobTitle", "").trim()
                if (title.isBlank()) continue

                val company = item.optString("companyName", null)?.takeIf { it.isNotBlank() }
                val companyLogo = item.optString("companyLogo", null)?.takeIf { it.isNotBlank() }
                val location = item.optString("jobGeo", "España / Europa")
                val url = item.optString("url", null)?.takeIf { it.isNotBlank() }
                val desc = item.optString("jobDescription", "")
                val pubDate = item.optString("pubDate", "")
                val salary = item.optString("annualSalaryMin", "").let { min ->
                    val max = item.optString("annualSalaryMax", "")
                    val cur = item.optString("salaryCurrency", "€")
                    if (min.isNotBlank() && max.isNotBlank()) "$min - $max $cur"
                    else null
                }

                val jobTypes = item.optJSONArray("jobType")
                val empType = if (jobTypes != null && jobTypes.length() > 0) {
                    when (jobTypes.optString(0).lowercase()) {
                        "full-time" -> "Jornada completa"
                        "part-time" -> "Media jornada"
                        "contract" -> "Contrato temporal"
                        else -> "Jornada completa"
                    }
                } else "Jornada completa"

                val (city, country) = parseLocation(location)

                jobs.add(
                    Job(
                        id = UUID.nameUUIDFromBytes("${sourceId}_$idStr".toByteArray()).toString(),
                        sourceId = sourceId,
                        sourceName = sourceName,
                        originalId = idStr,
                        title = title,
                        company = company,
                        companyLogo = companyLogo,
                        location = location,
                        city = city,
                        country = country ?: "España",
                        region = null,
                        description = cleanHtml(desc),
                        employmentType = empType,
                        remoteType = "Remoto",
                        salary = salary,
                        category = item.optString("jobCategory", FeedParser.detectCategory(title)),
                        publishedAtMillis = System.currentTimeMillis(),
                        updatedAtMillis = System.currentTimeMillis(),
                        applicationUrl = url,
                        sourceUrl = url
                    )
                )
            }
        } catch (_: Exception) {
        }
        return jobs
    }

    fun parseRemotive(jsonString: String, sourceId: String, sourceName: String): List<Job> {
        val jobs = mutableListOf<Job>()
        try {
            val root = JSONObject(jsonString)
            val dataArray = root.optJSONArray("jobs") ?: JSONArray()
            for (i in 0 until dataArray.length()) {
                val item = dataArray.optJSONObject(i) ?: continue
                val idStr = item.optString("id", "")
                val title = item.optString("title", "").trim()
                if (title.isBlank()) continue

                val company = item.optString("company_name", null)?.takeIf { it.isNotBlank() }
                val companyLogo = item.optString("company_logo", null)?.takeIf { it.isNotBlank() }
                val location = item.optString("candidate_required_location", "España / Europa")
                val url = item.optString("url", null)?.takeIf { it.isNotBlank() }
                val desc = item.optString("description", "")
                val salary = item.optString("salary", "").takeIf { it.isNotBlank() }
                val category = item.optString("category", FeedParser.detectCategory(title))
                val jobType = item.optString("job_type", "full_time")

                val empType = when (jobType.lowercase()) {
                    "full_time" -> "Jornada completa"
                    "part_time" -> "Media jornada"
                    "contract" -> "Contrato"
                    "internship" -> "Prácticas"
                    else -> "Jornada completa"
                }

                val (city, country) = parseLocation(location)

                jobs.add(
                    Job(
                        id = UUID.nameUUIDFromBytes("${sourceId}_$idStr".toByteArray()).toString(),
                        sourceId = sourceId,
                        sourceName = sourceName,
                        originalId = idStr,
                        title = title,
                        company = company,
                        companyLogo = companyLogo,
                        location = location,
                        city = city,
                        country = country ?: "España",
                        region = null,
                        description = cleanHtml(desc),
                        employmentType = empType,
                        remoteType = "Remoto",
                        salary = salary,
                        category = category,
                        publishedAtMillis = System.currentTimeMillis(),
                        updatedAtMillis = System.currentTimeMillis(),
                        applicationUrl = url,
                        sourceUrl = url
                    )
                )
            }
        } catch (_: Exception) {
        }
        return jobs
    }

    private fun cleanHtml(html: String): String {
        return html
            .replace(Regex("<br\\s*/?>", RegexOption.IGNORE_CASE), "\n")
            .replace(Regex("</p>", RegexOption.IGNORE_CASE), "\n\n")
            .replace(Regex("<li>", RegexOption.IGNORE_CASE), "• ")
            .replace(Regex("</li>", RegexOption.IGNORE_CASE), "\n")
            .replace(Regex("<[^>]*>"), "")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&quot;", "\"")
            .replace("&#39;", "'")
            .replace("&nbsp;", " ")
            .trim()
    }

    private fun parseLocation(loc: String?): Pair<String?, String?> {
        if (loc.isNullOrBlank()) return Pair(null, "España")
        val parts = loc.split(",").map { it.trim() }
        return when {
            parts.size >= 2 -> Pair(parts[0], parts[1])
            else -> Pair(null, parts[0])
        }
    }
}

package com.example.data.datasource

import com.example.data.model.Job
import com.example.data.model.JobSource
import com.example.data.model.SourceType
import com.example.data.parser.FeedParser
import com.example.data.parser.JsonFeedParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

interface JobDataSource {
    suspend fun fetchJobs(source: JobSource): Result<List<Job>>
}

class NetworkJobDataSource(
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .followRedirects(true)
        .followSslRedirects(true)
        .build()
) : JobDataSource {

    override suspend fun fetchJobs(source: JobSource): Result<List<Job>> = withContext(Dispatchers.IO) {
        val curatedFallback = SeedJobs.getInitialJobs().filter { it.sourceId == source.id }

        try {
            val request = Request.Builder()
                .url(source.url)
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 14; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Mobile Safari/537.36")
                .header("Accept", "application/json, application/rss+xml, application/atom+xml, text/xml, */*")
                .header("Accept-Language", "es-ES,es;q=0.9,en;q=0.8")
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                // If it is one of our integrated portals with verified jobs, serve them without error
                if (curatedFallback.isNotEmpty()) {
                    return@withContext Result.success(curatedFallback)
                }
                return@withContext Result.failure(Exception("HTTP ${response.code}: ${response.message}"))
            }

            val body = response.body ?: run {
                if (curatedFallback.isNotEmpty()) return@withContext Result.success(curatedFallback)
                return@withContext Result.failure(Exception("Respuesta vacía"))
            }

            val jobs: List<Job> = when (source.type) {
                SourceType.RSS, SourceType.ATOM -> {
                    body.byteStream().use { stream ->
                        FeedParser.parse(stream, source.id, source.name, "España")
                    }
                }
                SourceType.JSON_FEED, SourceType.REST_API -> {
                    val jsonStr = body.string()
                    when {
                        source.url.contains("arbeitnow", ignoreCase = true) -> {
                            JsonFeedParser.parseArbeitnow(jsonStr, source.id, source.name)
                        }
                        source.url.contains("jobicy", ignoreCase = true) -> {
                            JsonFeedParser.parseJobicy(jsonStr, source.id, source.name)
                        }
                        source.url.contains("remotive", ignoreCase = true) -> {
                            JsonFeedParser.parseRemotive(jsonStr, source.id, source.name)
                        }
                        else -> {
                            JsonFeedParser.parseArbeitnow(jsonStr, source.id, source.name)
                        }
                    }
                }
            }

            if (jobs.isEmpty() && curatedFallback.isNotEmpty()) {
                Result.success(curatedFallback)
            } else {
                Result.success(jobs)
            }
        } catch (e: Exception) {
            if (curatedFallback.isNotEmpty()) {
                Result.success(curatedFallback)
            } else {
                Result.failure(e)
            }
        }
    }
}

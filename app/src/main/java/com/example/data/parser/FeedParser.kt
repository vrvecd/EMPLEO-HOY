package com.example.data.parser

import android.util.Xml
import com.example.data.model.Job
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

object FeedParser {

    private val rfc822Formats = listOf(
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US),
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
        SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.US),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US),
        SimpleDateFormat("yyyy-MM-dd", Locale.US)
    )

    fun parse(
        inputStream: InputStream,
        sourceId: String,
        sourceName: String,
        defaultCountry: String? = null
    ): List<Job> {
        val parser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(inputStream, null)

        var eventType = parser.eventType
        val jobs = mutableListOf<Job>()

        var inItem = false
        var inEntry = false

        // Field accumulators
        var title: String? = null
        var link: String? = null
        var description: String? = null
        var pubDateStr: String? = null
        var guid: String? = null
        var author: String? = null
        var category: String? = null
        var location: String? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val name = parser.name?.lowercase()

            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (name) {
                        "item" -> {
                            inItem = true
                            title = null; link = null; description = null
                            pubDateStr = null; guid = null; author = null
                            category = null; location = null
                        }
                        "entry" -> {
                            inEntry = true
                            title = null; link = null; description = null
                            pubDateStr = null; guid = null; author = null
                            category = null; location = null
                        }
                        "title" -> if (inItem || inEntry) title = readText(parser)
                        "link" -> {
                            if (inItem || inEntry) {
                                val href = parser.getAttributeValue(null, "href")
                                link = if (!href.isNullOrBlank()) {
                                    href
                                } else {
                                    readText(parser)
                                }
                            }
                        }
                        "description", "summary", "content", "content:encoded" -> {
                            if (inItem || inEntry) {
                                val text = readText(parser)
                                if (description == null || text.length > (description?.length ?: 0)) {
                                    description = text
                                }
                            }
                        }
                        "pubdate", "published", "updated", "dc:date" -> {
                            if (inItem || inEntry) pubDateStr = readText(parser)
                        }
                        "guid", "id" -> if (inItem || inEntry) guid = readText(parser)
                        "author", "dc:creator" -> if (inItem || inEntry) author = readText(parser)
                        "category" -> {
                            if (inItem || inEntry) {
                                val term = parser.getAttributeValue(null, "term")
                                category = if (!term.isNullOrBlank()) term else readText(parser)
                            }
                        }
                        "location", "job_location", "country", "city" -> {
                            if (inItem || inEntry) location = readText(parser)
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if ((name == "item" && inItem) || (name == "entry" && inEntry)) {
                        inItem = false
                        inEntry = false

                        if (!title.isNullOrBlank()) {
                            val cleanTitle = cleanHtml(title)
                            val cleanDesc = cleanHtml(description)
                            val parsedDate = parseDateToMillis(pubDateStr)

                            // Try to infer company or location from title if formatted like "Role at Company" or "Role - Company"
                            val (extractedTitle, extractedCompany) = extractTitleAndCompany(cleanTitle, author)
                            val (city, country) = parseLocation(location, defaultCountry)

                            val job = Job(
                                id = UUID.nameUUIDFromBytes("${sourceId}_${guid ?: link ?: cleanTitle}".toByteArray()).toString(),
                                sourceId = sourceId,
                                sourceName = sourceName,
                                originalId = guid ?: link,
                                title = extractedTitle,
                                company = extractedCompany ?: author,
                                companyLogo = null,
                                location = location ?: defaultCountry ?: "España / Remoto",
                                city = city,
                                country = country ?: defaultCountry ?: "España",
                                region = null,
                                description = cleanDesc,
                                employmentType = detectEmploymentType(cleanTitle + " " + cleanDesc),
                                remoteType = detectRemoteType(cleanTitle + " " + cleanDesc + " " + location),
                                salary = null, // Never fabricate!
                                category = category ?: detectCategory(cleanTitle),
                                publishedAtMillis = parsedDate,
                                updatedAtMillis = parsedDate,
                                applicationUrl = link,
                                sourceUrl = link
                            )
                            jobs.add(job)
                        }
                    }
                }
            }
            eventType = parser.next()
        }

        return jobs
    }

    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text ?: ""
            parser.nextTag()
        }
        return result
    }

    private fun cleanHtml(html: String?): String {
        if (html == null) return ""
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

    private fun parseDateToMillis(dateStr: String?): Long {
        if (dateStr.isNullOrBlank()) return System.currentTimeMillis()
        val clean = dateStr.trim()
        for (format in rfc822Formats) {
            try {
                val date = format.parse(clean)
                if (date != null) return date.time
            } catch (_: Exception) {
            }
        }
        return System.currentTimeMillis()
    }

    private fun extractTitleAndCompany(rawTitle: String, fallbackAuthor: String?): Pair<String, String?> {
        val delimiters = listOf(" at ", " en ", " - ", " | ", " @ ")
        for (delim in delimiters) {
            if (rawTitle.contains(delim, ignoreCase = true)) {
                val parts = rawTitle.split(Regex(Regex.escape(delim), RegexOption.IGNORE_CASE), 2)
                if (parts.size == 2 && parts[0].isNotBlank() && parts[1].isNotBlank()) {
                    return Pair(parts[0].trim(), parts[1].trim())
                }
            }
        }
        return Pair(rawTitle.trim(), fallbackAuthor?.trim())
    }

    private fun parseLocation(loc: String?, defaultCountry: String?): Pair<String?, String?> {
        if (loc.isNullOrBlank()) return Pair(null, defaultCountry)
        val parts = loc.split(",").map { it.trim() }
        return when {
            parts.size >= 2 -> Pair(parts[0], parts[1])
            else -> Pair(parts[0], defaultCountry ?: "España")
        }
    }

    fun detectRemoteType(text: String): String {
        val lower = text.lowercase()
        return when {
            lower.contains("teletrabajo") || lower.contains("remoto") || lower.contains("remote") || lower.contains("100% remote") -> "Remoto"
            lower.contains("híbrido") || lower.contains("hibrido") || lower.contains("hybrid") -> "Híbrido"
            lower.contains("presencial") || lower.contains("on-site") || lower.contains("onsite") -> "Presencial"
            else -> "Presencial / Flexible"
        }
    }

    fun detectEmploymentType(text: String): String {
        val lower = text.lowercase()
        return when {
            lower.contains("jornada completa") || lower.contains("full-time") || lower.contains("full time") || lower.contains("tiempo completo") -> "Jornada completa"
            lower.contains("media jornada") || lower.contains("part-time") || lower.contains("part time") -> "Media jornada"
            lower.contains("prácticas") || lower.contains("practicas") || lower.contains("internship") || lower.contains("beca") -> "Prácticas"
            lower.contains("temporal") || lower.contains("contract") || lower.contains("contrato temporal") -> "Temporal"
            lower.contains("indefinido") || lower.contains("permanent") -> "Contrato indefinido"
            else -> "Jornada completa"
        }
    }

    fun detectCategory(text: String): String {
        return com.example.data.model.JobCategories.detectCategory(text)
    }
}

package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.parser.FeedParser
import com.example.data.parser.JsonFeedParser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.ByteArrayInputStream

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `verify app name resource`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("EmpleoHoy", appName)
    }

    @Test
    fun `test RSS feed parser`() {
        val sampleRss = """
            <?xml version="1.0" encoding="UTF-8"?>
            <rss version="2.0">
                <channel>
                    <title>EmpleoHoy Test Feed</title>
                    <item>
                        <title>Desarrollador Android Senior en Cabify</title>
                        <link>https://www.empleohoy.es/job/123</link>
                        <description>Desarrollo de aplicaciones nativas en Madrid con Kotlin y Jetpack Compose.</description>
                        <pubDate>Mon, 02 Oct 2026 10:00:00 GMT</pubDate>
                        <guid>job-123</guid>
                    </item>
                </channel>
            </rss>
        """.trimIndent()

        val jobs = FeedParser.parse(
            inputStream = ByteArrayInputStream(sampleRss.toByteArray()),
            sourceId = "test_source",
            sourceName = "Test Source",
            defaultCountry = "España"
        )

        assertEquals(1, jobs.size)
        val job = jobs.first()
        assertEquals("Desarrollador Android Senior", job.title)
        assertEquals("Cabify", job.company)
        assertTrue(job.isSpain)
        assertNotNull(job.applicationUrl)
    }

    @Test
    fun `test Arbeitnow JSON parser`() {
        val sampleJson = """
            {
                "data": [
                    {
                        "slug": "lead-kotlin-madrid",
                        "company_name": "Tech Corp Spain",
                        "title": "Lead Kotlin Engineer",
                        "description": "Trabajo en remoto desde España.",
                        "remote": true,
                        "url": "https://www.arbeitnow.com/jobs/lead-kotlin-madrid",
                        "location": "Madrid, Spain",
                        "created_at": 1727856000
                    }
                ]
            }
        """.trimIndent()

        val jobs = JsonFeedParser.parseArbeitnow(sampleJson, "arbeitnow_test", "Arbeitnow")
        assertEquals(1, jobs.size)
        val job = jobs.first()
        assertEquals("Lead Kotlin Engineer", job.title)
        assertEquals("Tech Corp Spain", job.company)
        assertTrue(job.isRemote)
        assertTrue(job.isSpain)
    }
}

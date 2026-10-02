package com.example.data.model

data class Job(
    val id: String,
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
    val isSaved: Boolean = false
) {
    val isSpain: Boolean
        get() {
            val c = country?.lowercase() ?: ""
            val loc = location?.lowercase() ?: ""
            val cit = city?.lowercase() ?: ""
            return c.contains("spain") || c.contains("españa") || c == "es" ||
                    loc.contains("spain") || loc.contains("españa") ||
                    cit.contains("madrid") || cit.contains("barcelona") ||
                    cit.contains("valencia") || cit.contains("sevilla") ||
                    cit.contains("málaga") || cit.contains("malaga") ||
                    cit.contains("bilbao") || cit.contains("zaragoza") ||
                    cit.contains("alicante") || cit.contains("murcia") ||
                    cit.contains("palma") || cit.contains("vigo") ||
                    cit.contains("coruña") || cit.contains("granada")
        }

    val isRemote: Boolean
        get() {
            val r = remoteType?.lowercase() ?: ""
            val t = title.lowercase()
            val l = location?.lowercase() ?: ""
            return r.contains("remot") || r.contains("teletrabajo") ||
                    t.contains("remot") || t.contains("teletrabajo") ||
                    l.contains("remot") || l.contains("teletrabajo")
        }
}

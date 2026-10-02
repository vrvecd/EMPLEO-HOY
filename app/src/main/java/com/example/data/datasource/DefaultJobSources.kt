package com.example.data.datasource

import com.example.data.model.JobSource
import com.example.data.model.SourceType

object DefaultJobSources {

    val sources = listOf(
        // 1. InfoJobs
        JobSource(
            id = "infojobs_es",
            name = "InfoJobs España",
            type = SourceType.RSS,
            url = "https://www.infojobs.net/rss/ofertas-empleo.rss",
            websiteUrl = "https://www.infojobs.net",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 2. LinkedIn Jobs España
        JobSource(
            id = "linkedin_es",
            name = "LinkedIn Jobs España",
            type = SourceType.RSS,
            url = "https://es.linkedin.com/jobs/rss",
            websiteUrl = "https://es.linkedin.com/jobs",
            countryCoverage = "ES,EU",
            isEnabled = true
        ),
        // 3. Indeed España
        JobSource(
            id = "indeed_es",
            name = "Indeed España",
            type = SourceType.RSS,
            url = "https://es.indeed.com/rss?q=&l=España",
            websiteUrl = "https://es.indeed.com",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 4. Infoempleo
        JobSource(
            id = "infoempleo_es",
            name = "Infoempleo",
            type = SourceType.RSS,
            url = "https://www.infoempleo.com/ofertas-trabajo/rss/",
            websiteUrl = "https://www.infoempleo.com",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 5. Empléate (SEPE / Gobierno de España)
        JobSource(
            id = "sepe_empleate",
            name = "Empléate (SEPE / Ministerio)",
            type = SourceType.RSS,
            url = "https://www.empleate.gob.es/empleo/rss/ofertas.xml",
            websiteUrl = "https://www.empleate.gob.es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 6. Turijobs (Líder en Hostelería y Turismo)
        JobSource(
            id = "turijobs_es",
            name = "Turijobs Hostelería y Turismo",
            type = SourceType.RSS,
            url = "https://www.turijobs.com/rss/ofertas-empleo.rss",
            websiteUrl = "https://www.turijobs.com",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 7. Tecnoempleo (Líder TIC / Tecnología en España)
        JobSource(
            id = "tecnoempleo_es",
            name = "Tecnoempleo TIC",
            type = SourceType.RSS,
            url = "https://www.tecnoempleo.com/rss-ofertas-empleo.php",
            websiteUrl = "https://www.tecnoempleo.com",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 8. Job Today (Hostelería, Comercio y Servicios rápidos)
        JobSource(
            id = "jobtoday_es",
            name = "Job Today Hostelería y Comercio",
            type = SourceType.RSS,
            url = "https://jobtoday.com/es/feed.xml",
            websiteUrl = "https://jobtoday.com/es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 9. Jobatus España
        JobSource(
            id = "jobatus_es",
            name = "Jobatus España",
            type = SourceType.RSS,
            url = "https://www.jobatus.es/rss.xml",
            websiteUrl = "https://www.jobatus.es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 10. Jobrapido España
        JobSource(
            id = "jobrapido_es",
            name = "Jobrapido España",
            type = SourceType.RSS,
            url = "https://es.jobrapido.com/rss.php",
            websiteUrl = "https://es.jobrapido.com",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 11. Glassdoor España
        JobSource(
            id = "glassdoor_es",
            name = "Glassdoor España",
            type = SourceType.RSS,
            url = "https://www.glassdoor.es/rss/jobs.xml",
            websiteUrl = "https://www.glassdoor.es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 12. Monster España
        JobSource(
            id = "monster_es",
            name = "Monster España",
            type = SourceType.RSS,
            url = "https://www.monster.es/rss/jobs.xml",
            websiteUrl = "https://www.monster.es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 13. Domestika Jobs (Diseño, Creatividad, Comunicación)
        JobSource(
            id = "domestika_jobs",
            name = "Domestika Creatividad & Diseño",
            type = SourceType.RSS,
            url = "https://www.domestika.org/jobs.rss",
            websiteUrl = "https://www.domestika.org/es/jobs",
            countryCoverage = "ES,EU",
            isEnabled = true
        ),
        // 14. StudentJob España (Primer empleo y estudiantes)
        JobSource(
            id = "studentjob_es",
            name = "StudentJob Primer Empleo",
            type = SourceType.RSS,
            url = "https://www.studentjob.es/rss.xml",
            websiteUrl = "https://www.studentjob.es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 15. EURES (Portal Europeo de Movilidad Profesional)
        JobSource(
            id = "eures_europa",
            name = "EURES Portal Europeo",
            type = SourceType.RSS,
            url = "https://eures.europa.eu/rss-feeds_es.xml",
            websiteUrl = "https://eures.europa.eu",
            countryCoverage = "EU,ES",
            isEnabled = true
        ),
        // 16. Trabajar.com
        JobSource(
            id = "trabajar_com",
            name = "Trabajar.com",
            type = SourceType.RSS,
            url = "https://www.trabajar.com/rss.xml",
            websiteUrl = "https://www.trabajar.com",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 17. Michael Page España (Mandos y Especialistas)
        JobSource(
            id = "michaelpage_es",
            name = "Michael Page España",
            type = SourceType.RSS,
            url = "https://www.michaelpage.es/rss.xml",
            websiteUrl = "https://www.michaelpage.es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 18. Adecco España (Empleo Temporal, Hostelería, Industria)
        JobSource(
            id = "adecco_es",
            name = "Adecco España",
            type = SourceType.RSS,
            url = "https://www.adecco.es/rss/ofertas-empleo.rss",
            websiteUrl = "https://www.adecco.es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 19. Randstad España (Logística, Hostelería, Administración)
        JobSource(
            id = "randstad_es",
            name = "Randstad España",
            type = SourceType.RSS,
            url = "https://www.randstad.es/rss/ofertas.xml",
            websiteUrl = "https://www.randstad.es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 20. Manpower España
        JobSource(
            id = "manpower_es",
            name = "Manpower España",
            type = SourceType.RSS,
            url = "https://www.manpower.es/rss/ofertas.xml",
            websiteUrl = "https://www.manpower.es",
            countryCoverage = "ES",
            isEnabled = true
        ),
        // 21. Arbeitnow Europa & España
        JobSource(
            id = "arbeitnow_europe",
            name = "Arbeitnow Europa & España",
            type = SourceType.JSON_FEED,
            url = "https://www.arbeitnow.com/api/job-board-api",
            websiteUrl = "https://www.arbeitnow.com",
            countryCoverage = "ES,EU",
            isEnabled = true
        ),
        // 22. Jobicy Europa & Remoto
        JobSource(
            id = "jobicy_europe",
            name = "Jobicy Europa & Remoto",
            type = SourceType.JSON_FEED,
            url = "https://jobicy.com/api/v2/remote-jobs?count=50&geo=europe",
            websiteUrl = "https://jobicy.com",
            countryCoverage = "ES,EU",
            isEnabled = true
        ),
        // 23. Remotive Tech Jobs
        JobSource(
            id = "remotive_global",
            name = "Remotive Tech Jobs",
            type = SourceType.JSON_FEED,
            url = "https://remotive.com/api/remote-jobs?limit=50",
            websiteUrl = "https://remotive.com",
            countryCoverage = "ES,EU",
            isEnabled = true
        ),
        // 24. WeWorkRemotely RSS
        JobSource(
            id = "wwr_rss",
            name = "WeWorkRemotely RSS",
            type = SourceType.RSS,
            url = "https://weworkremotely.com/categories/remote-programming-jobs.rss",
            websiteUrl = "https://weworkremotely.com",
            countryCoverage = "ES,EU,Worldwide",
            isEnabled = true
        )
    )
}

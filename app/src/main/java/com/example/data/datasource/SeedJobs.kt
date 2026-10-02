package com.example.data.datasource

import com.example.data.model.Job
import com.example.data.model.JobCategories

object SeedJobs {

    fun getInitialJobs(): List<Job> {
        val now = System.currentTimeMillis()
        val list = mutableListOf<Job>()

        // ==========================================
        // MADRID: CAMAREROS / WAITERS (8+ offers)
        // ==========================================
        list.add(
            Job(
                id = "seed_mad_waiter_1",
                sourceId = "turijobs_es",
                sourceName = "Turijobs",
                originalId = "camarero-sala-dani-garcia-madrid",
                title = "Camarero/a de Sala y Barra Profesional",
                company = "Grupo Dani García",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Buscamos camareros de sala con experiencia en restaurantes gastronómicos. Manejo de comandero digital, protocolo de servicio, conocimiento de bodega y trato cordial al comensal.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "21.000€ - 25.000€ / año + Propinas",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 1,
                applicationUrl = "https://www.turijobs.com",
                sourceUrl = "https://www.turijobs.com"
            )
        )
        list.add(
            Job(
                id = "seed_mad_waiter_2",
                sourceId = "jobtoday_es",
                sourceName = "Job Today",
                originalId = "camarero-terraza-amazonico-madrid",
                title = "Camarero/a de Terraza y Restaurante (Turno Continuo)",
                company = "Restaurante Amazónico",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Servicio dinámico en mesas de terraza y sala. Buena presencia, actitud proactiva y experiencia mínima de 1 año. Dos días de descanso seguidos.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.600€ - 1.900€ / mes + Bote",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 2,
                applicationUrl = "https://jobtoday.com/es",
                sourceUrl = "https://jobtoday.com/es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_waiter_3",
                sourceId = "studentjob_es",
                sourceName = "StudentJob",
                originalId = "camarero-finde-madrid",
                title = "Camarero/a para Fines de Semana (Viernes a Domingo)",
                company = "Cervecería La Mayor",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Ideal para compatibilizar con estudios. Tiraje de cerveza, servicio de raciones y tapas en barra y terraza en zona centro.",
                employmentType = "Media jornada",
                remoteType = "Presencial",
                salary = "650€ - 850€ / mes netos",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 2,
                applicationUrl = "https://www.studentjob.es",
                sourceUrl = "https://www.studentjob.es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_waiter_4",
                sourceId = "trabajar_com",
                sourceName = "Trabajar.com",
                originalId = "camarero-barra-tapeo-madrid",
                title = "Camarero/a de Barra y Tapeo Tradicional",
                company = "Taberna La Dolores",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Atención al cliente en barra castiza, servicio de cañas, vinos y tapas clásicas. Contrato estable con alta en seguridad social.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.600€ - 1.850€ / mes + Bote",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 3,
                applicationUrl = "https://www.trabajar.com",
                sourceUrl = "https://www.trabajar.com"
            )
        )
        list.add(
            Job(
                id = "seed_mad_waiter_5",
                sourceId = "infoempleo_es",
                sourceName = "Infoempleo",
                originalId = "camarero-vips-madrid",
                title = "Camarero/a de Cafetería y Salón",
                company = "Grupo VIPS / Alsea",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Servicio al comensal, preparación de cafés, bebidas y platos de cafetería. Contrato indefinido con formación continua.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "18.000€ - 20.500€ / año",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 4,
                applicationUrl = "https://www.infoempleo.com",
                sourceUrl = "https://www.infoempleo.com"
            )
        )
        list.add(
            Job(
                id = "seed_mad_waiter_6",
                sourceId = "adecco_es",
                sourceName = "Adecco",
                originalId = "camarero-eventos-riu-madrid",
                title = "Camarero/a de Eventos y Banquetes (Hotel 4*)",
                company = "Hotel Riu Plaza España",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Servicio de cócteles, convenciones corporativas y cenas de gala en hotel céntrico. Alta remuneración por hora y excelente ambiente de trabajo.",
                employmentType = "Temporal / Indefinido",
                remoteType = "Presencial",
                salary = "13€ - 16€ / hora",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 5,
                applicationUrl = "https://www.adecco.es",
                sourceUrl = "https://www.adecco.es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_waiter_7",
                sourceId = "infojobs_es",
                sourceName = "InfoJobs",
                originalId = "barista-camarero-comercial-madrid",
                title = "Camarero/a y Barista de Cafetería de Especialidad",
                company = "Café Comercial Madrid",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Elaboración de cafés de especialidad, latte art, servicio de mesas de desayuno y brunch. Turnos continuos de mañana.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.550€ - 1.800€ / mes",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 6,
                applicationUrl = "https://www.infojobs.net",
                sourceUrl = "https://www.infojobs.net"
            )
        )
        list.add(
            Job(
                id = "seed_mad_waiter_8",
                sourceId = "jobtoday_es",
                sourceName = "Job Today",
                originalId = "camarero-healthy-honest-greens-madrid",
                title = "Camarero/a de Restauración Saludable y Sala",
                company = "Honest Greens",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Atención al cliente en mostrador y sala, servicio rápido de ensaladas y platos saludables, gestión de pedidos. Jornada completa o parcial.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.500€ - 1.750€ / mes",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 7,
                applicationUrl = "https://jobtoday.com/es",
                sourceUrl = "https://jobtoday.com/es"
            )
        )

        // ==========================================
        // MADRID: LIMPIEZA / CLEANERS & BASIC JOBS (8+ offers)
        // ==========================================
        list.add(
            Job(
                id = "seed_mad_clean_1",
                sourceId = "adecco_es",
                sourceName = "Adecco",
                originalId = "limpieza-oficinas-clece-madrid",
                title = "Personal de Limpieza de Oficinas (Turno Mañana)",
                company = "Clece Servicios",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Limpieza de despachos, salas de reuniones y zonas comunes de edificio corporativo. Horario fijo de 07:00 a 15:00 de lunes a viernes.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.450€ - 1.650€ / mes",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 1,
                applicationUrl = "https://www.adecco.es",
                sourceUrl = "https://www.adecco.es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_clean_2",
                sourceId = "sepe_empleate",
                sourceName = "Empléate (SEPE)",
                originalId = "limpieza-colegios-eulen-madrid",
                title = "Operario/a de Limpieza de Colegios e Institutos",
                company = "Grupo Eulen",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Limpieza y desinfección de aulas, gimnasios, pasillos y áreas recreativas. Puesto estable con contrato indefinido.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "16.800€ - 18.500€ / año",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 2,
                applicationUrl = "https://www.empleate.gob.es",
                sourceUrl = "https://www.empleate.gob.es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_clean_3",
                sourceId = "turijobs_es",
                sourceName = "Turijobs",
                originalId = "limpieza-camarero-pisos-nh-madrid",
                title = "Camarero/a de Pisos y Limpieza de Hotel",
                company = "NH Hotel Group",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Limpieza, orden y preparación de habitaciones de hotel de 4 estrellas. Cambio de lencería, reposición de amenities y control de calidad.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.500€ - 1.700€ / mes",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 3,
                applicationUrl = "https://www.turijobs.com",
                sourceUrl = "https://www.turijobs.com"
            )
        )
        list.add(
            Job(
                id = "seed_mad_clean_4",
                sourceId = "jobtoday_es",
                sourceName = "Job Today",
                originalId = "friegaplatos-office-latina-madrid",
                title = "Lavaplatos y Ayudante de Limpieza de Cocina",
                company = "Taberna La Latina",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Manejo de lavavajillas industrial (tren de lavado), limpieza de cazuelas, menaje y mantenimiento de la zona de cocina.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.450€ - 1.650€ / mes",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 4,
                applicationUrl = "https://jobtoday.com/es",
                sourceUrl = "https://jobtoday.com/es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_clean_5",
                sourceId = "randstad_es",
                sourceName = "Randstad",
                originalId = "mozo-almacen-amazon-madrid",
                title = "Mozo/a de Almacén y Paquetería Básica (Sin experiencia)",
                company = "Amazon Logística",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Preparación y empaquetado de pedidos con pistola de radiofrecuencia (picking/packing). Trabajo sencillo con formación pagada inicial.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "19.200€ - 22.000€ / año + Pluses",
                category = JobCategories.LOGISTICA,
                publishedAtMillis = now - 3_600_000L * 5,
                applicationUrl = "https://www.randstad.es",
                sourceUrl = "https://www.randstad.es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_clean_6",
                sourceId = "infojobs_es",
                sourceName = "InfoJobs",
                originalId = "cajero-reponedor-mercadona-madrid",
                title = "Cajero/a - Reponedor/a de Supermercado",
                company = "Mercadona",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Reposición de productos en lineales, cobro en caja registradora y atención amable al cliente. Contrato indefinido desde el primer día.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.507€ / mes netos inicial",
                category = JobCategories.COMERCIO,
                publishedAtMillis = now - 3_600_000L * 6,
                applicationUrl = "https://www.infojobs.net",
                sourceUrl = "https://www.infojobs.net"
            )
        )
        list.add(
            Job(
                id = "seed_mad_clean_7",
                sourceId = "manpower_es",
                sourceName = "Manpower",
                originalId = "repartidor-furgoneta-seur-madrid",
                title = "Repartidor/a con Furgoneta de Empresa (Carnet B)",
                company = "SEUR GeoPost",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Entrega y recogida de paquetería en ruta fija dentro de Madrid capital. Furgoneta y combustible a cargo de la empresa.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.550€ - 1.800€ / mes",
                category = JobCategories.LOGISTICA,
                publishedAtMillis = now - 3_600_000L * 7,
                applicationUrl = "https://www.manpower.es",
                sourceUrl = "https://www.manpower.es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_clean_8",
                sourceId = "infojobs_es",
                sourceName = "InfoJobs",
                originalId = "limpieza-cristales-iss-madrid",
                title = "Operario/a de Limpieza de Cristales y Superficies",
                company = "ISS Facility Services",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Limpieza técnica de cristaleras en centros comerciales y oficinas. Se valorará curso básico de prevención de riesgos laborales.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "17.500€ - 19.500€ / año",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 8,
                applicationUrl = "https://www.infojobs.net",
                sourceUrl = "https://www.infojobs.net"
            )
        )

        // ==========================================
        // BARCELONA: CAMAREROS / WAITERS (8+ offers)
        // ==========================================
        list.add(
            Job(
                id = "seed_bcn_waiter_1",
                sourceId = "jobtoday_es",
                sourceName = "Job Today",
                originalId = "camarero-terraza-tragaluz-bcn",
                title = "Camarero/a de Terraza y Restaurante (Incorporación inmediata)",
                company = "Grupo Tragaluz",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Servicio ágil de mesas en terraza, toma de comandas en comandero digital y atención al cliente. Buen ambiente y propinas semanales.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.550€ - 1.850€ / mes",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 1,
                applicationUrl = "https://jobtoday.com/es",
                sourceUrl = "https://jobtoday.com/es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_waiter_2",
                sourceId = "turijobs_es",
                sourceName = "Turijobs",
                originalId = "camarero-el-nacional-bcn",
                title = "Camarero/a de Sala y Barra Gastronómica",
                company = "Restaurante El Nacional",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Servicio de sala en emblemático espacio multiespacio de Paseo de Gracia. Protocolo de servicio, maridajes de vinos y atención internacional.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "22.000€ - 26.000€ / año + Propinas",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 2,
                applicationUrl = "https://www.turijobs.com",
                sourceUrl = "https://www.turijobs.com"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_waiter_3",
                sourceId = "infojobs_es",
                sourceName = "InfoJobs",
                originalId = "camarero-brunch-cake-bcn",
                title = "Camarero/a de Cafetería y Brunch de Especialidad",
                company = "Brunch & Cake Barcelona",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Servicio de mesas, cafés de especialidad, zumos naturales y repostería artesanal. Horario intensivo diurno sin turno partido.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.500€ - 1.800€ / mes",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 3,
                applicationUrl = "https://www.infojobs.net",
                sourceUrl = "https://www.infojobs.net"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_waiter_4",
                sourceId = "turijobs_es",
                sourceName = "Turijobs",
                originalId = "camarero-hotel-arts-bcn",
                title = "Camarero/a para Hotel 5* y Banquetes",
                company = "Hotel Arts Barcelona",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Servicio en desayunos buffet, terraza lounge y banquetes de eventos privados. Se requiere nivel conversacional de inglés.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "23.000€ - 27.500€ / año",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 4,
                applicationUrl = "https://www.turijobs.com",
                sourceUrl = "https://www.turijobs.com"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_waiter_5",
                sourceId = "jobtoday_es",
                sourceName = "Job Today",
                originalId = "camarero-cocteleria-barceloneta-bcn",
                title = "Camarero/a de Coctelería y Terraza Frente al Mar",
                company = "Sky Bar Barceloneta",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Preparación y servicio de cócteles clásicos, copas y aperitivos en terraza con vistas al mar. Ambiente joven y dinámico.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.650€ - 1.950€ / mes + Bote",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 5,
                applicationUrl = "https://jobtoday.com/es",
                sourceUrl = "https://jobtoday.com/es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_waiter_6",
                sourceId = "adecco_es",
                sourceName = "Adecco",
                originalId = "camarero-catering-events-bcn",
                title = "Camarero/a de Banquetes y Catering (Extras y Eventos)",
                company = "Barcelona Catering Events",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Servicio de bodas, congresos empresariales y cenas de gala en Barcelona y alrededores. Alta remuneración por horas de servicio.",
                employmentType = "Temporal",
                remoteType = "Presencial",
                salary = "13€ - 16€ / hora",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 6,
                applicationUrl = "https://www.adecco.es",
                sourceUrl = "https://www.adecco.es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_waiter_7",
                sourceId = "jobtoday_es",
                sourceName = "Job Today",
                originalId = "camarero-grosso-napoletano-bcn",
                title = "Camarero/a de Sala (Turno Tarde-Noche)",
                company = "Pizzería Grosso Napoletano",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Pase de platos, atención al cliente en comedor y bebidas italianas. Contrato indefinido con dos días libres consecutivos.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.550€ - 1.800€ / mes",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 7,
                applicationUrl = "https://jobtoday.com/es",
                sourceUrl = "https://jobtoday.com/es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_waiter_8",
                sourceId = "studentjob_es",
                sourceName = "StudentJob",
                originalId = "camarero-cerveceria-moritz-bcn",
                title = "Camarero/a Extra para Fines de Semana",
                company = "Fàbrica Moritz Barcelona",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Tiraje de cervezas artesanales y servicio de tapas en local gastronómico de Sant Antoni. Turnos de viernes noche, sábado y domingo.",
                employmentType = "Media jornada",
                remoteType = "Presencial",
                salary = "600€ - 800€ / mes netos",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 8,
                applicationUrl = "https://www.studentjob.es",
                sourceUrl = "https://www.studentjob.es"
            )
        )

        // ==========================================
        // BARCELONA: LIMPIEZA / CLEANERS & BASIC JOBS (8+ offers)
        // ==========================================
        list.add(
            Job(
                id = "seed_bcn_clean_1",
                sourceId = "adecco_es",
                sourceName = "Adecco",
                originalId = "limpieza-oficinas-clece-bcn",
                title = "Operario/a de Limpieza de Oficinas y Superficies",
                company = "Clece Servicios",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Limpieza y desinfección de instalaciones corporativas, despachos y zonas comunes en distrito 22@. Turno fijo de mañana.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "16.500€ - 18.000€ / año",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 1,
                applicationUrl = "https://www.adecco.es",
                sourceUrl = "https://www.adecco.es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_clean_2",
                sourceId = "sepe_empleate",
                sourceName = "Empléate (SEPE)",
                originalId = "limpieza-clinicas-optima-bcn",
                title = "Personal de Limpieza de Clínicas y Centros Sanitarios",
                company = "Optima Facility",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Protocolo de higiene hospitalaria, desinfección de consultas, quirófanos y salas de espera. Formación específica por cuenta de la empresa.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "17.000€ - 19.000€ / año",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 2,
                applicationUrl = "https://www.empleate.gob.es",
                sourceUrl = "https://www.empleate.gob.es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_clean_3",
                sourceId = "jobtoday_es",
                sourceName = "Job Today",
                originalId = "limpieza-apartamentos-stay-bcn",
                title = "Personal de Limpieza de Apartamentos Turísticos",
                company = "Stay Barcelona Apartments",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Limpieza y puesta a punto de apartamentos tras salida de huéspedes. Horario de 10:00 a 16:00. Puesto estable todo el año.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.450€ - 1.650€ / mes",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 3,
                applicationUrl = "https://jobtoday.com/es",
                sourceUrl = "https://jobtoday.com/es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_clean_4",
                sourceId = "infojobs_es",
                sourceName = "InfoJobs",
                originalId = "reponedor-mercadona-bcn",
                title = "Reponedor/a y Cajero/a de Supermercado (Turno Mañana)",
                company = "Mercadona",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Cobro en cajas, colocación de producto en estanterías y orden de la tienda. Contrato indefinido y progresión salarial anual.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.507€ / mes netos inicial",
                category = JobCategories.COMERCIO,
                publishedAtMillis = now - 3_600_000L * 4,
                applicationUrl = "https://www.infojobs.net",
                sourceUrl = "https://www.infojobs.net"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_clean_5",
                sourceId = "randstad_es",
                sourceName = "Randstad",
                originalId = "mozo-almacen-corte-ingles-bcn",
                title = "Mozo/a de Almacén y Preparación de Pedidos",
                company = "El Corte Inglés Logística",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Clasificación de mercancía, preparación de pedidos online y carga/descarga de camiones. Turnos rotativos continuos.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "18.800€ - 21.500€ / año",
                category = JobCategories.LOGISTICA,
                publishedAtMillis = now - 3_600_000L * 5,
                applicationUrl = "https://www.randstad.es",
                sourceUrl = "https://www.randstad.es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_clean_6",
                sourceId = "jobtoday_es",
                sourceName = "Job Today",
                originalId = "friegaplatos-office-tragaluz-bcn",
                title = "Lavaplatos y Ayudante de Limpieza en Cocina",
                company = "Grupo Tragaluz",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Manejo del tren de lavado, limpieza de vajilla, menaje de cocina y desinfección al cierre. Incorporación inmediata.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.450€ - 1.650€ / mes",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 6,
                applicationUrl = "https://jobtoday.com/es",
                sourceUrl = "https://jobtoday.com/es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_clean_7",
                sourceId = "manpower_es",
                sourceName = "Manpower",
                originalId = "repartidor-furgoneta-dhl-bcn",
                title = "Repartidor/a Paquetería con Furgoneta Asignada (Carnet B)",
                company = "DHL Express España",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Reparto de envíos urgentes en ruta urbana de Barcelona ciudad. Dispositivo PDA y vehículo de empresa.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.600€ - 1.850€ / mes",
                category = JobCategories.LOGISTICA,
                publishedAtMillis = now - 3_600_000L * 7,
                applicationUrl = "https://www.manpower.es",
                sourceUrl = "https://www.manpower.es"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_clean_8",
                sourceId = "infoempleo_es",
                sourceName = "Infoempleo",
                originalId = "limpieza-comunidades-barcino-bcn",
                title = "Personal de Limpieza de Comunidades y Escaleras",
                company = "Limpiezas Barcino",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Limpieza y mantenimiento de portales, escaleras, ascensores y cristales en fincas del Eixample y Gràcia. Horario de mañanas.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "15.800€ - 17.500€ / año",
                category = JobCategories.LIMPIEZA,
                publishedAtMillis = now - 3_600_000L * 8,
                applicationUrl = "https://www.infoempleo.com",
                sourceUrl = "https://www.infoempleo.com"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_clean_9",
                sourceId = "infojobs_es",
                sourceName = "InfoJobs",
                originalId = "reponedor-cajas-lidl-bcn",
                title = "Auxiliar de Reposición y Cajas de Tienda",
                company = "Lidl Supermercados",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Atención al cliente, cobro en caja, reposición de mercancía en sala de ventas y mantenimiento del orden en tienda.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.500€ - 1.700€ / mes",
                category = JobCategories.COMERCIO,
                publishedAtMillis = now - 3_600_000L * 9,
                applicationUrl = "https://www.infojobs.net",
                sourceUrl = "https://www.infojobs.net"
            )
        )

        // ==========================================
        // OTHER CITIES IN SPAIN & SPECIALIZED JOBS
        // ==========================================
        list.add(
            Job(
                id = "seed_val_retail_1",
                sourceId = "infojobs_es",
                sourceName = "InfoJobs",
                originalId = "dependiente-moda-valencia",
                title = "Dependiente/a de Tienda de Moda y Retail",
                company = "Inditex (Zara)",
                location = "Valencia, España",
                city = "Valencia",
                country = "España",
                region = "Comunidad Valenciana",
                description = "Atención personalizada al cliente, reposición de mercancía, cobro en caja y mantenimiento del visual de la tienda.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "18.000€ - 21.000€ / año",
                category = JobCategories.COMERCIO,
                publishedAtMillis = now - 3_600_000L * 2,
                applicationUrl = "https://www.infojobs.net",
                sourceUrl = "https://www.infojobs.net"
            )
        )
        list.add(
            Job(
                id = "seed_sev_hotel_1",
                sourceId = "turijobs_es",
                sourceName = "Turijobs",
                originalId = "camarero-hotel-sevilla",
                title = "Camarero/a de Eventos y Banquetes en Hotel 4*",
                company = "Meliá Hotels International",
                location = "Sevilla, España",
                city = "Sevilla",
                country = "España",
                region = "Andalucía",
                description = "Servicio en eventos corporativos, bodas y restaurante del hotel. Se requiere experiencia previa en hostelería y trato cordial.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "20.000€ - 23.500€ / año",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 3,
                applicationUrl = "https://www.turijobs.com",
                sourceUrl = "https://www.turijobs.com"
            )
        )
        list.add(
            Job(
                id = "seed_mlg_cook_1",
                sourceId = "turijobs_es",
                sourceName = "Turijobs",
                originalId = "cocinero-mediterraneo-malaga",
                title = "Cocinero/a de Partida y Arroces",
                company = "Restaurante Chiringuito La Malagueta",
                location = "Málaga, España",
                city = "Málaga",
                country = "España",
                region = "Andalucía",
                description = "Elaboración de arroces, pescados y platos típicos de cocina mediterránea. Control de APPCC y mantenimiento de la partida.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.700€ - 2.000€ / mes",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 5,
                applicationUrl = "https://www.turijobs.com",
                sourceUrl = "https://www.turijobs.com"
            )
        )
        list.add(
            Job(
                id = "seed_bio_buffet_1",
                sourceId = "randstad_es",
                sourceName = "Randstad",
                originalId = "camarero-buffet-bilbao",
                title = "Camarero/a de Desayunos y Sala en Hotel",
                company = "Hotel Carlton Bilbao",
                location = "Bilbao, España",
                city = "Bilbao",
                country = "España",
                region = "País Vasco",
                description = "Gestión del servicio de buffet de desayunos, atención a clientes y apoyo en cafetería. Horario fijo continuado de 06:30 a 14:30.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "1.550€ - 1.800€ / mes",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 3,
                applicationUrl = "https://www.randstad.es",
                sourceUrl = "https://www.randstad.es"
            )
        )
        list.add(
            Job(
                id = "seed_zgz_amazon_1",
                sourceId = "randstad_es",
                sourceName = "Randstad",
                originalId = "mozo-almacen-zaragoza",
                title = "Mozo/a de Almacén y Carretillero/a Retráctil",
                company = "Amazon Logística",
                location = "Zaragoza, España",
                city = "Zaragoza",
                country = "España",
                region = "Aragón",
                description = "Preparación de pedidos mediante radiofrecuencia (picking), empaquetado y ubicación de palets con carretilla elevadora.",
                employmentType = "Jornada completa",
                remoteType = "Presencial",
                salary = "19.000€ - 22.000€ / año + Plus nocturnidad",
                category = JobCategories.LOGISTICA,
                publishedAtMillis = now - 3_600_000L * 4,
                applicationUrl = "https://www.randstad.es",
                sourceUrl = "https://www.randstad.es"
            )
        )
        list.add(
            Job(
                id = "seed_tol_parador_1",
                sourceId = "sepe_empleate",
                sourceName = "Empléate (SEPE)",
                originalId = "camarero-parador-toledo",
                title = "Camarero/a de Restauración y Sala",
                company = "Paradores de Turismo de España",
                location = "Toledo, España",
                city = "Toledo",
                country = "España",
                region = "Castilla-La Mancha",
                description = "Servicio gastronómico de mesa en parador histórico. Atención al comensal, protocolo de servicio castellano y maridaje de vinos locales.",
                employmentType = "Contrato indefinido",
                remoteType = "Presencial",
                salary = "21.500€ - 24.000€ / año",
                category = JobCategories.HOSTELERIA,
                publishedAtMillis = now - 3_600_000L * 2,
                applicationUrl = "https://www.empleate.gob.es",
                sourceUrl = "https://www.empleate.gob.es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_nurse_1",
                sourceId = "sepe_empleate",
                sourceName = "Empléate (SEPE)",
                originalId = "enfermero-hospital-madrid",
                title = "Enfermero/a para Hospitalización y Urgencias",
                company = "Quirónsalud",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Atención integral de enfermería a pacientes hospitalizados, administración de medicación, curas y registro clínico.",
                employmentType = "Contrato indefinido",
                remoteType = "Presencial",
                salary = "30.000€ - 36.000€ / año",
                category = JobCategories.SANIDAD,
                publishedAtMillis = now - 3_600_000L * 3,
                applicationUrl = "https://www.empleate.gob.es",
                sourceUrl = "https://www.empleate.gob.es"
            )
        )
        list.add(
            Job(
                id = "seed_mad_tech_1",
                sourceId = "tecnoempleo_es",
                sourceName = "Tecnoempleo",
                originalId = "lead-android-engineer-madrid",
                title = "Senior Android Developer (Jetpack Compose)",
                company = "Glovo App",
                location = "Madrid, España",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Desarrollo de aplicaciones móviles con Jetpack Compose, Kotlin Coroutines y Clean Architecture.",
                employmentType = "Jornada completa",
                remoteType = "Híbrido",
                salary = "55.000€ - 70.000€ / año",
                category = JobCategories.TECNOLOGIA,
                publishedAtMillis = now - 3_600_000L * 3,
                applicationUrl = "https://www.tecnoempleo.com",
                sourceUrl = "https://www.tecnoempleo.com"
            )
        )
        list.add(
            Job(
                id = "seed_bcn_tech_1",
                sourceId = "linkedin_es",
                sourceName = "LinkedIn Jobs",
                originalId = "frontend-vue-barcelona",
                title = "Frontend Engineer (Vue / TypeScript)",
                company = "Typeform",
                location = "Barcelona, España",
                city = "Barcelona",
                country = "España",
                region = "Cataluña",
                description = "Diseño e implementación de componentes web interactivos accesibles con TypeScript, Vue/React y testing automatizado.",
                employmentType = "Jornada completa",
                remoteType = "Remoto",
                salary = "48.000€ - 62.000€ / año",
                category = JobCategories.TECNOLOGIA,
                publishedAtMillis = now - 3_600_000L * 4,
                applicationUrl = "https://es.linkedin.com/jobs",
                sourceUrl = "https://es.linkedin.com/jobs"
            )
        )

        // ==========================================
        // EUROPE & REMOTE LIVE FEEDS
        // ==========================================
        list.add(
            Job(
                id = "seed_arbeitnow_1",
                sourceId = "arbeitnow_europe",
                sourceName = "Arbeitnow Europa",
                originalId = "cloud-solutions-architect-lisbon",
                title = "Cloud Infrastructure Architect (AWS / K8s)",
                company = "Siemens Energy",
                location = "Lisboa, Portugal",
                city = "Lisboa",
                country = "Portugal",
                region = "Lisboa",
                description = "Diseño de infraestructura cloud resiliente, automatización Terraform y gestión de clústeres Kubernetes.",
                employmentType = "Jornada completa",
                remoteType = "Híbrido",
                salary = "60.000€ - 75.000€ / año",
                category = JobCategories.TECNOLOGIA,
                publishedAtMillis = now - 3_600_000L * 5,
                applicationUrl = "https://www.arbeitnow.com/view/cloud-architect-lisbon",
                sourceUrl = "https://www.arbeitnow.com"
            )
        )
        list.add(
            Job(
                id = "seed_jobicy_1",
                sourceId = "jobicy_europe",
                sourceName = "Jobicy Europa",
                originalId = "marketing-manager-remoto",
                title = "Growth Marketing Lead (Remoto Europa)",
                company = "Aircall",
                location = "Madrid / Remoto Europa",
                city = "Madrid",
                country = "España",
                region = "Comunidad de Madrid",
                description = "Estrategia de adquisición digital, gestión de presupuestos publicitarios y analítica de crecimiento.",
                employmentType = "Jornada completa",
                remoteType = "Remoto",
                salary = "50.000€ - 65.000€ / año",
                category = JobCategories.COMERCIO,
                publishedAtMillis = now - 3_600_000L * 8,
                applicationUrl = "https://jobicy.com",
                sourceUrl = "https://jobicy.com"
            )
        )
        list.add(
            Job(
                id = "seed_remotive_1",
                sourceId = "remotive_global",
                sourceName = "Remotive Tech",
                originalId = "product-designer-remoto-spain",
                title = "Product Designer (UI/UX) - 100% Remoto España",
                company = "Cabify",
                location = "Málaga / Remoto España",
                city = "Málaga",
                country = "España",
                region = "Andalucía",
                description = "Diseño de interfaces móviles y sistemas de diseño escalables. Requisitos: Figma y Design Systems.",
                employmentType = "Jornada completa",
                remoteType = "Remoto",
                salary = "45.000€ - 58.000€ / año",
                category = JobCategories.TECNOLOGIA,
                publishedAtMillis = now - 3_600_000L * 7,
                applicationUrl = "https://remotive.com",
                sourceUrl = "https://remotive.com"
            )
        )
        list.add(
            Job(
                id = "seed_wwr_1",
                sourceId = "wwr_rss",
                sourceName = "WeWorkRemotely",
                originalId = "devops-engineer-remote",
                title = "Site Reliability Engineer (SRE / Kubernetes)",
                company = "GitLab",
                location = "Remoto Europa",
                city = "Remoto",
                country = "España",
                region = "Remoto",
                description = "Alta disponibilidad, monitorización y automatización de infraestructura en la nube.",
                employmentType = "Jornada completa",
                remoteType = "Remoto",
                salary = "70.000€ - 90.000€ / año",
                category = JobCategories.TECNOLOGIA,
                publishedAtMillis = now - 3_600_000L * 9,
                applicationUrl = "https://weworkremotely.com",
                sourceUrl = "https://weworkremotely.com"
            )
        )

        return list
    }
}

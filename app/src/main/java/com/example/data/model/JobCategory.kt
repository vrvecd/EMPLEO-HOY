package com.example.data.model

data class JobCategoryInfo(
    val name: String,
    val emoji: String,
    val description: String
)

object JobCategories {
    val HOSTELERIA = "Hostelería y Turismo"
    val COMERCIO = "Comercio y Ventas"
    val LOGISTICA = "Logística y Almacén"
    val TECNOLOGIA = "Tecnología e Informática"
    val SANIDAD = "Sanidad y Cuidados"
    val ADMINISTRACION = "Administración y Oficinas"
    val CONSTRUCCION = "Construcción y Oficios"
    val ATENCION_CLIENTE = "Atención al Cliente"
    val EDUCACION = "Educación y Formación"
    val LIMPIEZA = "Limpieza y Servicios"

    val ALL_INFOS = listOf(
        JobCategoryInfo(HOSTELERIA, "☕", "Camareros, cocineros, hoteles, turismo"),
        JobCategoryInfo(COMERCIO, "🛍️", "Dependientes, cajeros, ventas, retail"),
        JobCategoryInfo(LOGISTICA, "📦", "Almacén, carretilleros, reparto"),
        JobCategoryInfo(TECNOLOGIA, "💻", "Software, soporte técnico, IT"),
        JobCategoryInfo(SANIDAD, "🩺", "Enfermería, auxiliares, farmacia"),
        JobCategoryInfo(ADMINISTRACION, "🏢", "Administrativos, contabilidad, RRHH"),
        JobCategoryInfo(CONSTRUCCION, "🔧", "Electricistas, mecánica, mantenimiento"),
        JobCategoryInfo(ATENCION_CLIENTE, "🎧", "Teleoperadores, soporte al cliente"),
        JobCategoryInfo(EDUCACION, "📚", "Profesores, docentes, formadores"),
        JobCategoryInfo(LIMPIEZA, "🧹", "Limpieza, servicios, conserjería")
    )

    val ALL_NAMES = ALL_INFOS.map { it.name }

    fun detectCategory(text: String): String {
        val lower = text.lowercase()
        return when {
            // 1. Hostelería y Turismo
            lower.contains("camarer") || lower.contains("cociner") || lower.contains("chef") ||
                    lower.contains("ayudante cocina") || lower.contains("barista") || lower.contains("barman") ||
                    lower.contains("hosteler") || lower.contains("restauran") || lower.contains("hotel") ||
                    lower.contains("turis") || lower.contains("camarero") || lower.contains("camarera") ||
                    lower.contains("buffet") || lower.contains("catering") || lower.contains("cocteler") -> HOSTELERIA

            // 2. Comercio y Ventas
            lower.contains("dependient") || lower.contains("vendedor") || lower.contains("cajer") ||
                    lower.contains("comercial") || lower.contains("retail") || lower.contains("tienda") ||
                    lower.contains("ventas") || lower.contains("sales") || lower.contains("promotor") ||
                    lower.contains("escaparat") || lower.contains("moda") -> COMERCIO

            // 3. Logística y Almacén
            lower.contains("almacén") || lower.contains("almacen") || lower.contains("mozo") ||
                    lower.contains("carretiller") || lower.contains("repartidor") || lower.contains("chofer") ||
                    lower.contains("chófer") || lower.contains("conductor") || lower.contains("transport") ||
                    lower.contains("logíst") || lower.contains("logist") || lower.contains("picking") ||
                    lower.contains("packing") || lower.contains("furgoneta") || lower.contains("camión") -> LOGISTICA

            // 4. Sanidad y Cuidados
            lower.contains("enferm") || lower.contains("médic") || lower.contains("medic") ||
                    lower.contains("auxiliar de enfermería") || lower.contains("gerocult") ||
                    lower.contains("cuidador") || lower.contains("fisioterap") || lower.contains("farmac") ||
                    lower.contains("dentist") || lower.contains("odont") || lower.contains("clínic") ||
                    lower.contains("hospital") || lower.contains("sanitari") -> SANIDAD

            // 5. Construcción y Oficios
            lower.contains("electricis") || lower.contains("fontaner") || lower.contains("mecánic") ||
                    lower.contains("mecanic") || lower.contains("albañil") || lower.contains("carpinter") ||
                    lower.contains("soldador") || lower.contains("mantenimiento") || lower.contains("pintor") ||
                    lower.contains("operario de fábrica") || lower.contains("electromecán") -> CONSTRUCCION

            // 6. Administración y Oficinas
            lower.contains("administrativ") || lower.contains("recepcionist") || lower.contains("contab") ||
                    lower.contains("secretari") || lower.contains("rrhh") || lower.contains("recursos humanos") ||
                    lower.contains("gestor") || lower.contains("facturación") || lower.contains("nóminas") -> ADMINISTRACION

            // 7. Atención al Cliente
            lower.contains("teleoperador") || lower.contains("call center") || lower.contains("atención al cliente") ||
                    lower.contains("customer service") || lower.contains("helpdesk") || lower.contains("atención telefónica") -> ATENCION_CLIENTE

            // 8. Educación y Formación
            lower.contains("profesor") || lower.contains("maestr") || lower.contains("docente") ||
                    lower.contains("formador") || lower.contains("monitor") || lower.contains("tutor") ||
                    lower.contains("pedagog") -> EDUCACION

            // 9. Limpieza y Servicios
            lower.contains("limpieza") || lower.contains("limpiador") || lower.contains("camarera de pisos") ||
                    lower.contains("conserje") || lower.contains("vigilante") || lower.contains("seguridad") ||
                    lower.contains("portero") -> LIMPIEZA

            // 10. Tecnología e Informática
            lower.contains("software") || lower.contains("developer") || lower.contains("programador") ||
                    lower.contains("kotlin") || lower.contains("android") || lower.contains("frontend") ||
                    lower.contains("backend") || lower.contains("full stack") || lower.contains("devops") ||
                    lower.contains("informátic") || lower.contains("informatic") || lower.contains("java") ||
                    lower.contains("python") || lower.contains("javascript") || lower.contains("qa") ||
                    lower.contains("ciberseguridad") || lower.contains("sistemas") -> TECNOLOGIA

            else -> "General / Otros"
        }
    }
}

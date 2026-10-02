# EmpleoHoy - Progressive Web App (PWA) para PWABuilder

Este directorio contiene la versión **Progressive Web App (PWA) de producción** de **EmpleoHoy**, optimizada al 100% para ser empaquetada como aplicación nativa Android (APK/AAB para Google Play) a través de **[PWABuilder](https://www.pwabuilder.com/)**.

---

## 📁 Estructura de Archivos PWA

| Archivo | Ubicación | Descripción |
| :--- | :--- | :--- |
| **`manifest.json`** | `/pwa/manifest.json` | Manifiesto de la aplicación web con todos los campos requeridos por PWABuilder (nombre, iconos 192/512/maskable, colores, shortcuts, orientación). |
| **`sw.js`** | `/pwa/sw.js` | Service Worker con estrategias de caché offline (*Cache-First* para la interfaz y *Network-First* con fallback para feeds). |
| **`index.html`** | `/pwa/index.html` | Entrada principal HTML5 con diseño responsive móvil, metaetiquetas PWA y soporte para notch/safe areas. |
| **`styles.css`** | `/pwa/styles.css` | Hoja de estilos con la paleta esmeralda (`#059669`), tarjetas limpias SaaS y barra de navegación inferior. |
| **`app.js`** | `/pwa/app.js` | Lógica de la aplicación: gestión de pestañas, búsqueda y filtros, ofertas de Madrid y Barcelona, gestión del botón atrás físico de Android (`popstate`) y sincronización en vivo. |
| **`icons/`** | `/pwa/icons/` | Iconos en alta resolución: `icon-192.png`, `icon-512.png`, `icon-maskable.png`, `icon.svg` y captura de pantalla. |

---

## 🚀 Pasos para Empaquetar con PWABuilder

1. **Alojar los archivos en un servidor HTTPS:**
   Sube el contenido de la carpeta `/pwa/` a cualquier servicio de hosting gratuito (Vercel, Netlify, Cloudflare Pages, Firebase Hosting o GitHub Pages).
   * *Ejemplo Vercel:* `vercel deploy pwa --prod`
   * *Ejemplo Netlify:* Arrastra la carpeta `pwa` a Netlify Drop.

2. **Acceder a PWABuilder:**
   * Entra en [https://www.pwabuilder.com/](https://www.pwabuilder.com/)
   * Introduce la URL HTTPS donde alojaste la PWA (ej: `https://empleohoy.vercel.app/`).
   * Pulsa en **"Start"**.

3. **Verificar el PWA Score:**
   * PWABuilder detectará automáticamente el `manifest.json`, el `sw.js` y los iconos en todas las resoluciones con puntuación máxima.

4. **Generar el paquete Android (AAB/APK):**
   * Haz clic en **"Package for Stores"** -> **"Android"**.
   * Personaliza el Package ID (ej: `com.empleohoy.app`) y la clave de firmado (Keystore).
   * Descarga el archivo `.aab` listo para subir a Google Play Console o el `.apk` para instalación directa.

---

## 📱 Características Nativas en Android

* **Botón Atrás Físico de Android:** Cierra los modales de detalle y filtros de forma nativa sin salir de la app.
* **Modo Standalone:** Se ejecuta a pantalla completa sin barra de navegación del navegador web.
* **Acceso Offline:** Funciona sin conexión gracias al Service Worker y al almacenamiento local.
* **Acciones Rápidas (Shortcuts):** Al mantener pulsado el icono en el launcher de Android, permite saltar directamente a *Buscar*, *Madrid*, *Barcelona* o *Favoritos*.

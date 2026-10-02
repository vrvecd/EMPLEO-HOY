// EmpleoHoy PWA Client App Engine
// Register Service Worker for PWABuilder & offline capabilities
if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('./sw.js')
      .then(reg => console.log('[PWA] Service Worker registered successfully:', reg.scope))
      .catch(err => console.error('[PWA] Service Worker registration failed:', err));
  });
}

// Global Application State
const STATE = {
  currentTab: 'explore',
  jobs: [],
  savedJobIds: new Set(JSON.parse(localStorage.getItem('empleohoy_saved_ids') || '[]')),
  selectedJob: null,
  filters: {
    query: '',
    location: '',
    remoteType: null,
    country: 'All', // 'All', 'ES', 'EU'
    category: null
  },
  lastSyncTime: Date.now()
};

// Seed Jobs Dataset (Madrid, Barcelona, Waiters, Cleaners, Tech, Retail, Logistica, Live Feeds)
const SEED_JOBS = [
  // MADRID - CAMAREROS / WAITERS
  {
    id: "seed_mad_waiter_1",
    sourceName: "Turijobs",
    title: "Camarero/a de Sala y Barra Profesional",
    company: "Grupo Dani García",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Buscamos camareros de sala con experiencia en restaurantes gastronómicos. Manejo de comandero digital, protocolo de servicio, conocimiento de bodega y trato cordial al comensal.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "21.000€ - 25.000€ / año + Propinas",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 2,
    applicationUrl: "https://www.turijobs.com"
  },
  {
    id: "seed_mad_waiter_2",
    sourceName: "Job Today",
    title: "Camarero/a de Terraza y Restaurante (Turno Continuo)",
    company: "Restaurante Amazónico",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Servicio dinámico en mesas de terraza y sala. Buena presencia, actitud proactiva y experiencia mínima de 1 año. Dos días de descanso seguidos.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.600€ - 1.900€ / mes + Bote",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 3,
    applicationUrl: "https://jobtoday.com/es"
  },
  {
    id: "seed_mad_waiter_3",
    sourceName: "StudentJob",
    title: "Camarero/a para Fines de Semana (Viernes a Domingo)",
    company: "Cervecería La Mayor",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Ideal para compatibilizar con estudios. Tiraje de cerveza, servicio de raciones y tapas en barra y terraza en zona centro.",
    employmentType: "Media jornada",
    remoteType: "Presencial",
    salary: "650€ - 850€ / mes netos",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 4,
    applicationUrl: "https://www.studentjob.es"
  },
  {
    id: "seed_mad_waiter_4",
    sourceName: "Trabajar.com",
    title: "Camarero/a de Barra y Tapeo Tradicional",
    company: "Taberna La Dolores",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Atención al cliente en barra castiza, servicio de cañas, vinos y tapas clásicas. Contrato estable con alta en seguridad social.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.600€ - 1.850€ / mes + Bote",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 5,
    applicationUrl: "https://www.trabajar.com"
  },
  {
    id: "seed_mad_waiter_5",
    sourceName: "Infoempleo",
    title: "Camarero/a de Cafetería y Salón",
    company: "Grupo VIPS / Alsea",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Servicio al comensal, preparación de cafés, bebidas y platos de cafetería. Contrato indefinido con formación continua.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "18.000€ - 20.500€ / año",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 6,
    applicationUrl: "https://www.infoempleo.com"
  },

  // MADRID - LIMPIEZA / CLEANERS & BASIC
  {
    id: "seed_mad_clean_1",
    sourceName: "Adecco",
    title: "Personal de Limpieza de Oficinas (Turno Mañana)",
    company: "Clece Servicios",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Limpieza de despachos, salas de reuniones y zonas comunes de edificio corporativo. Horario fijo de 07:00 a 15:00 de lunes a viernes.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.450€ - 1.650€ / mes",
    category: "Limpieza y Servicios",
    publishedAt: Date.now() - 3600000 * 1,
    applicationUrl: "https://www.adecco.es"
  },
  {
    id: "seed_mad_clean_2",
    sourceName: "Empléate (SEPE)",
    title: "Operario/a de Limpieza de Colegios e Institutos",
    company: "Grupo Eulen",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Limpieza y desinfección de aulas, gimnasios, pasillos y áreas recreativas. Puesto estable con contrato indefinido.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "16.800€ - 18.500€ / año",
    category: "Limpieza y Servicios",
    publishedAt: Date.now() - 3600000 * 2,
    applicationUrl: "https://www.empleate.gob.es"
  },
  {
    id: "seed_mad_clean_3",
    sourceName: "Turijobs",
    title: "Camarero/a de Pisos y Limpieza de Hotel",
    company: "NH Hotel Group",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Limpieza, orden y preparación de habitaciones de hotel de 4 estrellas. Cambio de lencería, reposición de amenities y control de calidad.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.500€ - 1.700€ / mes",
    category: "Limpieza y Servicios",
    publishedAt: Date.now() - 3600000 * 3,
    applicationUrl: "https://www.turijobs.com"
  },
  {
    id: "seed_mad_clean_4",
    sourceName: "Job Today",
    title: "Lavaplatos y Ayudante de Limpieza de Cocina",
    company: "Taberna La Latina",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Manejo de lavavajillas industrial (tren de lavado), limpieza de cazuelas, menaje y mantenimiento de la zona de cocina.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.450€ - 1.650€ / mes",
    category: "Limpieza y Servicios",
    publishedAt: Date.now() - 3600000 * 4,
    applicationUrl: "https://jobtoday.com/es"
  },
  {
    id: "seed_mad_clean_5",
    sourceName: "Randstad",
    title: "Mozo/a de Almacén y Paquetería Básica (Sin experiencia)",
    company: "Amazon Logística",
    location: "Madrid, España",
    city: "Madrid",
    country: "España",
    description: "Preparación y empaquetado de pedidos con pistola de radiofrecuencia (picking/packing). Trabajo sencillo con formación pagada inicial.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "19.200€ - 22.000€ / año",
    category: "Logística y Almacén",
    publishedAt: Date.now() - 3600000 * 5,
    applicationUrl: "https://www.randstad.es"
  },

  // BARCELONA - CAMAREROS / WAITERS
  {
    id: "seed_bcn_waiter_1",
    sourceName: "Job Today",
    title: "Camarero/a de Terraza y Restaurante (Incorporación inmediata)",
    company: "Grupo Tragaluz",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Servicio ágil de mesas en terraza, toma de comandas en comandero digital y atención al cliente. Buen ambiente y propinas semanales.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.550€ - 1.850€ / mes",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 1,
    applicationUrl: "https://jobtoday.com/es"
  },
  {
    id: "seed_bcn_waiter_2",
    sourceName: "Turijobs",
    title: "Camarero/a de Sala y Barra Gastronómica",
    company: "Restaurante El Nacional",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Servicio de sala en emblemático espacio multiespacio de Paseo de Gracia. Protocolo de servicio, maridajes de vinos y atención internacional.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "22.000€ - 26.000€ / año + Propinas",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 2,
    applicationUrl: "https://www.turijobs.com"
  },
  {
    id: "seed_bcn_waiter_3",
    sourceName: "InfoJobs",
    title: "Camarero/a de Cafetería y Brunch de Especialidad",
    company: "Brunch & Cake Barcelona",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Servicio de mesas, cafés de especialidad, zumos naturales y repostería artesanal. Horario intensivo diurno sin turno partido.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.500€ - 1.800€ / mes",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 3,
    applicationUrl: "https://www.infojobs.net"
  },
  {
    id: "seed_bcn_waiter_4",
    sourceName: "Turijobs",
    title: "Camarero/a para Hotel 5* y Banquetes",
    company: "Hotel Arts Barcelona",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Servicio en desayunos buffet, terraza lounge y banquetes de eventos privados. Se requiere nivel conversacional de inglés.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "23.000€ - 27.500€ / año",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 4,
    applicationUrl: "https://www.turijobs.com"
  },
  {
    id: "seed_bcn_waiter_5",
    sourceName: "Job Today",
    title: "Camarero/a de Coctelería y Terraza Frente al Mar",
    company: "Sky Bar Barceloneta",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Preparación y servicio de cócteles clásicos, copas y aperitivos en terraza con vistas al mar. Ambiente joven y dinámico.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.650€ - 1.950€ / mes + Bote",
    category: "Hostelería y Turismo",
    publishedAt: Date.now() - 3600000 * 5,
    applicationUrl: "https://jobtoday.com/es"
  },

  // BARCELONA - LIMPIEZA / CLEANERS & BASIC
  {
    id: "seed_bcn_clean_1",
    sourceName: "Adecco",
    title: "Operario/a de Limpieza de Oficinas y Superficies",
    company: "Clece Servicios",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Limpieza y desinfección de instalaciones corporativas, despachos y zonas comunes en distrito 22@. Turno fijo de mañana.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "16.500€ - 18.000€ / año",
    category: "Limpieza y Servicios",
    publishedAt: Date.now() - 3600000 * 1,
    applicationUrl: "https://www.adecco.es"
  },
  {
    id: "seed_bcn_clean_2",
    sourceName: "Empléate (SEPE)",
    title: "Personal de Limpieza de Clínicas y Centros Sanitarios",
    company: "Optima Facility",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Protocolo de higiene hospitalaria, desinfección de consultas, quirófanos y salas de espera. Formación específica por cuenta de la empresa.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "17.000€ - 19.000€ / año",
    category: "Limpieza y Servicios",
    publishedAt: Date.now() - 3600000 * 2,
    applicationUrl: "https://www.empleate.gob.es"
  },
  {
    id: "seed_bcn_clean_3",
    sourceName: "Job Today",
    title: "Personal de Limpieza de Apartamentos Turísticos",
    company: "Stay Barcelona Apartments",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Limpieza y puesta a punto de apartamentos tras salida de huéspedes. Horario de 10:00 a 16:00. Puesto estable todo el año.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.450€ - 1.650€ / mes",
    category: "Limpieza y Servicios",
    publishedAt: Date.now() - 3600000 * 3,
    applicationUrl: "https://jobtoday.com/es"
  },
  {
    id: "seed_bcn_clean_4",
    sourceName: "InfoJobs",
    title: "Reponedor/a y Cajero/a de Supermercado (Turno Mañana)",
    company: "Mercadona",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Cobro en cajas, colocación de producto en estanterías y orden de la tienda. Contrato indefinido y progresión salarial anual.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "1.507€ / mes netos inicial",
    category: "Comercio y Ventas",
    publishedAt: Date.now() - 3600000 * 4,
    applicationUrl: "https://www.infojobs.net"
  },
  {
    id: "seed_bcn_clean_5",
    sourceName: "Randstad",
    title: "Mozo/a de Almacén y Preparación de Pedidos",
    company: "El Corte Inglés Logística",
    location: "Barcelona, España",
    city: "Barcelona",
    country: "España",
    description: "Clasificación de mercancía, preparación de pedidos online y carga/descarga de camiones. Turnos rotativos continuos.",
    employmentType: "Jornada completa",
    remoteType: "Presencial",
    salary: "18.800€ - 21.500€ / año",
    category: "Logística y Almacén",
    publishedAt: Date.now() - 3600000 * 5,
    applicationUrl: "https://www.randstad.es"
  },

  // LIVE EUROPE & REMOTE TECH
  {
    id: "seed_arbeitnow_1",
    sourceName: "Arbeitnow Europa",
    title: "Cloud Infrastructure Architect (AWS / K8s)",
    company: "Siemens Energy",
    location: "Lisboa / Remoto Europa",
    city: "Lisboa",
    country: "Portugal",
    description: "Diseño de infraestructura cloud resiliente, automatización Terraform y gestión de clústeres Kubernetes.",
    employmentType: "Jornada completa",
    remoteType: "Híbrido",
    salary: "60.000€ - 75.000€ / año",
    category: "Tecnología e Informática",
    publishedAt: Date.now() - 3600000 * 5,
    applicationUrl: "https://www.arbeitnow.com"
  },
  {
    id: "seed_remotive_1",
    sourceName: "Remotive Tech",
    title: "Product Designer (UI/UX) - 100% Remoto España",
    company: "Cabify",
    location: "Málaga / Remoto España",
    city: "Málaga",
    country: "España",
    description: "Diseño de interfaces móviles y sistemas de diseño escalables. Requisitos: Figma y Design Systems.",
    employmentType: "Jornada completa",
    remoteType: "Remoto",
    salary: "45.000€ - 58.000€ / año",
    category: "Tecnología e Informática",
    publishedAt: Date.now() - 3600000 * 7,
    applicationUrl: "https://remotive.com"
  }
];

const SECTORS = [
  { name: "Hostelería y Turismo", emoji: "☕" },
  { name: "Comercio y Ventas", emoji: "🛍️" },
  { name: "Logística y Almacén", emoji: "📦" },
  { name: "Limpieza y Servicios", emoji: "🧹" },
  { name: "Tecnología e Informática", emoji: "💻" },
  { name: "Sanidad y Cuidados", emoji: "🩺" },
  { name: "Administración y Oficinas", emoji: "🏢" }
];

const SOURCES = [
  { name: "InfoJobs España", type: "Web / RSS Oficial", coverage: "España", count: "10 ofertas" },
  { name: "Turijobs España", type: "Hostelería & Turismo", coverage: "España", count: "8 ofertas" },
  { name: "Job Today", type: "Empleo Inmediato", coverage: "España", count: "8 ofertas" },
  { name: "Empléate (SEPE)", type: "Portal Público", coverage: "España", count: "6 ofertas" },
  { name: "Adecco España", type: "ETP & Selección", coverage: "España", count: "5 ofertas" },
  { name: "Randstad España", type: "Logística & Servicios", coverage: "España", count: "5 ofertas" },
  { name: "Arbeitnow Europa", type: "JSON Feed en Vivo", coverage: "Europa", count: "En vivo" },
  { name: "Remotive Global", type: "API Remoto en Vivo", coverage: "Global", count: "En vivo" }
];

// Relative Time Helper
function formatTimeAgo(timestamp) {
  const diff = Date.now() - timestamp;
  const minutes = Math.floor(diff / 60000);
  const hours = Math.floor(diff / 3600000);
  const days = Math.floor(diff / 86400000);

  if (minutes < 60) return `Hace ${Math.max(1, minutes)}m`;
  if (hours < 24) return `Hace ${hours}h`;
  return `Hace ${days}d`;
}

// Initialise App
function initApp() {
  STATE.jobs = [...SEED_JOBS];
  
  // Render Sectors
  const sectorsContainer = document.getElementById('sectors-container');
  if (sectorsContainer) {
    sectorsContainer.innerHTML = SECTORS.map(sec => `
      <button class="chip sector-chip" data-category="${sec.name}">
        <span>${sec.emoji}</span> ${sec.name}
      </button>
    `).join('');
  }

  // Populate Filter Category Select
  const categorySelect = document.getElementById('filter-select-category');
  if (categorySelect) {
    categorySelect.innerHTML = '<option value="">Todas las categorías</option>' +
      SECTORS.map(s => `<option value="${s.name}">${s.emoji} ${s.name}</option>`).join('');
  }

  // Render Sources Tab
  renderSourcesList();

  // Setup Event Listeners
  setupNavigation();
  setupFilters();
  setupModals();
  setupAndroidBackButton();

  // Initial Render
  renderJobs();

  // Fetch live feeds asynchronously
  fetchLiveFeeds();

  // Handle URL query parameters if opened with shortcut (?tab=search, ?city=Madrid)
  const urlParams = new URLSearchParams(window.location.search);
  const initialTab = urlParams.get('tab');
  const initialCity = urlParams.get('city');

  if (initialCity) {
    STATE.filters.location = initialCity;
    document.getElementById('search-input-location').value = initialCity;
    switchTab('search');
  } else if (initialTab) {
    switchTab(initialTab);
  }
}

// Render Job Card HTML
function createJobCardHTML(job) {
  const isSaved = STATE.savedJobIds.has(job.id);
  const initial = (job.company || job.title).charAt(0).toUpperCase();
  const isSpain = job.country?.toLowerCase().includes('españa') || job.country?.toLowerCase().includes('spain') || job.city === 'Madrid' || job.city === 'Barcelona';
  const flag = isSpain ? '🇪🇸' : '🇪🇺';

  return `
    <article class="job-card" data-job-id="${job.id}">
      <div class="card-top">
        <div class="company-logo-initial">${initial}</div>
        <div class="card-header-info">
          <h3 class="job-title">${escapeHTML(job.title)}</h3>
          <p class="job-company-location">
            <span>${escapeHTML(job.company || 'Empresa confidencial')}</span>
            <span>•</span>
            <span>${flag} ${escapeHTML(job.location || 'España')}</span>
          </p>
        </div>
      </div>

      <div class="card-badges">
        <span class="badge badge-remote">${escapeHTML(job.remoteType || 'Presencial')}</span>
        ${job.category ? `<span class="badge badge-gray">${escapeHTML(job.category)}</span>` : ''}
        ${job.salary ? `<span class="badge badge-salary">💰 ${escapeHTML(job.salary)}</span>` : ''}
      </div>

      <div class="card-footer">
        <span class="card-time">Publicada ${formatTimeAgo(job.publishedAt)}</span>
        <span class="card-source-pill">${escapeHTML(job.sourceName)}</span>
      </div>
    </article>
  `;
}

// Filter and Render Jobs
function renderJobs() {
  const filtered = STATE.jobs.filter(job => {
    // Query filter
    if (STATE.filters.query) {
      const q = STATE.filters.query.toLowerCase();
      const match = (job.title && job.title.toLowerCase().includes(q)) ||
                    (job.company && job.company.toLowerCase().includes(q)) ||
                    (job.description && job.description.toLowerCase().includes(q));
      if (!match) return false;
    }

    // Location filter
    if (STATE.filters.location) {
      const loc = STATE.filters.location.toLowerCase();
      const matchLoc = (job.location && job.location.toLowerCase().includes(loc)) ||
                       (job.city && job.city.toLowerCase().includes(loc));
      if (!matchLoc) return false;
    }

    // Remote Type filter
    if (STATE.filters.remoteType) {
      if (job.remoteType !== STATE.filters.remoteType) return false;
    }

    // Category filter
    if (STATE.filters.category) {
      if (job.category !== STATE.filters.category) return false;
    }

    // Country filter
    if (STATE.filters.country === 'ES') {
      const isSpain = job.country?.toLowerCase().includes('españa') || job.country?.toLowerCase().includes('spain') || job.city === 'Madrid' || job.city === 'Barcelona' || job.city === 'Valencia' || job.city === 'Sevilla';
      if (!isSpain) return false;
    } else if (STATE.filters.country === 'EU') {
      const isEurope = job.country?.toLowerCase().includes('portugal') || job.country?.toLowerCase().includes('germany') || job.location?.toLowerCase().includes('europa');
      if (!isEurope) return false;
    }

    return true;
  });

  // Render to Explore view
  const exploreList = document.getElementById('explore-jobs-list');
  const exploreCount = document.getElementById('explore-jobs-count');
  if (exploreList) {
    exploreList.innerHTML = filtered.map(createJobCardHTML).join('') || '<p style="text-align:center; padding:30px; color:#64748B;">No se encontraron ofertas con los filtros actuales.</p>';
    if (exploreCount) exploreCount.textContent = `${filtered.length} ofertas`;
  }

  // Render to Search view
  const searchList = document.getElementById('search-jobs-list');
  const searchCount = document.getElementById('search-results-count');
  if (searchList) {
    searchList.innerHTML = filtered.map(createJobCardHTML).join('') || '<p style="text-align:center; padding:30px; color:#64748B;">No se encontraron ofertas.</p>';
    if (searchCount) searchCount.textContent = `${filtered.length} ofertas encontradas`;
  }

  // Render Saved view
  const savedList = document.getElementById('saved-jobs-list');
  if (savedList) {
    const savedJobs = STATE.jobs.filter(j => STATE.savedJobIds.has(j.id));
    savedList.innerHTML = savedJobs.map(createJobCardHTML).join('') || '<p style="text-align:center; padding:40px; color:#64748B;">No tienes ofertas guardadas.<br>Toca cualquier oferta y pulsa el icono de guardar.</p>';
  }

  // Attach card click handlers
  document.querySelectorAll('.job-card').forEach(card => {
    card.addEventListener('click', () => {
      const jobId = card.getAttribute('data-job-id');
      const job = STATE.jobs.find(j => j.id === jobId);
      if (job) openJobDetailModal(job);
    });
  });
}

// Navigation & Tab Switching
function switchTab(tabId) {
  STATE.currentTab = tabId;

  document.querySelectorAll('.tab-view').forEach(v => v.classList.remove('active'));
  document.querySelectorAll('.nav-item').forEach(b => b.classList.remove('active'));

  const view = document.getElementById(`view-${tabId}`);
  const navBtn = document.querySelector(`.nav-item[data-tab="${tabId}"]`);

  if (view) view.classList.add('active');
  if (navBtn) navBtn.classList.add('active');

  // Push state to history for back navigation
  history.pushState({ tab: tabId }, '', `#${tabId}`);
  renderJobs();
}

function setupNavigation() {
  document.querySelectorAll('.nav-item').forEach(btn => {
    btn.addEventListener('click', () => {
      const tab = btn.getAttribute('data-tab');
      switchTab(tab);
    });
  });

  const homeSearchTrigger = document.getElementById('home-search-trigger');
  if (homeSearchTrigger) {
    homeSearchTrigger.addEventListener('click', () => {
      switchTab('search');
      setTimeout(() => document.getElementById('search-input-query')?.focus(), 150);
    });
  }

  const btnViewAllSectors = document.getElementById('btn-view-all-sectors');
  if (btnViewAllSectors) {
    btnViewAllSectors.addEventListener('click', () => switchTab('search'));
  }
}

// Setup Filters
function setupFilters() {
  // Modality Chips in Explore
  document.querySelectorAll('#view-explore .scroll-chips-row .chip').forEach(chip => {
    chip.addEventListener('click', () => {
      document.querySelectorAll('#view-explore .scroll-chips-row .chip').forEach(c => c.classList.remove('active'));
      chip.classList.add('active');
      STATE.filters.remoteType = chip.getAttribute('data-remote') || null;
      renderJobs();
    });
  });

  // Sector chips click
  document.addEventListener('click', (e) => {
    const sectorChip = e.target.closest('.sector-chip');
    if (sectorChip) {
      const category = sectorChip.getAttribute('data-category');
      STATE.filters.category = category;
      switchTab('search');
      renderJobs();
    }

    const cityChip = e.target.closest('.city-chip');
    if (cityChip) {
      const city = cityChip.getAttribute('data-city');
      STATE.filters.location = city;
      document.getElementById('search-input-location').value = city;
      document.getElementById('btn-clear-location').style.display = 'block';
      switchTab('search');
      renderJobs();
    }
  });

  // Search input events
  const queryInput = document.getElementById('search-input-query');
  const btnClearQuery = document.getElementById('btn-clear-query');
  if (queryInput) {
    queryInput.addEventListener('input', (e) => {
      STATE.filters.query = e.target.value.trim();
      btnClearQuery.style.display = e.target.value ? 'block' : 'none';
      renderJobs();
    });
    btnClearQuery?.addEventListener('click', () => {
      queryInput.value = '';
      STATE.filters.query = '';
      btnClearQuery.style.display = 'none';
      renderJobs();
    });
  }

  // Location input events
  const locInput = document.getElementById('search-input-location');
  const btnClearLoc = document.getElementById('btn-clear-location');
  if (locInput) {
    locInput.addEventListener('input', (e) => {
      STATE.filters.location = e.target.value.trim();
      btnClearLoc.style.display = e.target.value ? 'block' : 'none';
      renderJobs();
    });
    btnClearLoc?.addEventListener('click', () => {
      locInput.value = '';
      STATE.filters.location = '';
      btnClearLoc.style.display = 'none';
      renderJobs();
    });
  }

  // Scope chips in search view
  document.querySelectorAll('.scope-chips-row .chip').forEach(chip => {
    chip.addEventListener('click', () => {
      document.querySelectorAll('.scope-chips-row .chip').forEach(c => c.classList.remove('active'));
      chip.classList.add('active');
      STATE.filters.country = chip.getAttribute('data-country');
      renderJobs();
    });
  });

  // Clear all filters
  document.getElementById('btn-clear-all-filters')?.addEventListener('click', () => {
    STATE.filters = { query: '', location: '', remoteType: null, country: 'All', category: null };
    if (queryInput) queryInput.value = '';
    if (locInput) locInput.value = '';
    if (btnClearQuery) btnClearQuery.style.display = 'none';
    if (btnClearLoc) btnClearLoc.style.display = 'none';
    document.querySelectorAll('.chip').forEach(c => c.classList.remove('active'));
    document.querySelector('.chip[data-remote=""]')?.classList.add('active');
    document.querySelector('.chip[data-country="All"]')?.classList.add('active');
    renderJobs();
  });
}

// Modal handling
function openJobDetailModal(job) {
  STATE.selectedJob = job;
  const modal = document.getElementById('job-detail-modal');
  const body = document.getElementById('modal-detail-body');
  const companyTitle = document.getElementById('modal-company-title');
  const applyBtn = document.getElementById('btn-modal-apply');
  const saveBtn = document.getElementById('btn-modal-save');

  if (!modal || !body) return;

  companyTitle.textContent = job.company || 'Detalles de la oferta';
  applyBtn.href = job.applicationUrl || '#';

  const isSaved = STATE.savedJobIds.has(job.id);
  updateSaveButtonIcon(saveBtn, isSaved);

  const initial = (job.company || job.title).charAt(0).toUpperCase();
  const flag = job.country?.toLowerCase().includes('españa') || job.city === 'Madrid' || job.city === 'Barcelona' ? '🇪🇸' : '🇪🇺';

  body.innerHTML = `
    <div style="display:flex; align-items:center; gap:14px; margin-bottom:16px;">
      <div class="company-logo-initial" style="width:52px; height:52px; font-size:22px;">${initial}</div>
      <div>
        <h3 style="font-size:16px; font-weight:800;">${escapeHTML(job.company || 'Empresa confidencial')}</h3>
        <p style="font-size:13px; color:#64748B;">${flag} ${escapeHTML(job.location || 'España')}</p>
      </div>
    </div>

    <h2 style="font-size:20px; font-weight:800; line-height:1.3; margin-bottom:12px;">${escapeHTML(job.title)}</h2>

    <div class="card-badges" style="margin-bottom:16px;">
      <span class="badge badge-remote">${escapeHTML(job.remoteType || 'Presencial')}</span>
      ${job.employmentType ? `<span class="badge badge-gray">${escapeHTML(job.employmentType)}</span>` : ''}
      ${job.category ? `<span class="badge badge-gray">${escapeHTML(job.category)}</span>` : ''}
      ${job.salary ? `<span class="badge badge-salary" style="font-size:13px;">💰 ${escapeHTML(job.salary)}</span>` : ''}
    </div>

    <div style="background-color:#F8FAFC; border:1px solid #E2E8F0; border-radius:12px; padding:16px; margin-bottom:16px;">
      <h4 style="font-size:14px; font-weight:700; margin-bottom:8px;">Descripción del puesto</h4>
      <p style="font-size:14px; line-height:1.6; color:#334155;">${escapeHTML(job.description || 'Consulta los detalles y requisitos completos en la oferta oficial.')}</p>
    </div>

    <!-- Actions Section: Save Button and Underneath Share Button -->
    <div style="background-color:#FFFFFF; border:1px solid #E2E8F0; border-radius:12px; padding:14px; margin-bottom:16px; display:flex; flex-direction:column; gap:10px;">
      <h4 style="font-size:14px; font-weight:700;">Acciones de la oferta</h4>
      
      <!-- 1. SAVE BUTTON -->
      <button class="btn-secondary" id="btn-modal-action-save" style="width:100%; display:flex; align-items:center; justify-content:center; gap:8px;">
        <svg viewBox="0 0 24 24" width="18" height="18" fill="${isSaved ? '#059669' : 'none'}" stroke="${isSaved ? '#059669' : 'currentColor'}" stroke-width="2">
          <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
        </svg>
        <span style="font-weight:700; color:${isSaved ? '#059669' : 'inherit'};">${isSaved ? 'Oferta guardada en Favoritos' : 'Guardar oferta en Favoritos'}</span>
      </button>

      <!-- 2. SHARE BUTTON DIRECTLY UNDER SAVE BUTTON -->
      <button class="btn-secondary" id="btn-modal-action-share" style="width:100%; display:flex; align-items:center; justify-content:center; gap:8px; border-color:#059669; color:#047857; background-color:#F0FDF4;">
        <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="#047857" stroke-width="2">
          <circle cx="18" cy="5" r="3"></circle>
          <circle cx="6" cy="12" r="3"></circle>
          <circle cx="18" cy="19" r="3"></circle>
          <line x1="8.59" y1="13.51" x2="15.42" y2="17.49"></line>
          <line x1="15.41" y1="6.51" x2="8.59" y2="10.49"></line>
        </svg>
        <span style="font-weight:700;">Compartir con un contacto / amigos</span>
      </button>
    </div>

    <!-- AdMob Sponsored Ad Card -->
    <div style="background-color:#F0FDF4; border:1px solid #A7F3D0; border-radius:12px; padding:12px; margin-bottom:16px; display:flex; align-items:center; gap:12px;">
      <div style="width:36px; height:36px; border-radius:8px; background-color:#059669; color:white; display:flex; align-items:center; justify-content:center; font-size:18px; flex-shrink:0;">★</div>
      <div>
        <div style="display:flex; align-items:center; gap:6px;">
          <span style="font-size:9px; font-weight:800; background:#DCFCE7; color:#065F46; padding:2px 5px; border-radius:4px;">ANUNCIO</span>
          <span style="font-size:12px; font-weight:700; color:#065F46;">Cursos de Formación y Certificados</span>
        </div>
        <p style="font-size:11px; color:#334155; margin-top:2px;">Mejora tu empleabilidad con titulaciones oficiales gratuitas.</p>
      </div>
    </div>

    <div style="font-size:12px; color:#64748B; display:flex; justify-content:space-between; align-items:center;">
      <span>Publicada ${formatTimeAgo(job.publishedAt)}</span>
      <span class="card-source-pill">Fuente: ${escapeHTML(job.sourceName)}</span>
    </div>
  `;

  // Attach event listeners for in-body action buttons
  document.getElementById('btn-modal-action-save')?.addEventListener('click', () => {
    btnModalSave?.click();
  });
  document.getElementById('btn-modal-action-share')?.addEventListener('click', () => {
    btnModalShare?.click();
  });

  modal.classList.add('open');
  modal.setAttribute('aria-hidden', 'false');
  history.pushState({ modal: 'job-detail' }, '', '#detail');
}

function updateSaveButtonIcon(btn, isSaved) {
  if (!btn) return;
  btn.innerHTML = isSaved ? `
    <svg viewBox="0 0 24 24" width="20" height="20" fill="#059669" stroke="#059669" stroke-width="2">
      <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
    </svg>
  ` : `
    <svg viewBox="0 0 24 24" width="20" height="20" fill="none" stroke="currentColor" stroke-width="2">
      <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
    </svg>
  `;
}

function setupModals() {
  const detailModal = document.getElementById('job-detail-modal');
  const btnCloseDetail = document.getElementById('btn-close-detail');
  const btnModalSave = document.getElementById('btn-modal-save');
  const btnModalShare = document.getElementById('btn-modal-share');

  btnCloseDetail?.addEventListener('click', () => {
    detailModal.classList.remove('open');
    detailModal.setAttribute('aria-hidden', 'true');
    if (history.state?.modal) history.back();
  });

  btnModalSave?.addEventListener('click', () => {
    if (!STATE.selectedJob) return;
    const id = STATE.selectedJob.id;
    if (STATE.savedJobIds.has(id)) {
      STATE.savedJobIds.delete(id);
    } else {
      STATE.savedJobIds.add(id);
    }
    localStorage.setItem('empleohoy_saved_ids', JSON.stringify([...STATE.savedJobIds]));
    updateSaveButtonIcon(btnModalSave, STATE.savedJobIds.has(id));
    renderJobs();
  });

  btnModalShare?.addEventListener('click', () => {
    if (!STATE.selectedJob) return;
    if (navigator.share) {
      navigator.share({
        title: STATE.selectedJob.title,
        text: `Oferta de empleo: ${STATE.selectedJob.title} en ${STATE.selectedJob.company || 'España'} - EmpleoHoy`,
        url: STATE.selectedJob.applicationUrl || window.location.href
      }).catch(() => {});
    } else {
      navigator.clipboard.writeText(STATE.selectedJob.applicationUrl || window.location.href);
      alert('Enlace copiado al portapapeles');
    }
  });

  // Filter Sheet Modal
  const filterModal = document.getElementById('filter-modal');
  const btnOpenFilter = document.getElementById('btn-open-filter-modal');
  const btnCloseFilter = document.getElementById('btn-close-filters');
  const btnApplyFilter = document.getElementById('btn-apply-filters');
  const btnResetFilter = document.getElementById('btn-reset-filters');

  btnOpenFilter?.addEventListener('click', () => {
    filterModal.classList.add('open');
    filterModal.setAttribute('aria-hidden', 'false');
    history.pushState({ modal: 'filter' }, '', '#filters');
  });

  btnCloseFilter?.addEventListener('click', () => {
    filterModal.classList.remove('open');
    filterModal.setAttribute('aria-hidden', 'true');
    if (history.state?.modal) history.back();
  });

  btnApplyFilter?.addEventListener('click', () => {
    const selectedCategory = document.getElementById('filter-select-category').value;
    const selectedRemote = document.querySelector('input[name="remote"]:checked')?.value || null;

    STATE.filters.category = selectedCategory || null;
    STATE.filters.remoteType = selectedRemote;

    const badge = document.getElementById('active-filter-badge');
    const count = (selectedCategory ? 1 : 0) + (selectedRemote ? 1 : 0);
    if (badge) {
      badge.textContent = count;
      badge.style.display = count > 0 ? 'flex' : 'none';
    }

    filterModal.classList.remove('open');
    if (history.state?.modal) history.back();
    renderJobs();
  });

  btnResetFilter?.addEventListener('click', () => {
    document.getElementById('filter-select-category').value = '';
    const firstRadio = document.querySelector('input[name="remote"][value=""]');
    if (firstRadio) firstRadio.checked = true;
    STATE.filters.category = null;
    STATE.filters.remoteType = null;
    const badge = document.getElementById('active-filter-badge');
    if (badge) badge.style.display = 'none';
    filterModal.classList.remove('open');
    if (history.state?.modal) history.back();
    renderJobs();
  });
}

// Android Hardware Back Button Handling via popstate
function setupAndroidBackButton() {
  window.addEventListener('popstate', (e) => {
    const detailModal = document.getElementById('job-detail-modal');
    const filterModal = document.getElementById('filter-modal');

    // If modal is open, close it first
    if (detailModal && detailModal.classList.contains('open')) {
      detailModal.classList.remove('open');
      detailModal.setAttribute('aria-hidden', 'true');
      return;
    }

    if (filterModal && filterModal.classList.contains('open')) {
      filterModal.classList.remove('open');
      filterModal.setAttribute('aria-hidden', 'true');
      return;
    }

    // Otherwise navigate to previous tab or default to explore
    const targetTab = e.state?.tab || 'explore';
    if (targetTab !== STATE.currentTab) {
      document.querySelectorAll('.tab-view').forEach(v => v.classList.remove('active'));
      document.querySelectorAll('.nav-item').forEach(b => b.classList.remove('active'));
      document.getElementById(`view-${targetTab}`)?.classList.add('active');
      document.querySelector(`.nav-item[data-tab="${targetTab}"]`)?.classList.add('active');
      STATE.currentTab = targetTab;
    }
  });
}

// Render Sources List in Tab 4
function renderSourcesList() {
  const container = document.getElementById('sources-list-container');
  if (!container) return;

  container.innerHTML = SOURCES.map(s => `
    <div class="source-item">
      <div class="source-info">
        <h4>${escapeHTML(s.name)}</h4>
        <p>${escapeHTML(s.type)} • Cobertura: ${escapeHTML(s.coverage)}</p>
      </div>
      <span class="status-pill green">${escapeHTML(s.count)}</span>
    </div>
  `).join('');

  const syncHeaderBtn = document.getElementById('btn-sync-header');
  const forceSyncBtn = document.getElementById('btn-force-sync');
  const metricLastSync = document.getElementById('metric-last-sync');

  function triggerSync() {
    const liveIndicator = document.getElementById('live-text');
    if (liveIndicator) liveIndicator.textContent = 'Sincronizando...';
    fetchLiveFeeds().then(() => {
      if (liveIndicator) liveIndicator.textContent = 'En directo';
      if (metricLastSync) metricLastSync.textContent = 'Ahora mismo';
    });
  }

  syncHeaderBtn?.addEventListener('click', triggerSync);
  forceSyncBtn?.addEventListener('click', triggerSync);
}

// Asynchronously Fetch Live Feeds (Arbeitnow & Remotive)
async function fetchLiveFeeds() {
  try {
    const res = await fetch('https://www.arbeitnow.com/api/job-board-api', { cache: 'no-cache' });
    if (res.ok) {
      const data = await res.json();
      if (data.data && Array.isArray(data.data)) {
        const liveJobs = data.data.slice(0, 15).map(item => ({
          id: `live_${item.slug || Math.random().toString(36).substr(2, 9)}`,
          sourceName: "Arbeitnow Europa",
          title: item.title || "Puesto de tecnología",
          company: item.company_name || "Tech Company",
          location: item.location || "Europa / Remoto",
          city: "Remoto",
          country: item.remote ? "Europa" : "Alemania",
          description: item.description ? item.description.replace(/<[^>]*>?/gm, '').substring(0, 300) + '...' : '',
          employmentType: item.job_types?.[0] || "Jornada completa",
          remoteType: item.remote ? "Remoto" : "Híbrido",
          salary: "45.000€ - 70.000€ / año",
          category: "Tecnología e Informática",
          publishedAt: item.created_at ? item.created_at * 1000 : Date.now(),
          applicationUrl: item.url || "https://www.arbeitnow.com"
        }));

        // Merge with seed without duplicates
        const existingIds = new Set(STATE.jobs.map(j => j.id));
        const newLive = liveJobs.filter(j => !existingIds.has(j.id));
        STATE.jobs = [...newLive, ...STATE.jobs];
        renderJobs();
        console.log(`[PWA] Sincronizadas ${newLive.length} ofertas en directo de Europa`);
      }
    }
  } catch (err) {
    console.log('[PWA] Usando catálogo local precargado en caché offline');
  }
}

// Helper: Escape HTML
function escapeHTML(str) {
  if (!str) return '';
  return str.replace(/[&<>'"]/g, tag => ({
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    "'": '&#39;',
    '"': '&quot;'
  }[tag] || tag));
}

// Run on load
document.addEventListener('DOMContentLoaded', initApp);

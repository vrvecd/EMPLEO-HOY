package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Job
import com.example.ui.components.BrandHeader
import com.example.ui.components.JobCard
import com.example.ui.theme.BrandGreenContainer
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenOnContainer
import com.example.ui.theme.BrandGreenPrimary
import com.example.ui.theme.BrandGreenSoft
import com.example.ui.viewmodel.JobViewModel

@Composable
fun HomeScreen(
    viewModel: JobViewModel,
    onJobClick: (Job) -> Unit,
    onNavigateToSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isOnline by viewModel.isOnline.collectAsStateWithLifecycle()
    val syncStatus by viewModel.syncStatus.collectAsStateWithLifecycle()
    val allJobs by viewModel.allJobs.collectAsStateWithLifecycle()
    val spainJobs by viewModel.spainJobs.collectAsStateWithLifecycle()
    val europeJobs by viewModel.europeJobs.collectAsStateWithLifecycle()
    val remoteJobs by viewModel.remoteJobs.collectAsStateWithLifecycle()
    val filter by viewModel.filter.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val showAdminSources by viewModel.showAdminSources.collectAsStateWithLifecycle()

    val spanishCities = listOf(
        "Madrid", "Barcelona", "Valencia", "Sevilla", "Málaga", "Bilbao", "Zaragoza", "Alicante"
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        BrandHeader(
            isOnline = isOnline,
            isSyncing = syncStatus.isSyncing,
            themeMode = themeMode,
            onThemeChange = { viewModel.setThemeMode(it) },
            showAdminSources = showAdminSources,
            onToggleAdminSources = { viewModel.setShowAdminSources(it) },
            onRefreshClick = { viewModel.refresh() }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("home_job_list"),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Search Input Trigger
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onNavigateToSearch)
                            .testTag("home_search_trigger"),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 13.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = BrandGreenPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Buscar puesto, empresa o tecnología...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Quick Modality Filter Chips
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = filter.remoteType == null,
                            onClick = { viewModel.setRemoteTypeFilter(null) },
                            label = { Text("Todas", fontWeight = if (filter.remoteType == null) FontWeight.Bold else FontWeight.Medium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BrandGreenSoft,
                                selectedLabelColor = BrandGreenDark,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = filter.remoteType == null,
                                borderColor = MaterialTheme.colorScheme.outlineVariant,
                                selectedBorderColor = BrandGreenPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                    item {
                        FilterChip(
                            selected = filter.remoteType == "Remoto",
                            onClick = { viewModel.setRemoteTypeFilter("Remoto") },
                            label = { Text("Teletrabajo / Remoto", fontWeight = if (filter.remoteType == "Remoto") FontWeight.Bold else FontWeight.Medium) },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Laptop,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = if (filter.remoteType == "Remoto") BrandGreenDark else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BrandGreenSoft,
                                selectedLabelColor = BrandGreenDark,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = filter.remoteType == "Remoto",
                                borderColor = MaterialTheme.colorScheme.outlineVariant,
                                selectedBorderColor = BrandGreenPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                    item {
                        FilterChip(
                            selected = filter.remoteType == "Híbrido",
                            onClick = { viewModel.setRemoteTypeFilter("Híbrido") },
                            label = { Text("Híbrido", fontWeight = if (filter.remoteType == "Híbrido") FontWeight.Bold else FontWeight.Medium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BrandGreenSoft,
                                selectedLabelColor = BrandGreenDark,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = filter.remoteType == "Híbrido",
                                borderColor = MaterialTheme.colorScheme.outlineVariant,
                                selectedBorderColor = BrandGreenPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                    item {
                        FilterChip(
                            selected = filter.remoteType == "Presencial",
                            onClick = { viewModel.setRemoteTypeFilter("Presencial") },
                            label = { Text("Presencial", fontWeight = if (filter.remoteType == "Presencial") FontWeight.Bold else FontWeight.Medium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BrandGreenSoft,
                                selectedLabelColor = BrandGreenDark,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = filter.remoteType == "Presencial",
                                borderColor = MaterialTheme.colorScheme.outlineVariant,
                                selectedBorderColor = BrandGreenPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }

            // Popular Sectors & Categories (Hostelería, Comercio, Logística, Sanidad, Construcción...)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sectores más demandados",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = onNavigateToSearch) {
                        Text("Ver todos", color = BrandGreenPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(com.example.data.model.JobCategories.ALL_INFOS) { catInfo ->
                        val isSelected = filter.category == catInfo.name
                        Surface(
                            modifier = Modifier.clickable {
                                viewModel.setCategoryFilter(if (isSelected) null else catInfo.name)
                                onNavigateToSearch()
                            },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) BrandGreenSoft else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) BrandGreenPrimary else MaterialTheme.colorScheme.outlineVariant
                            ),
                            shadowElevation = if (isSelected) 1.5.dp else 0.5.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = catInfo.emoji, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = catInfo.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) BrandGreenDark else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            // Spanish Cities Horizontal Strip
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ciudades destacadas en España",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(spanishCities) { city ->
                        Surface(
                            modifier = Modifier.clickable {
                                viewModel.updateLocationQuery(city)
                                onNavigateToSearch()
                            },
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            shadowElevation = 0.5.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "📍 $city", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            // Categories Section
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Categorías profesionales",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val categories = listOf(
                        CategoryItem("Tecnología e IT", Icons.Default.Code),
                        CategoryItem("Marketing y Ventas", Icons.Default.TrendingUp),
                        CategoryItem("Diseño y Creatividad", Icons.Default.DesignServices),
                        CategoryItem("Atención al Cliente", Icons.Default.HeadsetMic),
                        CategoryItem("Operaciones", Icons.Default.LocalShipping)
                    )
                    items(categories) { cat ->
                        Card(
                            modifier = Modifier
                                .width(140.dp)
                                .clickable {
                                    viewModel.setCategoryFilter(cat.title)
                                    onNavigateToSearch()
                                },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(BrandGreenContainer, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = cat.icon,
                                        contentDescription = null,
                                        tint = BrandGreenDark,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = cat.title,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }

            // Unified "Ofertas" Section (Spain and Europe together)
            item {
                Spacer(modifier = Modifier.height(16.dp))
                com.example.ui.components.AdMobBanner(
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ofertas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = {
                        viewModel.resetFilters()
                        onNavigateToSearch()
                    }) {
                        Text("Ver todas", color = BrandGreenPrimary)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Job Cards
            if (allJobs.isEmpty()) {
                item {
                    Text(
                        text = "Cargando ofertas de empleo...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(allJobs.take(15), key = { it.id }) { job ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        JobCard(
                            job = job,
                            onClick = { onJobClick(job) },
                            onSaveToggle = { viewModel.toggleSave(job.id) }
                        )
                    }
                }
            }
        }
    }
}

private data class CategoryItem(val title: String, val icon: ImageVector)

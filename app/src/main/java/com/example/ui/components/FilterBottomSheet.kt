package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.JobFilter
import com.example.ui.theme.BrandGreenPrimary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
    filter: JobFilter,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSetCountry: (String) -> Unit,
    onSetRemoteType: (String?) -> Unit,
    onSetEmploymentType: (String?) -> Unit,
    onSetCategory: (String?) -> Unit,
    onReset: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filtros de empleo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Geographic Scope
            Text(
                text = "Ámbito geográfico",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = filter.country == "ES",
                    onClick = { onSetCountry(if (filter.country == "ES") "All" else "ES") },
                    label = { Text("🇪🇸 España") },
                    modifier = Modifier.testTag("filter_country_es")
                )
                FilterChip(
                    selected = filter.country == "EU",
                    onClick = { onSetCountry(if (filter.country == "EU") "All" else "EU") },
                    label = { Text("🇪🇺 Europa") },
                    modifier = Modifier.testTag("filter_country_eu")
                )
                FilterChip(
                    selected = filter.country == "All",
                    onClick = { onSetCountry("All") },
                    label = { Text("🌍 Todo") },
                    modifier = Modifier.testTag("filter_country_all")
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Modality / Remote
            Text(
                text = "Modalidad de trabajo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val modalities = listOf("Remoto", "Híbrido", "Presencial")
                modalities.forEach { modality ->
                    FilterChip(
                        selected = filter.remoteType == modality,
                        onClick = { onSetRemoteType(modality) },
                        label = { Text(modality) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BrandGreenPrimary.copy(alpha = 0.15f),
                            selectedLabelColor = BrandGreenPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Employment Type
            Text(
                text = "Tipo de contrato / Jornada",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val contracts = listOf(
                    "Jornada completa",
                    "Media jornada",
                    "Contrato indefinido",
                    "Prácticas",
                    "Temporal"
                )
                contracts.forEach { contract ->
                    FilterChip(
                        selected = filter.employmentType == contract,
                        onClick = { onSetEmploymentType(contract) },
                        label = { Text(contract) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Professional Category
            Text(
                text = "Sector profesional",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                com.example.data.model.JobCategories.ALL_INFOS.forEach { catInfo ->
                    val isSelected = filter.category == catInfo.name
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSetCategory(if (isSelected) null else catInfo.name) },
                        label = { Text("${catInfo.emoji} ${catInfo.name}") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BrandGreenPrimary.copy(alpha = 0.15f),
                            selectedLabelColor = BrandGreenPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onReset,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("filter_reset_button")
                ) {
                    Text("Restablecer")
                }
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("filter_apply_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandGreenPrimary)
                ) {
                    Text("Ver resultados")
                }
            }
        }
    }
}

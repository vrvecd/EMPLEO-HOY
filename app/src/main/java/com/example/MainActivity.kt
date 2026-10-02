package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.JobDetailScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SourcesScreen
import com.example.ui.theme.BrandGreenDark
import com.example.ui.theme.BrandGreenPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.JobViewModel

enum class NavigationTab(val label: String, val icon: ImageVector, val tag: String) {
    EXPLORE("Explorar", Icons.Default.Home, "tab_explore"),
    SEARCH("Buscar", Icons.Default.Search, "tab_search"),
    SAVED("Guardados", Icons.Default.Bookmark, "tab_saved"),
    SOURCES("Fuentes", Icons.Default.RssFeed, "tab_sources")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: JobViewModel = viewModel()
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

            MyApplicationTheme(themeMode = themeMode) {
                EmpleoHoyApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun EmpleoHoyApp(
    viewModel: JobViewModel = viewModel()
) {
    var currentTab by rememberSaveable { mutableStateOf(NavigationTab.EXPLORE) }
    val selectedJob by viewModel.selectedJob.collectAsStateWithLifecycle()
    val savedJobs by viewModel.savedJobs.collectAsStateWithLifecycle()
    val showAdminSources by viewModel.showAdminSources.collectAsStateWithLifecycle()

    // Sources panel is visible in AI Studio Preview / Debug build, and hidden for users in release
    val isEditorMode = BuildConfig.DEBUG
    val availableTabs = remember(isEditorMode, showAdminSources) {
        if (isEditorMode && showAdminSources) {
            NavigationTab.entries
        } else {
            NavigationTab.entries.filter { it != NavigationTab.SOURCES }
        }
    }

    if ((!isEditorMode || !showAdminSources) && currentTab == NavigationTab.SOURCES) {
        currentTab = NavigationTab.EXPLORE
    }

    if (selectedJob != null) {
        JobDetailScreen(
            job = selectedJob!!,
            onBack = { viewModel.selectJob(null) },
            onToggleSave = { viewModel.toggleSave(selectedJob!!.id) }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    availableTabs.forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentTab = tab },
                            icon = {
                                if (tab == NavigationTab.SAVED && savedJobs.isNotEmpty()) {
                                    BadgedBox(
                                        badge = {
                                            Badge(containerColor = BrandGreenPrimary) {
                                                Text(savedJobs.size.toString())
                                            }
                                        }
                                    ) {
                                        Icon(imageVector = tab.icon, contentDescription = tab.label)
                                    }
                                } else {
                                    Icon(imageVector = tab.icon, contentDescription = tab.label)
                                }
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = BrandGreenDark,
                                selectedTextColor = BrandGreenDark,
                                indicatorColor = BrandGreenPrimary.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.testTag(tab.tag)
                        )
                    }
                }
            }
        ) { innerPadding ->
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "TabContentTransition"
            ) { targetTab ->
                when (targetTab) {
                    NavigationTab.EXPLORE -> {
                        HomeScreen(
                            viewModel = viewModel,
                            onJobClick = { viewModel.selectJob(it) },
                            onNavigateToSearch = { currentTab = NavigationTab.SEARCH },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    NavigationTab.SEARCH -> {
                        SearchScreen(
                            viewModel = viewModel,
                            onJobClick = { viewModel.selectJob(it) },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    NavigationTab.SAVED -> {
                        SavedScreen(
                            viewModel = viewModel,
                            onJobClick = { viewModel.selectJob(it) },
                            onNavigateToExplore = { currentTab = NavigationTab.EXPLORE },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    NavigationTab.SOURCES -> {
                        SourcesScreen(
                            viewModel = viewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

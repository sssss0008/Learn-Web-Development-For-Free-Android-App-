package com.example.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CurriculumRepository
import com.example.data.repository.DevToolsRepository
import com.example.data.repository.ProjectsRepository
import com.example.ui.screens.*
import com.example.ui.theme.*

enum class NavDestination(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Dashboard),
    LEARN("Learn", Icons.Default.School),
    CODE("Code", Icons.Default.Code),
    PROJECTS("Projects", Icons.Default.Layers),
    TOOLS("Tools", Icons.Default.Build),
    AI_TUTOR("AI Tutor", Icons.Default.Psychology),
    CAREER("Career", Icons.Default.WorkspacePremium),
    COMMUNITY("Community", Icons.Default.Groups),
    PROFILE("Profile", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebDevApp() {
    var currentDestination by remember { mutableStateOf(NavDestination.HOME) }
    var isDarkTheme by remember { mutableStateOf(true) }
    var aiPromptContext by remember { mutableStateOf("") }
    var showSearchDialog by remember { mutableStateOf(false) }

    WebDevAcademyTheme(darkTheme = isDarkTheme) {
        if (showSearchDialog) {
            GlobalSearchModal(
                onDismiss = { showSearchDialog = false },
                onNavigate = { dest ->
                    currentDestination = dest
                    showSearchDialog = false
                }
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(DevSky),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "</>",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = Slate950,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Column {
                                Text(
                                    text = "WebDev Academy",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Learn. Code. Build. Deploy.",
                                    fontSize = 10.sp,
                                    color = DevSky
                                )
                            }
                        }
                    },
                    actions = {
                        // Search Button
                        IconButton(onClick = { showSearchDialog = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Global Search", tint = Color.White)
                        }

                        // AI Tutor shortcut
                        IconButton(onClick = { currentDestination = NavDestination.AI_TUTOR }) {
                            Icon(Icons.Default.Psychology, contentDescription = "AI Tutor", tint = DevSky)
                        }

                        // Tools shortcut
                        IconButton(onClick = { currentDestination = NavDestination.TOOLS }) {
                            Icon(Icons.Default.Build, contentDescription = "Dev Tools", tint = DevIndigoLight)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Slate850,
                        titleContentColor = Color.White
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Slate850,
                    tonalElevation = 8.dp
                ) {
                    val mainTabs = listOf(
                        NavDestination.HOME,
                        NavDestination.LEARN,
                        NavDestination.CODE,
                        NavDestination.PROJECTS,
                        NavDestination.TOOLS,
                        NavDestination.PROFILE
                    )

                    mainTabs.forEach { destination ->
                        val isSelected = currentDestination == destination
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentDestination = destination },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.title,
                                    tint = if (isSelected) DevSky else Slate400
                                )
                            },
                            label = {
                                Text(
                                    text = destination.title,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) DevSky else Slate400
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Slate800
                            )
                        )
                    }
                }
            },
            floatingActionButton = {
                if (currentDestination != NavDestination.AI_TUTOR) {
                    FloatingActionButton(
                        onClick = { currentDestination = NavDestination.AI_TUTOR },
                        containerColor = DevSky,
                        contentColor = Slate950,
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = "Ask AI Mentor")
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Slate900)
            ) {
                Crossfade(targetState = currentDestination, label = "ScreenTransition") { destination ->
                    when (destination) {
                        NavDestination.HOME -> HomeScreen(
                            onNavigateToLearn = { currentDestination = NavDestination.LEARN },
                            onNavigateToCode = { currentDestination = NavDestination.CODE },
                            onNavigateToProjects = { currentDestination = NavDestination.PROJECTS },
                            onNavigateToAiTutor = { currentDestination = NavDestination.AI_TUTOR },
                            onNavigateToProfile = { currentDestination = NavDestination.PROFILE }
                        )
                        NavDestination.LEARN -> LearnScreen(
                            onNavigateToAiTutor = { prompt ->
                                aiPromptContext = prompt
                                currentDestination = NavDestination.AI_TUTOR
                            }
                        )
                        NavDestination.CODE -> CodeScreen(
                            onNavigateToAiTutor = { prompt ->
                                aiPromptContext = prompt
                                currentDestination = NavDestination.AI_TUTOR
                            }
                        )
                        NavDestination.PROJECTS -> ProjectsScreen()
                        NavDestination.TOOLS -> ToolsScreen()
                        NavDestination.AI_TUTOR -> AiTutorScreen(initialPrompt = aiPromptContext)
                        NavDestination.CAREER -> CareerScreen()
                        NavDestination.COMMUNITY -> CommunityScreen()
                        NavDestination.PROFILE -> ProfileScreen(
                            onToggleTheme = { isDarkTheme = !isDarkTheme },
                            isDarkTheme = isDarkTheme
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GlobalSearchModal(
    onDismiss: () -> Unit,
    onNavigate: (NavDestination) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val matchedLessons = remember(searchQuery) {
        if (searchQuery.isBlank()) emptyList()
        else CurriculumRepository.roadmapLevels.flatMap { it.lessons }.filter {
            it.title.contains(searchQuery, ignoreCase = true) || it.overview.contains(searchQuery, ignoreCase = true)
        }
    }

    val matchedTools = remember(searchQuery) {
        if (searchQuery.isBlank()) emptyList()
        else DevToolsRepository.toolsList.filter {
            it.name.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true)
        }
    }

    val matchedProjects = remember(searchQuery) {
        if (searchQuery.isBlank()) emptyList()
        else ProjectsRepository.projectsList.filter {
            it.title.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Global Academy Search", fontWeight = FontWeight.Bold, color = DevSky)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search lessons, tools, projects, SQL, HTTP...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (searchQuery.isBlank()) {
                        item {
                            Text("Try searching for 'Flexbox', 'Async', 'JSON', 'Docker', or 'SQL'.", color = Slate400, fontSize = 12.sp)
                        }
                    }

                    // Lessons
                    if (matchedLessons.isNotEmpty()) {
                        item {
                            Text("LESSONS (${matchedLessons.size})", fontWeight = FontWeight.Bold, color = DevSky, fontSize = 11.sp)
                        }
                        items(matchedLessons.take(3)) { lesson ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Slate900,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onNavigate(NavDestination.LEARN) }
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(lesson.title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                    Text("Level ${lesson.levelNumber} • ${lesson.durationMinutes} min", color = Slate400, fontSize = 10.sp)
                                }
                            }
                        }
                    }

                    // Tools
                    if (matchedTools.isNotEmpty()) {
                        item {
                            Text("TOOLS (${matchedTools.size})", fontWeight = FontWeight.Bold, color = DevIndigoLight, fontSize = 11.sp)
                        }
                        items(matchedTools.take(3)) { tool ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Slate900,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onNavigate(NavDestination.TOOLS) }
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(tool.name, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                    Text(tool.description, color = Slate400, fontSize = 10.sp)
                                }
                            }
                        }
                    }

                    // Projects
                    if (matchedProjects.isNotEmpty()) {
                        item {
                            Text("PROJECTS (${matchedProjects.size})", fontWeight = FontWeight.Bold, color = DevEmeraldLight, fontSize = 11.sp)
                        }
                        items(matchedProjects.take(3)) { proj ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Slate900,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onNavigate(NavDestination.PROJECTS) }
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(proj.title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                    Text(proj.description, color = Slate400, fontSize = 10.sp)
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        },
        containerColor = Slate850
    )
}

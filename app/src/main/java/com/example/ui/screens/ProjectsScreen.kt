package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProjectItem
import com.example.data.repository.ProjectsRepository
import com.example.ui.components.CodeBlockView
import com.example.ui.theme.*

@Composable
fun ProjectsScreen() {
    var selectedCategory by remember { mutableStateOf("All") }
    var showGeneratorDialog by remember { mutableStateOf(false) }

    val allProjects = remember { mutableStateListOf(*ProjectsRepository.projectsList.toTypedArray()) }

    val filteredProjects = remember(selectedCategory, allProjects.size) {
        if (selectedCategory == "All") allProjects
        else allProjects.filter { it.category == selectedCategory }
    }

    if (showGeneratorDialog) {
        ProjectGeneratorModal(
            onDismiss = { showGeneratorDialog = false },
            onProjectGenerated = { newProj ->
                allProjects.add(0, newProj)
                showGeneratorDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate900)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Slate850)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Full-Stack Project Lab",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Production Blueprints • Architecture • Milestones",
                        fontSize = 12.sp,
                        color = DevSky,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = { showGeneratorDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = DevSky),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = "Generate", modifier = Modifier.size(16.dp), tint = Slate950)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Generate", color = Slate950, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Category Filter
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Beginner", "Intermediate", "Advanced").forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
        }

        // Projects List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredProjects) { project ->
                ProjectCardView(project = project)
            }
        }
    }
}

@Composable
fun ProjectCardView(project: ProjectItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Category Badge & Hours
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (project.category) {
                        "Beginner" -> DevEmerald.copy(alpha = 0.2f)
                        "Intermediate" -> DevSky.copy(alpha = 0.2f)
                        else -> DevAmber.copy(alpha = 0.2f)
                    }
                ) {
                    Text(
                        text = project.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (project.category) {
                            "Beginner" -> DevEmeraldLight
                            "Intermediate" -> DevSky
                            else -> DevAmberLight
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = "EST. ${project.estimatedHours} HOURS",
                    fontSize = 11.sp,
                    color = Slate400,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = project.description,
                fontSize = 13.sp,
                color = Slate300,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Tech stack chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                project.technologies.forEach { tech ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Slate900,
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text(
                            text = tech,
                            fontSize = 11.sp,
                            color = DevSky,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Architecture Flow diagram box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Slate950)
                    .border(1.dp, Slate800, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        text = "SYSTEM ARCHITECTURE FLOW",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = DevIndigoLight,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = project.architectureFlow,
                        fontSize = 11.sp,
                        color = Slate200,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Milestones checklist
            Text("Development Milestones:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate200)
            Column(
                modifier = Modifier.padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                project.milestones.forEach { milestone ->
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(
                            imageVector = if (milestone.completed) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = "Status",
                            tint = if (milestone.completed) DevEmeraldLight else Slate500,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = milestone.title,
                            fontSize = 12.sp,
                            color = if (milestone.completed) Slate200 else Slate400
                        )
                    }
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(10.dp))
                Text("Code Starter Template:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Slate200)
                Spacer(modifier = Modifier.height(4.dp))
                CodeBlockView(code = project.sampleSnippet, language = "javascript")
            }

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (expanded) "Hide Code Details" else "View Code & Blueprint", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ProjectGeneratorModal(
    onDismiss: () -> Unit,
    onProjectGenerated: (ProjectItem) -> Unit
) {
    var selectedFrontend by remember { mutableStateOf("React 19") }
    var selectedBackend by remember { mutableStateOf("Node.js / Express") }
    var selectedDatabase by remember { mutableStateOf("PostgreSQL") }
    var selectedType by remember { mutableStateOf("SaaS Dashboard") }
    var selectedDifficulty by remember { mutableStateOf("Intermediate") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Full-Stack Project Generator", fontWeight = FontWeight.Bold, color = DevSky)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Choose your technical preferences to generate a custom roadmap blueprint:", fontSize = 12.sp, color = Slate300)

                // Frontend
                Text("Frontend Framework", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate400)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("React 19", "Next.js", "Vue 3", "Vanilla HTML/JS").forEach { f ->
                        FilterChip(selected = selectedFrontend == f, onClick = { selectedFrontend = f }, label = { Text(f, fontSize = 11.sp) })
                    }
                }

                // Backend
                Text("Backend Engine", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate400)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Node.js / Express", "FastAPI Python", "Laravel PHP", "Spring Boot").forEach { b ->
                        FilterChip(selected = selectedBackend == b, onClick = { selectedBackend = b }, label = { Text(b, fontSize = 11.sp) })
                    }
                }

                // Database
                Text("Database Engine", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate400)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("PostgreSQL", "MongoDB", "SQLite", "MySQL").forEach { d ->
                        FilterChip(selected = selectedDatabase == d, onClick = { selectedDatabase = d }, label = { Text(d, fontSize = 11.sp) })
                    }
                }

                // Project Type
                Text("Application Domain", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate400)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("SaaS Dashboard", "E-Commerce", "Social Platform", "Real-Time Chat").forEach { t ->
                        FilterChip(selected = selectedType == t, onClick = { selectedType = t }, label = { Text(t, fontSize = 11.sp) })
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val generated = ProjectsRepository.generateCustomProject(
                        frontend = selectedFrontend,
                        backend = selectedBackend,
                        database = selectedDatabase,
                        projectType = selectedType,
                        difficulty = selectedDifficulty
                    )
                    onProjectGenerated(generated)
                },
                colors = ButtonDefaults.buttonColors(containerColor = DevSky)
            ) {
                Text("Generate Blueprint", color = Slate950, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        containerColor = Slate850
    )
}

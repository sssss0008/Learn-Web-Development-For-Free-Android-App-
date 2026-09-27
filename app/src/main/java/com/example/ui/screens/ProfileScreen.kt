package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.CurriculumRepository
import com.example.data.repository.UserProgressRepository
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    onToggleTheme: () -> Unit = {},
    isDarkTheme: Boolean = true
) {
    val context = LocalContext.current
    val progressState by UserProgressRepository.state.collectAsState()
    var isEditingName by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf(progressState.studentName) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate900)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Creator Spotlight Section: Awiskar Acharya
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Slate850)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(DevIndigoDark.copy(alpha = 0.5f), Slate850)
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = DevIndigo.copy(alpha = 0.2f),
                                border = ButtonDefaults.outlinedButtonBorder
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified Creator",
                                        tint = DevSky,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "PLATFORM ARCHITECT & CREATOR",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DevSky
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/awiskaracharya/"))
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = DevSky),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Link, contentDescription = "LinkedIn", modifier = Modifier.size(14.dp), tint = Slate950)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("LinkedIn Profile", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate950)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(DevSky)
                                    .border(2.dp, DevAmberLight, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "AA",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Slate950,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            Column {
                                Text(
                                    text = "Awiskar Acharya",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Lead Web Architect & Engineering Educator",
                                    fontSize = 12.sp,
                                    color = DevSky
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Creator of WebDev Academy. Passionate about structuring accessible, high-performance web systems and mentoring the next generation of full-stack software engineers.",
                            fontSize = 12.sp,
                            color = Slate300,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Slate950)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("17", fontWeight = FontWeight.Bold, color = DevEmeraldLight, fontSize = 16.sp)
                                Text("Levels", fontSize = 11.sp, color = Slate400)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("50+", fontWeight = FontWeight.Bold, color = DevSky, fontSize = 16.sp)
                                Text("Topics", fontSize = 11.sp, color = Slate400)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("100%", fontWeight = FontWeight.Bold, color = DevAmberLight, fontSize = 16.sp)
                                Text("Full-Stack", fontSize = 11.sp, color = Slate400)
                            }
                        }
                    }
                }
            }
        }

        // Student Profile Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Slate850)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(DevIndigoLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = progressState.studentName.take(1).uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Slate950
                                )
                            }

                            Column {
                                if (isEditingName) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        OutlinedTextField(
                                            value = tempName,
                                            onValueChange = { tempName = it },
                                            modifier = Modifier.width(160.dp),
                                            singleLine = true
                                        )
                                        IconButton(onClick = {
                                            UserProgressRepository.setStudentName(tempName)
                                            isEditingName = false
                                        }) {
                                            Icon(Icons.Default.Check, contentDescription = "Save", tint = DevEmeraldLight)
                                        }
                                    }
                                } else {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = progressState.studentName,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        IconButton(onClick = { isEditingName = true }, modifier = Modifier.size(28.dp)) {
                                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Slate400, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                }
                                Text(
                                    text = progressState.levelTitle,
                                    fontSize = 12.sp,
                                    color = DevSky
                                )
                            }
                        }

                        // Daily Coding Minutes
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Slate900
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text("${progressState.codingMinutesToday} MIN", fontWeight = FontWeight.Bold, color = DevEmeraldLight, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                                Text("CODED TODAY", fontSize = 9.sp, color = Slate500, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Progress breakdown
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Weekly Target Progress", fontSize = 12.sp, color = Slate300)
                        Text(
                            "${progressState.weeklyLessonsCompleted} / ${progressState.weeklyLessonsTarget} Lessons",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = DevSky,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { progressState.weeklyLessonsCompleted.toFloat() / progressState.weeklyLessonsTarget.toFloat() },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                        color = DevSky,
                        trackColor = Slate900
                    )
                }
            }
        }

        // Bookmarked Lessons Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Slate850)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Bookmarked Lessons", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DevAmberLight)
                        Text("${progressState.bookmarkedLessonIds.size} saved", fontSize = 12.sp, color = Slate400)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (progressState.bookmarkedLessonIds.isEmpty()) {
                        Text("No bookmarks yet. Tap the bookmark icon on any lesson to save it here.", fontSize = 12.sp, color = Slate500)
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            progressState.bookmarkedLessonIds.forEach { id ->
                                val lesson = CurriculumRepository.roadmapLevels.flatMap { it.lessons }.find { it.id == id }
                                if (lesson != null) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Slate900)
                                            .padding(10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(lesson.title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                            Text("Level ${lesson.levelNumber} • ${lesson.language}", color = Slate400, fontSize = 11.sp)
                                        }
                                        Icon(Icons.Default.Bookmark, contentDescription = "Saved", tint = DevAmberLight, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Personal Saved Notes Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Slate850)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Study Notes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DevSky)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (progressState.notes.isEmpty()) {
                        Text("No notes recorded yet. Add notes inside lesson viewer.", fontSize = 12.sp, color = Slate500)
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            progressState.notes.forEach { (lessonId, note) ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Slate900)
                                        .padding(10.dp)
                                ) {
                                    Text("Lesson #$lessonId Note:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DevSky)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(note, fontSize = 12.sp, color = Slate200, lineHeight = 17.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Application Settings
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Slate850)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Developer Preferences", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Dark Theme First", fontWeight = FontWeight.SemiBold, color = Slate200, fontSize = 13.sp)
                            Text("Optimized for code readability", fontSize = 11.sp, color = Slate400)
                        }
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { onToggleTheme() },
                            colors = SwitchDefaults.colors(checkedThumbColor = DevSky, checkedTrackColor = DevIndigoDark)
                        )
                    }

                    Divider(color = Slate800, modifier = Modifier.padding(vertical = 6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Offline Course Caching", fontWeight = FontWeight.SemiBold, color = Slate200, fontSize = 13.sp)
                            Text("All lessons available without network", fontSize = 11.sp, color = Slate400)
                        }
                        Switch(
                            checked = true,
                            onCheckedChange = { Toast.makeText(context, "Offline mode always active", Toast.LENGTH_SHORT).show() },
                            colors = SwitchDefaults.colors(checkedThumbColor = DevEmeraldLight, checkedTrackColor = Color(0xFF064E3B))
                        )
                    }
                }
            }
        }
    }
}

package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.data.repository.ProjectsRepository
import com.example.data.repository.UserProgressRepository
import com.example.ui.components.SkillRadarView
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToLearn: () -> Unit,
    onNavigateToCode: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToAiTutor: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val context = LocalContext.current
    val progressState by UserProgressRepository.state.collectAsState()
    val dailyChallenge = CurriculumRepository.dailyChallenges.first()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate900)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Developer Greeting & Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Good morning, ${progressState.studentName}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(DevEmeraldLight)
                        )
                    }
                    Text(
                        text = "WebDev Academy • Learn. Code. Build. Deploy.",
                        fontSize = 13.sp,
                        color = DevSky,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Profile Avatar button
                IconButton(
                    onClick = onNavigateToProfile,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Slate800)
                        .border(1.dp, DevSky.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = DevSky
                    )
                }
            }
        }

        // Creator Spotlight Card: Awiskar Acharya
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
                        .padding(16.dp)
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
                                        text = "PLATFORM CREATOR",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DevSky
                                    )
                                }
                            }

                            // LinkedIn link button
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
                                Text("Awiskar Acharya", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Slate950)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Created by Awiskar Acharya",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Lead Web Architect & Engineering Educator. Dedicated to empowering developers with complete mastery from fundamentals to production full-stack systems.",
                            fontSize = 12.sp,
                            color = Slate300,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        // Quick Stats Row: XP, Streak, Level, Solved
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // XP Card
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Slate850)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.Bolt, contentDescription = "XP", tint = DevAmberLight, modifier = Modifier.size(18.dp))
                            Text("Total XP", fontSize = 11.sp, color = Slate400)
                        }
                        Text(
                            text = "${progressState.xp}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // Streak Card
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Slate850)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.LocalFireDepartment, contentDescription = "Streak", tint = Color(0xFFF97316), modifier = Modifier.size(18.dp))
                            Text("Streak", fontSize = 11.sp, color = Slate400)
                        }
                        Text(
                            text = "${progressState.currentStreak} Days",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // Certifications Card
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Slate850)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.WorkspacePremium, contentDescription = "Certs", tint = DevEmeraldLight, modifier = Modifier.size(18.dp))
                            Text("Certs", fontSize = 11.sp, color = Slate400)
                        }
                        Text(
                            text = "${progressState.certificates.size} Issued",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }

        // Continue Learning Card
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
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = DevSky.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "CURRENT COURSE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = DevSky,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Text("Progress: 67%", fontSize = 12.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Level 4: JavaScript Mastery & Async",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Promises, Async/Await, Error Handling & DOM manipulation.",
                        fontSize = 13.sp,
                        color = Slate300,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 0.67f },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                        color = DevSky,
                        trackColor = Slate900
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onNavigateToLearn,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = DevIndigo),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Continue", modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Continue Lesson", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }

        // Daily Challenge Card
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
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Code, contentDescription = "Challenge", tint = DevAmberLight, modifier = Modifier.size(18.dp))
                            Text(
                                text = "DAILY CHALLENGE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DevAmberLight
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = DevAmber.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "+${dailyChallenge.xpReward} XP",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DevAmberLight,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dailyChallenge.title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = dailyChallenge.problem,
                        fontSize = 13.sp,
                        color = Slate300,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onNavigateToCode,
                        colors = ButtonDefaults.buttonColors(containerColor = DevAmber),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Terminal, contentDescription = "Solve", modifier = Modifier.size(16.dp), tint = Slate950)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Solve in Code Playground", color = Slate950, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // Active Project Milestone Card
        item {
            val activeProject = ProjectsRepository.projectsList.first()
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
                        Text(
                            text = "PROJECT LAB WORKSPACE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DevEmeraldLight
                        )
                        Text("Milestone 2/3", fontSize = 12.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = activeProject.title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = activeProject.description,
                        fontSize = 13.sp,
                        color = Slate300,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onNavigateToProjects,
                        colors = ButtonDefaults.buttonColors(containerColor = DevEmerald),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Layers, contentDescription = "Projects", modifier = Modifier.size(16.dp), tint = Slate950)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open Project Laboratory", color = Slate950, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // Skill Radar Matrix
        item {
            SkillRadarView()
        }
    }
}

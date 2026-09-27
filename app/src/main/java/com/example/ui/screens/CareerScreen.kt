package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
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
import com.example.data.model.Certificate
import com.example.data.repository.UserProgressRepository
import com.example.ui.components.CodeBlockView
import com.example.ui.theme.*

@Composable
fun CareerScreen() {
    val context = LocalContext.current
    val progressState by UserProgressRepository.state.collectAsState()
    var activeCareerTab by remember { mutableStateOf("Certificates") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate900)
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Slate850)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Career & Certification Center",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Verified Credentials • Resume Builder • Portfolio • Interview Prep",
                fontSize = 12.sp,
                color = DevSky,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Career Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Certificates", "Resume Builder", "Portfolio Generator", "Roadmaps").forEach { tab ->
                    FilterChip(
                        selected = activeCareerTab == tab,
                        onClick = { activeCareerTab = tab },
                        label = { Text(tab, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
        }

        // Active Tab Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (activeCareerTab) {
                "Certificates" -> {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Your Verified Certificates",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Button(
                                onClick = {
                                    UserProgressRepository.issueCertificate("Full-Stack Web Architect", "FSWA")
                                    Toast.makeText(context, "Full-Stack Certificate issued!", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = DevEmerald),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.WorkspacePremium, contentDescription = "Claim", modifier = Modifier.size(16.dp), tint = Slate950)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Claim New Cert", color = Slate950, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }
                    }

                    items(progressState.certificates) { cert ->
                        OfficialCertificateCard(cert = cert)
                    }
                }

                "Resume Builder" -> {
                    item {
                        DeveloperResumeBuilderCard(studentName = progressState.studentName)
                    }
                }

                "Portfolio Generator" -> {
                    item {
                        PortfolioGeneratorCard(studentName = progressState.studentName)
                    }
                }

                "Roadmaps" -> {
                    item {
                        CareerRoadmapCard()
                    }
                }
            }
        }
    }
}

@Composable
fun OfficialCertificateCard(cert: Certificate) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                    )
                )
                .border(1.dp, DevAmberLight.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Header badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "WEBDEV ACADEMY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DevAmberLight,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = DevEmerald.copy(alpha = 0.2f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Verified, contentDescription = "Verified", tint = DevEmeraldLight, modifier = Modifier.size(14.dp))
                            Text("VERIFIED CREDENTIAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DevEmeraldLight)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "CERTIFICATE OF ACHIEVEMENT",
                    fontSize = 12.sp,
                    color = Slate400,
                    letterSpacing = 1.sp
                )

                Text(
                    text = cert.studentName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = "has successfully mastered and demonstrated production competency in",
                    fontSize = 12.sp,
                    color = Slate300
                )

                Text(
                    text = cert.courseName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DevSky,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Divider(color = Slate700, modifier = Modifier.padding(vertical = 12.dp))

                // Signatures & verification footer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text("SIGNATURE:", fontSize = 9.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                        Text(
                            text = cert.creatorName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = DevAmberLight
                        )
                        Text(
                            text = cert.creatorTitle,
                            fontSize = 10.sp,
                            color = Slate400
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("VERIFICATION ID:", fontSize = 9.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                        Text(
                            text = cert.verificationCode,
                            fontSize = 11.sp,
                            color = DevSky,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = cert.issuedDate,
                            fontSize = 10.sp,
                            color = Slate400
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeveloperResumeBuilderCard(studentName: String) {
    var role by remember { mutableStateOf("Full-Stack Web Developer") }
    var skills by remember { mutableStateOf("TypeScript, React, Next.js, Node.js, Express, PostgreSQL, Docker, Git") }
    var experience by remember { mutableStateOf("Built production SaaS Task Management System with WebDev Academy. Implemented JWT auth, Prisma ORM, and automated Vitest suite.") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ATS-Friendly Developer Resume Builder", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DevSky)
            Text("Format developer credentials for engineering hiring managers.", fontSize = 13.sp, color = Slate400, modifier = Modifier.padding(vertical = 4.dp))

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = role,
                onValueChange = { role = it },
                label = { Text("Target Engineering Role") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = skills,
                onValueChange = { skills = it },
                label = { Text("Core Technologies & Languages") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = experience,
                onValueChange = { experience = it },
                label = { Text("Projects & Experience Highlights") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text("Generated Resume Preview (Markdown):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DevIndigoLight)
            Spacer(modifier = Modifier.height(4.dp))

            val resumeMarkdown = """
                # $studentName
                **$role** | Mentored by Awiskar Acharya (WebDev Academy)
                
                ### Technical Skills
                $skills
                
                ### Featured Projects
                - **WebDev Academy Capstone**: Full-stack web application
                - **Experience**: $experience
                
                ### Certifications
                - WebDev Academy Verified Credentials
            """.trimIndent()

            CodeBlockView(code = resumeMarkdown, language = "markdown")
        }
    }
}

@Composable
fun PortfolioGeneratorCard(studentName: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Modern Developer Portfolio Generator", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DevSky)
            Text("Ready-to-deploy static HTML/CSS/JS portfolio template.", fontSize = 13.sp, color = Slate400, modifier = Modifier.padding(vertical = 4.dp))

            Spacer(modifier = Modifier.height(10.dp))
            val portfolioCode = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8">
                  <title>$studentName | Full-Stack Engineer</title>
                  <link rel="stylesheet" href="styles.css">
                </head>
                <body>
                  <nav><h2>$studentName</h2><a href="#projects">Work</a></nav>
                  <header>
                    <h1>Building Web Experiences That Scale</h1>
                    <p>Graduate of WebDev Academy (Mentored by Awiskar Acharya)</p>
                  </header>
                </body>
                </html>
            """.trimIndent()

            CodeBlockView(code = portfolioCode, language = "html")
        }
    }
}

@Composable
fun CareerRoadmapCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Engineering Career Tracks", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DevSky)
            Spacer(modifier = Modifier.height(10.dp))

            listOf(
                "Frontend Engineer" to "HTML5, Modern CSS, Responsive Design, TypeScript, React 19, Next.js, Web Performance, Accessibility",
                "Backend Engineer" to "Node.js, Express, REST APIs, PostgreSQL, Schema Design, JWT Auth, Docker, Microservices",
                "Full-Stack Architect" to "All 17 WebDev Academy levels: Frontend + Backend + Databases + CI/CD + Capstone Project"
            ).forEach { (track, stack) ->
                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                    Text(track, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                    Text(stack, color = Slate300, fontSize = 12.sp, lineHeight = 18.sp)
                    Divider(color = Slate800, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}

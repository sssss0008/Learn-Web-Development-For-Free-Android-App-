package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CodeBlockView
import com.example.ui.theme.*

data class AiChatMessage(
    val sender: String, // "User" or "AI Mentor"
    val content: String,
    val codeBlock: String? = null,
    val isAnalysisCard: Boolean = false
)

@Composable
fun AiTutorScreen(initialPrompt: String = "") {
    var selectedMode by remember { mutableStateOf("Explain Code") }
    var userInput by remember {
        mutableStateOf(if (initialPrompt.isNotBlank()) initialPrompt else "const promise = fetch('/api/courses');\nconst data = await promise.json();")
    }

    val messages = remember {
        mutableStateListOf(
            AiChatMessage(
                sender = "AI Mentor",
                content = "👋 Hello Developer! I am your WebDev Academy AI Mentor, trained on Awiskar Acharya's engineering curriculum. Select a mode below to explain code, diagnose errors, review architecture, or practice mock technical interviews."
            )
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
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Psychology, contentDescription = "AI", tint = DevSky, modifier = Modifier.size(24.dp))
                Column {
                    Text(
                        text = "AI Coding Tutor & Debugger",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Real-time explanations, root cause analysis & code reviews",
                        fontSize = 11.sp,
                        color = DevSky,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Modes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Explain Code", "AI Debugger", "Code Review", "Mock Interview").forEach { mode ->
                    FilterChip(
                        selected = selectedMode == mode,
                        onClick = { selectedMode = mode },
                        label = { Text(mode, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
        }

        // Chat conversation
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages.size) { index ->
                val msg = messages[index]
                val isAi = msg.sender == "AI Mentor"

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isAi) Slate850 else DevIndigoDark.copy(alpha = 0.4f)
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (isAi) Icons.Default.AutoAwesome else Icons.Default.Psychology,
                                contentDescription = msg.sender,
                                tint = if (isAi) DevSky else DevIndigoLight,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = msg.sender,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (isAi) DevSky else DevIndigoLight
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = msg.content,
                            fontSize = 13.sp,
                            color = Slate200,
                            lineHeight = 20.sp
                        )

                        if (msg.codeBlock != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            CodeBlockView(code = msg.codeBlock, language = "javascript")
                        }
                    }
                }
            }
        }

        // Input bottom bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Slate850)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Ask question, paste code, or enter error message...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 5,
                textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DevSky,
                    unfocusedBorderColor = Slate700
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Quick suggestions
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    AssistChip(
                        onClick = {
                            userInput = "TypeError: Cannot read properties of undefined (reading 'map')"
                            selectedMode = "AI Debugger"
                        },
                        label = { Text("Debug TypeError", fontSize = 11.sp) }
                    )
                    AssistChip(
                        onClick = {
                            userInput = "Review this authentication middleware:\nconst auth = (req,res,next) => { if (!req.headers.token) return res.status(401); next(); }"
                            selectedMode = "Code Review"
                        },
                        label = { Text("Review Code", fontSize = 11.sp) }
                    )
                }

                Button(
                    onClick = {
                        val input = userInput.trim()
                        if (input.isNotBlank()) {
                            messages.add(AiChatMessage("User", input))
                            userInput = ""

                            // Generate response according to mode
                            val reply = when (selectedMode) {
                                "Explain Code" -> AiChatMessage(
                                    sender = "AI Mentor",
                                    content = """
                                        🧠 **Concept Analysis & Architecture Breakdown**:
                                        
                                        1. **Single-threaded Asynchronous Flow**: This code leverages modern ES6 `async/await` to handle network I/O without blocking the browser UI thread.
                                        2. **Promise Chain**: `fetch()` returns a Response Promise. The `await` keyword pauses execution of the local async function until the HTTP status headers resolve.
                                        3. **Body Parsing**: `.json()` reads the response stream to completion and parses the payload into a native JavaScript object.
                                        4. **Production Best Practice**: Always wrap in a `try...catch` block and verify `response.ok` before reading JSON!
                                    """.trimIndent(),
                                    codeBlock = """
                                        async function safeFetchCourses() {
                                          try {
                                            const response = await fetch('/api/courses');
                                            if (!response.ok) throw new Error('HTTP ' + response.status);
                                            return await response.json();
                                          } catch (err) {
                                            console.error('Fetch error:', err);
                                          }
                                        }
                                    """.trimIndent()
                                )

                                "AI Debugger" -> AiChatMessage(
                                    sender = "AI Mentor",
                                    content = """
                                        🚨 **ERROR DETECTED**: Runtime Null/Undefined Access
                                        
                                        🔍 **Root Cause Analysis**:
                                        You are invoking `.map()` on a variable that is either `undefined` or still `null` while awaiting an asynchronous network response.
                                        
                                        🛠️ **Recommended 3-Step Fix**:
                                        1. **Default Array State**: Initialize state with empty array: `useState([])`.
                                        2. **Optional Chaining**: Use `items?.map(...)` to safely short-circuit when data is loading.
                                        3. **Guard Clause**: Add an explicit check `if (!Array.isArray(items)) return <LoadingSpinner />;`.
                                    """.trimIndent(),
                                    codeBlock = """
                                        // Safe rendering pattern
                                        const CourseList = ({ courses = [] }) => {
                                          if (!courses || courses.length === 0) {
                                            return <p>No courses found.</p>;
                                          }
                                          return courses.map(c => <div key={c.id}>{c.title}</div>);
                                        };
                                    """.trimIndent()
                                )

                                "Code Review" -> AiChatMessage(
                                    sender = "AI Mentor",
                                    content = """
                                        📊 **PROJECT CODE REVIEW SCORECARD**:
                                        
                                        • Readability:      9 / 10
                                        • Architecture:     8 / 10
                                        • Web Security:     7 / 10
                                        • Performance:      9 / 10
                                        
                                        💡 **Mentor Feedback by Awiskar Acharya**:
                                        - Good usage of standard Express middleware parameters `(req, res, next)`.
                                        - **Security Alert**: Avoid plain token reading without cryptographic verification! Use `jwt.verify(token, process.env.JWT_SECRET)`.
                                        - Make sure to handle token expiration (TokenExpiredError).
                                    """.trimIndent()
                                )

                                else -> AiChatMessage(
                                    sender = "AI Mentor",
                                    content = """
                                        🎯 **TECHNICAL INTERVIEW QUESTION**:
                                        
                                        "Can you explain the difference between the Virtual DOM in React and the Browser's real DOM, and how reconciliation works?"
                                        
                                        💡 **Evaluation Criteria**:
                                        - Mentions memory representation vs real browser tree.
                                        - Explains diffing algorithm (O(n) heuristic).
                                        - Explains batching and minimal paint operations.
                                    """.trimIndent()
                                )
                            }
                            messages.add(reply)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DevSky),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Slate950, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

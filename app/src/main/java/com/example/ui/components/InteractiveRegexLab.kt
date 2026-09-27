package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun InteractiveRegexLab(modifier: Modifier = Modifier) {
    var regexPattern by remember { mutableStateOf("""\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}\b""") }
    var testText by remember {
        mutableStateOf("Contact Awiskar Acharya at awiskar@webdevacademy.io or admin@example.org for full-stack courses.")
    }

    val matchResults = remember(regexPattern, testText) {
        try {
            if (regexPattern.isBlank()) emptyList()
            else {
                val regex = Regex(regexPattern)
                regex.findAll(testText).map { it.value }.toList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    val isValidRegex = remember(regexPattern) {
        try {
            Regex(regexPattern)
            true
        } catch (e: Exception) {
            false
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
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
                    text = "Interactive Regex Lab",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DevSky
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isValidRegex) DevEmerald.copy(alpha = 0.2f) else DevRose.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = if (isValidRegex) "${matchResults.size} MATCHES" else "INVALID PATTERN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isValidRegex) DevEmeraldLight else DevRose,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Presets
            Text("Quick Presets:", fontSize = 12.sp, color = Slate400)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    "Email" to """\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}\b""",
                    "Digits" to """\d+""",
                    "Slug" to """^[a-z0-9]+(?:-[a-z0-9]+)*$""",
                    "Mentions" to """@\w+"""
                ).forEach { (label, pat) ->
                    AssistChip(
                        onClick = { regexPattern = pat },
                        label = { Text(label, fontSize = 11.sp) }
                    )
                }
            }

            // Regex Input
            OutlinedTextField(
                value = regexPattern,
                onValueChange = { regexPattern = it },
                label = { Text("Regular Expression Pattern") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 13.sp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DevSky,
                    unfocusedBorderColor = Slate700
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Test Text Input
            OutlinedTextField(
                value = testText,
                onValueChange = { testText = it },
                label = { Text("Test String / Payload") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DevIndigoLight,
                    unfocusedBorderColor = Slate700
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Matches View
            Text("Matched Tokens:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate200)
            if (matchResults.isEmpty()) {
                Text(
                    text = "No matches detected for the given pattern.",
                    fontSize = 12.sp,
                    color = Slate500,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    matchResults.take(6).forEachIndexed { index, match ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Slate950)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Match #${index + 1}",
                                fontSize = 11.sp,
                                color = Slate400,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = match,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = DevEmeraldLight,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }

            // Regex Token Guide
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Slate900)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("""\d: Digit""", fontSize = 11.sp, color = DevAmberLight, fontFamily = FontFamily.Monospace)
                Text("""\w: Word""", fontSize = 11.sp, color = DevSky, fontFamily = FontFamily.Monospace)
                Text("""+: 1 or more""", fontSize = 11.sp, color = DevIndigoLight, fontFamily = FontFamily.Monospace)
                Text("""[a-z]: Range""", fontSize = 11.sp, color = DevEmeraldLight, fontFamily = FontFamily.Monospace)
            }
        }
    }
}

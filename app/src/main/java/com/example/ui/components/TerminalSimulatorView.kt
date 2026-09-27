package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
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
import com.example.data.repository.PlaygroundRepository
import com.example.ui.theme.*

data class TerminalHistoryItem(
    val command: String,
    val output: String
)

@Composable
fun TerminalSimulatorView(modifier: Modifier = Modifier) {
    var commandInput by remember { mutableStateOf("") }
    val history = remember {
        mutableStateListOf(
            TerminalHistoryItem("whoami", "developer@webdevacademy (Mentored by Awiskar Acharya)"),
            TerminalHistoryItem("git status", "On branch main\nYour branch is up to date with 'origin/main'.\nnothing to commit, working tree clean")
        )
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFFEF4444)))
                    Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFFF59E0B)))
                    Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFF10B981)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "bash - developer@webdevacademy:~",
                        color = Slate300,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Text("v2.4", fontSize = 11.sp, color = Slate500, fontFamily = FontFamily.Monospace)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Quick Command Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("npm run dev", "git status", "cat package.json", "node -v", "ls", "help", "clear").forEach { cmd ->
                    AssistChip(
                        onClick = {
                            if (cmd == "clear") {
                                history.clear()
                            } else {
                                val out = PlaygroundRepository.executeTerminalCommand(cmd)
                                history.add(TerminalHistoryItem(cmd, out))
                            }
                        },
                        label = { Text(cmd, fontSize = 11.sp, fontFamily = FontFamily.Monospace) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Terminal Screen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate950)
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    history.forEach { item ->
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "➜ ",
                                color = DevEmeraldLight,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp
                            )
                            Text(
                                text = item.command,
                                color = DevSky,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp
                            )
                        }
                        if (item.output.isNotBlank()) {
                            Text(
                                text = item.output,
                                color = Slate300,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Terminal Input
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "$",
                    color = DevEmeraldLight,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp
                )
                OutlinedTextField(
                    value = commandInput,
                    onValueChange = { commandInput = it },
                    placeholder = { Text("Enter command (e.g. npm run dev, help)...", fontSize = 11.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DevSky,
                        unfocusedBorderColor = Slate700
                    )
                )
                IconButton(
                    onClick = {
                        val trimmed = commandInput.trim()
                        if (trimmed.isNotBlank()) {
                            if (trimmed.equals("clear", ignoreCase = true)) {
                                history.clear()
                            } else {
                                val output = PlaygroundRepository.executeTerminalCommand(trimmed)
                                history.add(TerminalHistoryItem(trimmed, output))
                            }
                            commandInput = ""
                        }
                    },
                    modifier = Modifier.background(DevSky, RoundedCornerShape(8.dp)).size(42.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Run Command", tint = Slate950, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

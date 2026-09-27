package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.ui.theme.*

@Composable
fun InteractiveApiTester(modifier: Modifier = Modifier) {
    var method by remember { mutableStateOf("GET") }
    var url by remember { mutableStateOf("https://api.webdevacademy.io/v1/courses") }
    var requestBody by remember { mutableStateOf("{\n  \"title\": \"Advanced Full-Stack Engineering\",\n  \"creator\": \"Awiskar Acharya\"\n}") }
    var responseStatus by remember { mutableStateOf("200 OK") }
    var responseTime by remember { mutableStateOf("48 ms") }
    var responsePayload by remember {
        mutableStateOf("""{
  "status": "success",
  "data": [
    { "id": 1, "title": "HTML5 Architecture", "mentor": "Awiskar Acharya", "rating": 4.9 },
    { "id": 2, "title": "Modern CSS & Flexbox", "mentor": "Awiskar Acharya", "rating": 5.0 },
    { "id": 3, "title": "Node.js REST Services", "mentor": "Awiskar Acharya", "rating": 4.95 }
  ],
  "meta": { "total": 3, "server": "WebDev Academy Cloud Edge" }
}""")
    }
    var isSending by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Interactive API Testing Console",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DevSky
            )
            Text(
                text = "Dispatch HTTP requests, inspect headers, status codes, and JSON response bodies.",
                fontSize = 13.sp,
                color = Slate400,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Method & URL Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Method Selector
                var methodMenuExpanded by remember { mutableStateOf(false) }
                Box {
                    Button(
                        onClick = { methodMenuExpanded = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (method) {
                                "GET" -> DevEmerald
                                "POST" -> DevIndigo
                                "PUT" -> DevAmber
                                else -> DevRose
                            }
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Text(method, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                    }
                    DropdownMenu(
                        expanded = methodMenuExpanded,
                        onDismissRequest = { methodMenuExpanded = false }
                    ) {
                        listOf("GET", "POST", "PUT", "DELETE").forEach { m ->
                            DropdownMenuItem(
                                text = { Text(m, fontWeight = FontWeight.Bold) },
                                onClick = {
                                    method = m
                                    methodMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // URL text field
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DevSky,
                        unfocusedBorderColor = Slate700
                    )
                )

                // Send Button
                Button(
                    onClick = {
                        isSending = true
                        if (method == "GET") {
                            responseStatus = "200 OK"
                            responseTime = "${(35..75).random()} ms"
                            responsePayload = """{
  "status": "success",
  "endpoint": "$url",
  "method": "GET",
  "data": [
    { "id": 101, "course": "Web Fundamentals", "status": "active" },
    { "id": 102, "course": "Full-Stack Capstone", "status": "in_progress" }
  ]
}"""
                        } else if (method == "POST") {
                            responseStatus = "201 Created"
                            responseTime = "${(60..120).random()} ms"
                            responsePayload = """{
  "status": "created",
  "message": "Resource successfully saved to PostgreSQL database",
  "record": $requestBody
}"""
                        } else if (method == "DELETE") {
                            responseStatus = "204 No Content"
                            responseTime = "${(25..45).random()} ms"
                            responsePayload = "/* HTTP 204: Resource deleted successfully. Zero bytes returned */"
                        } else {
                            responseStatus = "200 OK"
                            responseTime = "${(40..80).random()} ms"
                            responsePayload = """{ "status": "updated", "timestamp": ${System.currentTimeMillis()} }"""
                        }
                        isSending = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DevSky),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send", modifier = Modifier.size(16.dp), tint = Slate950)
                }
            }

            // If POST or PUT, show Request Body Editor
            if (method == "POST" || method == "PUT") {
                Spacer(modifier = Modifier.height(10.dp))
                Text("Request Body (JSON)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate200)
                OutlinedTextField(
                    value = requestBody,
                    onValueChange = { requestBody = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 11.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DevIndigoLight,
                        unfocusedBorderColor = Slate700
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Response Metas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Slate900)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("STATUS:", fontSize = 11.sp, color = Slate400, fontWeight = FontWeight.Bold)
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = if (responseStatus.startsWith("2")) DevEmerald.copy(alpha = 0.2f) else DevAmber.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = responseStatus,
                            color = if (responseStatus.startsWith("2")) DevEmeraldLight else DevAmberLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("TIME: $responseTime", fontSize = 11.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                    Text("SIZE: 1.2 KB", fontSize = 11.sp, color = Slate400, fontFamily = FontFamily.Monospace)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            CodeBlockView(code = responsePayload, language = "json")
        }
    }
}

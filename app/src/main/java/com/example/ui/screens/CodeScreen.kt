package com.example.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.viewinterop.AndroidView
import com.example.data.repository.PlaygroundRepository
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun CodeScreen(
    onNavigateToAiTutor: (String) -> Unit
) {
    var selectedLab by remember { mutableStateOf("Web IDE") }

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
            Text(
                text = "Developer Playground & Labs",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Live Execution • Frontend • Backend • Database • Shell",
                fontSize = 12.sp,
                color = DevSky,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Lab Category Switcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Web IDE", "Flexbox Lab", "SQL Lab", "API Tester", "Terminal Shell", "Regex Lab").forEach { lab ->
                    FilterChip(
                        selected = selectedLab == lab,
                        onClick = { selectedLab = lab },
                        label = { Text(lab, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
        }

        // Active Lab Body
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (selectedLab) {
                "Web IDE" -> {
                    item {
                        InteractiveWebIde(onAskAi = onNavigateToAiTutor)
                    }
                }
                "Flexbox Lab" -> {
                    item {
                        InteractiveFlexboxLab()
                    }
                }
                "SQL Lab" -> {
                    item {
                        InteractiveSqlLab()
                    }
                }
                "API Tester" -> {
                    item {
                        InteractiveApiTester()
                    }
                }
                "Terminal Shell" -> {
                    item {
                        TerminalSimulatorView()
                    }
                }
                "Regex Lab" -> {
                    item {
                        InteractiveRegexLab()
                    }
                }
            }
        }
    }
}

@Composable
fun InteractiveWebIde(
    onAskAi: (String) -> Unit
) {
    var activeTab by remember { mutableStateOf("HTML") }
    var htmlCode by remember { mutableStateOf(PlaygroundRepository.DEFAULT_HTML) }
    var cssCode by remember { mutableStateOf(PlaygroundRepository.DEFAULT_CSS) }
    var jsCode by remember { mutableStateOf(PlaygroundRepository.DEFAULT_JS) }
    var previewKey by remember { mutableIntStateOf(0) }

    val combinedHtml = remember(htmlCode, cssCode, jsCode, previewKey) {
        """
        <!DOCTYPE html>
        <html>
        <head>
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <style>
            body { margin: 0; padding: 12px; background: #030712; color: #f8fafc; font-family: sans-serif; }
            $cssCode
          </style>
        </head>
        <body>
          $htmlCode
          <script>
            try {
              $jsCode
            } catch (err) {
              console.error(err);
            }
          </script>
        </body>
        </html>
        """.trimIndent()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Editor Tab selector (HTML, CSS, JS)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("HTML", "CSS", "JS").forEach { tab ->
                        val isSelected = activeTab == tab
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) DevSky else Slate900,
                            modifier = Modifier.clickable { activeTab = tab }
                        ) {
                            Text(
                                text = tab,
                                color = if (isSelected) Slate950 else Slate300,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IconButton(
                        onClick = { previewKey++ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = DevSky, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Text Area Editor
            when (activeTab) {
                "HTML" -> {
                    OutlinedTextField(
                        value = htmlCode,
                        onValueChange = { htmlCode = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 8,
                        maxLines = 14,
                        textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DevSky,
                            unfocusedBorderColor = Slate700
                        )
                    )
                }
                "CSS" -> {
                    OutlinedTextField(
                        value = cssCode,
                        onValueChange = { cssCode = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 8,
                        maxLines = 14,
                        textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DevIndigoLight,
                            unfocusedBorderColor = Slate700
                        )
                    )
                }
                "JS" -> {
                    OutlinedTextField(
                        value = jsCode,
                        onValueChange = { jsCode = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 8,
                        maxLines = 14,
                        textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DevAmberLight,
                            unfocusedBorderColor = Slate700
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { previewKey++ },
                    colors = ButtonDefaults.buttonColors(containerColor = DevEmerald),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Run", modifier = Modifier.size(16.dp), tint = Slate950)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Render Live Preview", color = Slate950, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                OutlinedButton(
                    onClick = {
                        val currentCode = if (activeTab == "HTML") htmlCode else if (activeTab == "CSS") cssCode else jsCode
                        onAskAi("Analyze and explain this $activeTab code:\n$currentCode")
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Ask AI", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Live Browser Preview Label
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LIVE BROWSER PREVIEW",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DevSky,
                    fontFamily = FontFamily.Monospace
                )
                Text("localhost:5173", fontSize = 11.sp, color = Slate500, fontFamily = FontFamily.Monospace)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Embedded WebView for rendering the live HTML/CSS/JS output
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF030712))
            ) {
                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            webViewClient = WebViewClient()
                            loadDataWithBaseURL(null, combinedHtml, "text/html", "UTF-8", null)
                        }
                    },
                    update = { webView ->
                        webView.loadDataWithBaseURL(null, combinedHtml, "text/html", "UTF-8", null)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

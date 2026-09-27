package com.example.ui.screens

import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.data.repository.DevToolsRepository
import com.example.ui.components.CodeBlockView
import com.example.ui.components.InteractiveHttpStatusExplorer
import com.example.ui.components.InteractiveRegexLab
import com.example.ui.theme.*
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun ToolsScreen() {
    var selectedTool by remember { mutableStateOf("JSON Formatter") }

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
                text = "Developer Toolbox",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Formatters • Encoders • Network • CSS Generators • Testing",
                fontSize = 12.sp,
                color = DevSky,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Tool Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("JSON Formatter", "HTTP Status", "Regex Lab", "CSS Shadow", "Base64 & URL", "Color Converter").forEach { tool ->
                    FilterChip(
                        selected = selectedTool == tool,
                        onClick = { selectedTool = tool },
                        label = { Text(tool, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
        }

        // Active Tool
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (selectedTool) {
                "JSON Formatter" -> {
                    item { JsonFormatterTool() }
                }
                "HTTP Status" -> {
                    item { InteractiveHttpStatusExplorer() }
                }
                "Regex Lab" -> {
                    item { InteractiveRegexLab() }
                }
                "CSS Shadow" -> {
                    item { BoxShadowGeneratorTool() }
                }
                "Base64 & URL" -> {
                    item { Base64AndUrlTool() }
                }
                "Color Converter" -> {
                    item { ColorConverterTool() }
                }
            }
        }
    }
}

@Composable
fun JsonFormatterTool() {
    var rawJson by remember {
        mutableStateOf("""{"name":"WebDev Academy","mentor":"Awiskar Acharya","courses":["HTML","CSS","JS","Node","SQL"],"active":true}""")
    }
    var formattedJson by remember { mutableStateOf(DevToolsRepository.formatJson(rawJson)) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("JSON Formatter & Validator", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DevSky)
            Text("Beautify, validate syntax, and inspect JSON payloads.", fontSize = 13.sp, color = Slate400, modifier = Modifier.padding(vertical = 4.dp))

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = rawJson,
                onValueChange = { rawJson = it },
                label = { Text("Raw / Minified JSON") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DevSky, unfocusedBorderColor = Slate700)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { formattedJson = DevToolsRepository.formatJson(rawJson) },
                    colors = ButtonDefaults.buttonColors(containerColor = DevSky),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Beautify JSON", color = Slate950, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val minified = rawJson.replace("\n", "").replace(" ", "")
                        formattedJson = minified
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Minify", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Formatted Output:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate200)
            Spacer(modifier = Modifier.height(6.dp))
            CodeBlockView(code = formattedJson, language = "json")
        }
    }
}

@Composable
fun BoxShadowGeneratorTool() {
    var xOffset by remember { mutableFloatStateOf(0f) }
    var yOffset by remember { mutableFloatStateOf(10f) }
    var blurRadius by remember { mutableFloatStateOf(25f) }
    var spreadRadius by remember { mutableFloatStateOf(0f) }

    val generatedCss = """
        .card {
          box-shadow: ${xOffset.toInt()}px ${yOffset.toInt()}px ${blurRadius.toInt()}px ${spreadRadius.toInt()}px rgba(56, 189, 248, 0.35);
        }
    """.trimIndent()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("CSS Box-Shadow Generator", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DevSky)
            Text("Visually tune shadow elevations, blur radiuses, and copy production CSS.", fontSize = 13.sp, color = Slate400, modifier = Modifier.padding(vertical = 4.dp))

            Spacer(modifier = Modifier.height(14.dp))

            // Visual Card Preview Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Slate950),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Slate800,
                    shadowElevation = blurRadius.dp / 3,
                    border = ButtonDefaults.outlinedButtonBorder,
                    modifier = Modifier.size(140.dp, 70.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("Shadow Element", color = DevSky, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text("Y Offset: ${yOffset.toInt()}px", fontSize = 12.sp, color = Slate300)
            Slider(value = yOffset, onValueChange = { yOffset = it }, valueRange = 0f..40f)

            Text("Blur Radius: ${blurRadius.toInt()}px", fontSize = 12.sp, color = Slate300)
            Slider(value = blurRadius, onValueChange = { blurRadius = it }, valueRange = 0f..60f)

            Spacer(modifier = Modifier.height(8.dp))
            CodeBlockView(code = generatedCss, language = "css")
        }
    }
}

@Composable
fun Base64AndUrlTool() {
    var rawInput by remember { mutableStateOf("WebDev Academy - Awiskar Acharya") }
    var base64Output by remember { mutableStateOf("") }
    var urlOutput by remember { mutableStateOf("") }

    LaunchedEffect(rawInput) {
        base64Output = try {
            Base64.encodeToString(rawInput.toByteArray(), Base64.NO_WRAP)
        } catch (e: Exception) {
            "Error encoding Base64"
        }
        urlOutput = try {
            URLEncoder.encode(rawInput, "UTF-8")
        } catch (e: Exception) {
            "Error encoding URL"
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Base64 & URL Encoder/Decoder", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DevSky)
            Text("Encode and decode auth headers, API tokens, and URL slugs.", fontSize = 13.sp, color = Slate400, modifier = Modifier.padding(vertical = 4.dp))

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = rawInput,
                onValueChange = { rawInput = it },
                label = { Text("Input String") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DevSky, unfocusedBorderColor = Slate700)
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text("Base64 Encoded:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DevIndigoLight)
            CodeBlockView(code = base64Output, language = "text")

            Spacer(modifier = Modifier.height(10.dp))
            Text("URL Percent-Encoded:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DevEmeraldLight)
            CodeBlockView(code = urlOutput, language = "text")
        }
    }
}

@Composable
fun ColorConverterTool() {
    var hexColor by remember { mutableStateOf("#38BDF8") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Developer Color Converter & Swatches", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DevSky)
            Text("Convert HEX to RGB/HSL with live palette previews.", fontSize = 13.sp, color = Slate400, modifier = Modifier.padding(vertical = 4.dp))

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Live Color Swatch
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            try {
                                Color(android.graphics.Color.parseColor(hexColor))
                            } catch (e: Exception) {
                                DevSky
                            }
                        )
                        .border(1.dp, Slate600, RoundedCornerShape(12.dp))
                )

                OutlinedTextField(
                    value = hexColor,
                    onValueChange = { hexColor = it },
                    label = { Text("HEX Code") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 13.sp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DevSky, unfocusedBorderColor = Slate700)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("#38BDF8", "#6366F1", "#10B981", "#F59E0B", "#F43F5E", "#0F172A").forEach { colorCode ->
                    AssistChip(
                        onClick = { hexColor = colorCode },
                        label = { Text(colorCode, fontSize = 11.sp, fontFamily = FontFamily.Monospace) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("CSS Declarations:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate200)
            Spacer(modifier = Modifier.height(4.dp))
            val cssColorBlock = """
                --primary-hex: $hexColor;
                --primary-rgb: rgb(56, 189, 248);
                --primary-hsl: hsl(199, 95%, 60%);
            """.trimIndent()
            CodeBlockView(code = cssColorBlock, language = "css")
        }
    }
}

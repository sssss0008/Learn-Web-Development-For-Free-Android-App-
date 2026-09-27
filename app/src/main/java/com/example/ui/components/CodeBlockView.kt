package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun CodeBlockView(
    code: String,
    language: String = "javascript",
    modifier: Modifier = Modifier,
    onRunCode: ((String) -> Unit)? = null,
    onAskAi: ((String) -> Unit)? = null
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val lines = code.trimIndent().lines()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Slate950)
    ) {
        // Header Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Slate850)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFFEF4444)))
                Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFFF59E0B)))
                Box(modifier = Modifier.size(10.dp).clip(RoundedCornerShape(5.dp)).background(Color(0xFF10B981)))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = language.uppercase(),
                    color = DevSky,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (onAskAi != null) {
                    IconButton(
                        onClick = { onAskAi(code) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = "Ask AI to Explain",
                            tint = DevIndigoLight,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                if (onRunCode != null) {
                    IconButton(
                        onClick = { onRunCode(code) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Run Code",
                            tint = DevEmeraldLight,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(code))
                        Toast.makeText(context, "Code copied to clipboard", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy Code",
                        tint = Slate400,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Code Body with line numbers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 12.dp, horizontal = 12.dp)
        ) {
            // Line numbers
            Column(modifier = Modifier.padding(end = 12.dp)) {
                lines.indices.forEach { idx ->
                    Text(
                        text = "${idx + 1}",
                        color = Slate600,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 20.sp
                    )
                }
            }

            // Code Text with basic syntax coloring
            Column {
                lines.forEach { line ->
                    Text(
                        text = highlightSyntax(line, language),
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

private fun highlightSyntax(line: String, language: String): AnnotatedString {
    return buildAnnotatedString {
        val keywords = setOf(
            "const", "let", "var", "function", "return", "async", "await", "import", "export",
            "from", "default", "class", "interface", "type", "try", "catch", "if", "else",
            "SELECT", "FROM", "WHERE", "JOIN", "INNER", "LEFT", "ON", "CREATE", "TABLE",
            "FROM", "RUN", "WORKDIR", "COPY", "CMD", "EXPOSE", "AS"
        )
        val tokens = line.split(" ")
        tokens.forEachIndexed { index, token ->
            val cleanToken = token.trim().replace("(", "").replace(")", "").replace(";", "")
            when {
                token.startsWith("//") || token.startsWith("#") || token.startsWith("--") -> {
                    append(token)
                    addStyle(SpanStyle(color = SyntaxComment), length - token.length, length)
                }
                keywords.contains(cleanToken) -> {
                    append(token)
                    addStyle(SpanStyle(color = SyntaxKeyword, fontWeight = FontWeight.SemiBold), length - token.length, length)
                }
                token.startsWith("\"") || token.startsWith("'") || token.startsWith("`") -> {
                    append(token)
                    addStyle(SpanStyle(color = SyntaxString), length - token.length, length)
                }
                token.startsWith("<") || token.endsWith(">") -> {
                    append(token)
                    addStyle(SpanStyle(color = SyntaxTag, fontWeight = FontWeight.Medium), length - token.length, length)
                }
                cleanToken.toIntOrNull() != null -> {
                    append(token)
                    addStyle(SpanStyle(color = SyntaxNumber), length - token.length, length)
                }
                else -> {
                    append(token)
                    addStyle(SpanStyle(color = Slate200), length - token.length, length)
                }
            }
            if (index < tokens.size - 1) {
                append(" ")
            }
        }
    }
}

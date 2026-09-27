package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import com.example.ui.theme.*

@Composable
fun InteractiveFlexboxLab(modifier: Modifier = Modifier) {
    var flexDirection by remember { mutableStateOf("row") }
    var justifyContent by remember { mutableStateOf("center") }
    var alignItems by remember { mutableStateOf("center") }
    var gapVal by remember { mutableIntStateOf(12) }
    var itemCount by remember { mutableIntStateOf(4) }

    val generatedCss = """
        .container {
          display: flex;
          flex-direction: $flexDirection;
          justify-content: $justifyContent;
          align-items: $alignItems;
          gap: ${gapVal}px;
        }
    """.trimIndent()

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
                    text = "CSS Flexbox Interactive Lab",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DevSky
                )
                Text(
                    text = "$itemCount items",
                    fontSize = 12.sp,
                    color = Slate400
                )
            }

            Text(
                text = "Manipulate properties live and observe the CSS box positioning response.",
                fontSize = 13.sp,
                color = Slate400,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Live Visual Flexbox Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Slate950)
                    .border(1.dp, DevIndigoLight.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .padding(gapVal.dp)
            ) {
                val horizontalArrangement = when (justifyContent) {
                    "flex-start" -> Arrangement.Start
                    "center" -> Arrangement.Center
                    "flex-end" -> Arrangement.End
                    "space-between" -> Arrangement.SpaceBetween
                    "space-around" -> Arrangement.SpaceAround
                    else -> Arrangement.SpaceEvenly
                }

                val verticalAlignment = when (alignItems) {
                    "flex-start" -> Alignment.Top
                    "center" -> Alignment.CenterVertically
                    "flex-end" -> Alignment.Bottom
                    else -> Alignment.CenterVertically
                }

                if (flexDirection == "row" || flexDirection == "row-reverse") {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = horizontalArrangement,
                        verticalAlignment = verticalAlignment
                    ) {
                        val range = if (flexDirection == "row-reverse") (itemCount downTo 1) else (1..itemCount)
                        for (i in range) {
                            FlexBoxItem(index = i)
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = when (justifyContent) {
                            "flex-start" -> Arrangement.Top
                            "center" -> Arrangement.Center
                            "flex-end" -> Arrangement.Bottom
                            "space-between" -> Arrangement.SpaceBetween
                            else -> Arrangement.SpaceEvenly
                        },
                        horizontalAlignment = when (alignItems) {
                            "flex-start" -> Alignment.Start
                            "center" -> Alignment.CenterHorizontally
                            "flex-end" -> Alignment.End
                            else -> Alignment.CenterHorizontally
                        }
                    ) {
                        val range = if (flexDirection == "column-reverse") (itemCount downTo 1) else (1..itemCount)
                        for (i in range) {
                            FlexBoxItem(index = i)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Control 1: flex-direction
            Text("flex-direction", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate200)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("row", "column", "row-reverse", "column-reverse").forEach { dir ->
                    FilterChip(
                        selected = flexDirection == dir,
                        onClick = { flexDirection = dir },
                        label = { Text(dir, fontSize = 11.sp, fontFamily = FontFamily.Monospace) }
                    )
                }
            }

            // Control 2: justify-content
            Text("justify-content", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate200)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("flex-start", "center", "flex-end", "space-between", "space-around", "space-evenly").forEach { jc ->
                    FilterChip(
                        selected = justifyContent == jc,
                        onClick = { justifyContent = jc },
                        label = { Text(jc, fontSize = 11.sp, fontFamily = FontFamily.Monospace) }
                    )
                }
            }

            // Control 3: align-items & gap
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("align-items", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate200)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("center", "flex-start", "flex-end").forEach { ai ->
                            FilterChip(
                                selected = alignItems == ai,
                                onClick = { alignItems = ai },
                                label = { Text(ai, fontSize = 11.sp, fontFamily = FontFamily.Monospace) }
                            )
                        }
                    }
                }
            }

            // Gap & Items count
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Gap: ${gapVal}px", fontSize = 12.sp, color = Slate300)
                    Button(
                        onClick = { gapVal = if (gapVal == 8) 16 else if (gapVal == 16) 24 else 8 },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("Toggle Gap", fontSize = 11.sp)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Items: $itemCount", fontSize = 12.sp, color = Slate300)
                    Button(
                        onClick = { itemCount = if (itemCount < 6) itemCount + 1 else 3 },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("Add / Cycle", fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            CodeBlockView(code = generatedCss, language = "css")
        }
    }
}

@Composable
private fun FlexBoxItem(index: Int) {
    val bgColors = listOf(
        Color(0xFF38BDF8),
        Color(0xFF818CF8),
        Color(0xFF34D399),
        Color(0xFFFBBF24),
        Color(0xFFF472B6),
        Color(0xFFA78BFA)
    )
    val color = bgColors[(index - 1) % bgColors.size]

    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$index",
            color = Slate950,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

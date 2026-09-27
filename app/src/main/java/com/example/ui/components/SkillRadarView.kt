package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class SkillStat(
    val name: String,
    val percentage: Float, // 0.0 to 1.0
    val color: Color
)

@Composable
fun SkillRadarView(modifier: Modifier = Modifier) {
    val skills = listOf(
        SkillStat("HTML5 Architecture", 0.95f, DevSky),
        SkillStat("Modern CSS & Flex/Grid", 0.88f, DevIndigoLight),
        SkillStat("JavaScript ES6+ & Async", 0.82f, DevAmberLight),
        SkillStat("React & Frontend Architecture", 0.78f, Color(0xFF61DAFB)),
        SkillStat("Node.js & REST APIs", 0.72f, DevEmeraldLight),
        SkillStat("PostgreSQL & Relational SQL", 0.68f, DevSkyDark),
        SkillStat("Git & Version Control", 0.80f, Color(0xFFF97316)),
        SkillStat("Web Security & OWASP", 0.62f, DevRose)
    )

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
                    text = "Developer Skill Matrix",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DevSky
                )
                Text(
                    text = "8 Core Disciplines",
                    fontSize = 12.sp,
                    color = Slate400
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                skills.forEach { skill ->
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = skill.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Slate200
                            )
                            Text(
                                text = "${(skill.percentage * 100).toInt()}%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = skill.color,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        // Progress track
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Slate900)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(skill.percentage)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(skill.color)
                            )
                        }
                    }
                }
            }
        }
    }
}

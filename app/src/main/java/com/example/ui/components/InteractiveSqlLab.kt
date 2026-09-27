package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.PlaygroundRepository
import com.example.ui.theme.*

@Composable
fun InteractiveSqlLab(modifier: Modifier = Modifier) {
    var sqlQuery by remember { mutableStateOf("SELECT * FROM courses WHERE mentor = 'Awiskar Acharya';") }
    var queryResult by remember { mutableStateOf(PlaygroundRepository.executeMockSql(sqlQuery)) }

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
                    text = "Interactive SQL Laboratory",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DevSky
                )
                Text(
                    text = "PostgreSQL Engine",
                    fontSize = 12.sp,
                    color = DevIndigoLight,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "Write and execute SQL queries against relational database tables with live results.",
                fontSize = 13.sp,
                color = Slate400,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Preset Query Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    "All Courses" to "SELECT * FROM courses;",
                    "Awiskar's Courses" to "SELECT * FROM courses WHERE mentor = 'Awiskar Acharya';",
                    "Frontend Only" to "SELECT * FROM courses WHERE category = 'Frontend';",
                    "Users Table" to "SELECT * FROM users;",
                    "Students Join" to "SELECT users.name, courses.title FROM users JOIN courses ON 1=1;"
                ).forEach { (label, q) ->
                    AssistChip(
                        onClick = {
                            sqlQuery = q
                            queryResult = PlaygroundRepository.executeMockSql(q)
                        },
                        label = { Text(label, fontSize = 11.sp) }
                    )
                }
            }

            // SQL Input
            OutlinedTextField(
                value = sqlQuery,
                onValueChange = { sqlQuery = it },
                label = { Text("SQL Query") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DevSky,
                    unfocusedBorderColor = Slate700
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Action & Stats Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        queryResult = PlaygroundRepository.executeMockSql(sqlQuery)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DevEmerald),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Run", modifier = Modifier.size(16.dp), tint = Slate950)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Execute Query", color = Slate950, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "${queryResult.rowCount} rows",
                        fontSize = 11.sp,
                        color = DevEmeraldLight,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "${queryResult.executionTimeMs} ms",
                        fontSize = 11.sp,
                        color = Slate400,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Data Table
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Slate950)
                    .border(1.dp, Slate800, RoundedCornerShape(8.dp))
                    .horizontalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                Column {
                    // Header Row
                    Row(
                        modifier = Modifier
                            .background(Slate800)
                            .padding(vertical = 6.dp, horizontal = 4.dp)
                    ) {
                        queryResult.columns.forEach { col ->
                            Text(
                                text = col.uppercase(),
                                fontWeight = FontWeight.Bold,
                                color = DevSky,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.width(130.dp).padding(horizontal = 6.dp)
                            )
                        }
                    }

                    Divider(color = Slate700)

                    // Data Rows
                    queryResult.rows.forEachIndexed { rowIndex, row ->
                        Row(
                            modifier = Modifier
                                .background(if (rowIndex % 2 == 0) Slate950 else Slate900)
                                .padding(vertical = 6.dp, horizontal = 4.dp)
                        ) {
                            row.forEach { cell ->
                                Text(
                                    text = cell,
                                    color = Slate200,
                                    fontSize = 11.sp,
                                    fontFamily = FontFamily.Monospace,
                                    modifier = Modifier.width(130.dp).padding(horizontal = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

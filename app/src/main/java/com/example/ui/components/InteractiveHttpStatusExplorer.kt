package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
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
import com.example.data.model.HttpStatusItem
import com.example.data.repository.DevToolsRepository
import com.example.ui.theme.*

@Composable
fun InteractiveHttpStatusExplorer(modifier: Modifier = Modifier) {
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var expandedCode by remember { mutableIntStateOf(200) }

    val filteredList = remember(selectedCategory, searchQuery) {
        DevToolsRepository.httpStatusList.filter { item ->
            val matchesCategory = (selectedCategory == "All") || (item.category == selectedCategory)
            val matchesSearch = searchQuery.isBlank() ||
                item.code.toString().contains(searchQuery) ||
                item.name.contains(searchQuery, ignoreCase = true) ||
                item.description.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "HTTP Status Code Explorer",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DevSky
            )
            Text(
                text = "Interactive dictionary of REST status codes, payloads, and troubleshooting.",
                fontSize = 13.sp,
                color = Slate400,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Search input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by code or status name...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Slate400) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DevSky,
                    unfocusedBorderColor = Slate700
                )
            )

            // Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("All", "2xx", "3xx", "4xx", "5xx").forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }

            // Status List Items
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                filteredList.forEach { item ->
                    HttpStatusCard(
                        item = item,
                        isExpanded = expandedCode == item.code,
                        onToggle = {
                            expandedCode = if (expandedCode == item.code) -1 else item.code
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HttpStatusCard(
    item: HttpStatusItem,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val statusColor = when (item.category) {
        "2xx" -> DevEmeraldLight
        "3xx" -> DevSky
        "4xx" -> DevAmberLight
        "5xx" -> DevRose
        else -> Slate200
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Slate900)
            .clickable { onToggle() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = statusColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "${item.code}",
                        color = statusColor,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = item.name,
                    color = Slate100,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = "Expand",
                tint = Slate400
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            Column(modifier = Modifier.padding(top = 10.dp)) {
                Divider(color = Slate800, thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description,
                    color = Slate300,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text("Scenario: ", color = DevIndigoLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(item.exampleUseCase, color = Slate400, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text("Troubleshooting: ", color = DevAmberLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(item.troubleshooting, color = Slate400, fontSize = 12.sp)
                }
            }
        }
    }
}

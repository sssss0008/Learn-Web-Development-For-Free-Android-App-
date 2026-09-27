package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CommunityPost
import com.example.data.repository.CommunityRepository
import com.example.ui.theme.*

@Composable
fun CommunityScreen() {
    val context = LocalContext.current
    var selectedTag by remember { mutableStateOf("All") }
    var showNewPostDialog by remember { mutableStateOf(false) }

    val posts = remember { mutableStateListOf(*CommunityRepository.communityPosts.toTypedArray()) }

    val filteredPosts = remember(selectedTag, posts.size) {
        if (selectedTag == "All") posts
        else posts.filter { it.tags.contains(selectedTag) }
    }

    if (showNewPostDialog) {
        var postTitle by remember { mutableStateOf("") }
        var postContent by remember { mutableStateOf("") }
        var postTag by remember { mutableStateOf("Question") }

        AlertDialog(
            onDismissRequest = { showNewPostDialog = false },
            title = { Text("New Community Discussion", fontWeight = FontWeight.Bold, color = DevSky) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = postTitle,
                        onValueChange = { postTitle = it },
                        label = { Text("Discussion Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = postContent,
                        onValueChange = { postContent = it },
                        label = { Text("Details & Code Questions") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (postTitle.isNotBlank()) {
                            posts.add(
                                0,
                                CommunityPost(
                                    id = "p_${System.currentTimeMillis()}",
                                    authorName = "Developer",
                                    authorRole = "Student",
                                    isCreator = false,
                                    title = postTitle,
                                    content = postContent,
                                    tags = listOf(postTag, "Community"),
                                    likesCount = 1,
                                    repliesCount = 0,
                                    timeAgo = "Just now"
                                )
                            )
                            showNewPostDialog = false
                            Toast.makeText(context, "Discussion posted!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DevSky)
                ) {
                    Text("Post", color = Slate950, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewPostDialog = false }) { Text("Cancel") }
            },
            containerColor = Slate850
        )
    }

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Developer Community",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Peer Feedback • Creator Tips • Project Showcases",
                        fontSize = 12.sp,
                        color = DevSky,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = { showNewPostDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = DevSky),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(16.dp), tint = Slate950)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("New Post", color = Slate950, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Tags filter
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Announcement", "ProTip", "Capstone", "CSS", "JavaScript").forEach { tag ->
                    FilterChip(
                        selected = selectedTag == tag,
                        onClick = { selectedTag = tag },
                        label = { Text(tag, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
        }

        // Posts List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredPosts) { post ->
                CommunityPostCard(post = post)
            }
        }
    }
}

@Composable
fun CommunityPostCard(post: CommunityPost) {
    var isLiked by remember { mutableStateOf(false) }
    var likesCount by remember { mutableIntStateOf(post.likesCount) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (post.isCreator) DevIndigoDark.copy(alpha = 0.35f) else Slate850
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Author info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (post.isCreator) DevSky else DevIndigoLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = post.authorName.take(1),
                            color = Slate950,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = post.authorName,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            if (post.isCreator) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified Creator",
                                    tint = DevSky,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = "${post.authorRole} • ${post.timeAgo}",
                            fontSize = 11.sp,
                            color = Slate400
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = post.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (post.isCreator) DevSky else Color.White
            )
            Text(
                text = post.content,
                fontSize = 13.sp,
                color = Slate300,
                lineHeight = 19.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Tags
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                post.tags.forEach { tag ->
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Slate900
                    ) {
                        Text(
                            text = "#$tag",
                            fontSize = 10.sp,
                            color = DevIndigoLight,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Divider(color = Slate800, modifier = Modifier.padding(vertical = 8.dp))

            // Likes & comments footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = {
                            isLiked = !isLiked
                            likesCount += if (isLiked) 1 else -1
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) DevRose else Slate400,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text(text = "$likesCount", fontSize = 12.sp, color = Slate400)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Replies",
                        tint = Slate400,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "${post.repliesCount} replies", fontSize = 12.sp, color = Slate400)
                }
            }
        }
    }
}

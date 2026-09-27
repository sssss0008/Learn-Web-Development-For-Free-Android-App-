package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Lesson
import com.example.data.model.RoadmapLevel
import com.example.data.repository.CurriculumRepository
import com.example.data.repository.UserProgressRepository
import com.example.ui.components.CodeBlockView
import com.example.ui.theme.*

@Composable
fun LearnScreen(
    onNavigateToAiTutor: (String) -> Unit
) {
    var selectedDifficulty by remember { mutableStateOf("All") }
    var activeLesson by remember { mutableStateOf<Lesson?>(null) }
    val progressState by UserProgressRepository.state.collectAsState()

    if (activeLesson != null) {
        BackHandler {
            activeLesson = null
        }
        LessonDetailView(
            lesson = activeLesson!!,
            isCompleted = progressState.completedLessonIds.contains(activeLesson!!.id),
            isBookmarked = progressState.bookmarkedLessonIds.contains(activeLesson!!.id),
            savedNote = progressState.notes[activeLesson!!.id] ?: "",
            onBack = { activeLesson = null },
            onComplete = {
                UserProgressRepository.completeLesson(activeLesson!!.id, xpEarned = 50)
            },
            onToggleBookmark = {
                UserProgressRepository.toggleBookmark(activeLesson!!.id)
            },
            onSaveNote = { note ->
                UserProgressRepository.saveNote(activeLesson!!.id, note)
            },
            onAskAi = { prompt ->
                onNavigateToAiTutor(prompt)
            }
        )
        return
    }

    val levels = remember(selectedDifficulty) {
        if (selectedDifficulty == "All") CurriculumRepository.roadmapLevels
        else CurriculumRepository.roadmapLevels.filter { it.difficulty == selectedDifficulty }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate900)
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Slate850)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Web Development Roadmap",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "17 Progressive Levels • Mentored by Awiskar Acharya",
                fontSize = 12.sp,
                color = DevSky,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Difficulty Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Beginner", "Intermediate", "Advanced", "Professional").forEach { diff ->
                    FilterChip(
                        selected = selectedDifficulty == diff,
                        onClick = { selectedDifficulty = diff },
                        label = { Text(diff, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                    )
                }
            }
        }

        // Roadmap Levels List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(levels) { level ->
                RoadmapLevelCard(
                    level = level,
                    completedLessons = progressState.completedLessonIds,
                    onSelectLesson = { lesson -> activeLesson = lesson }
                )
            }
        }
    }
}

@Composable
fun RoadmapLevelCard(
    level: RoadmapLevel,
    completedLessons: Set<String>,
    onSelectLesson: (Lesson) -> Unit
) {
    var expanded by remember { mutableStateOf(level.levelNumber <= 4) }
    val levelCompleted = level.lessons.isNotEmpty() && level.lessons.all { completedLessons.contains(it.id) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Slate850)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Level Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Level Circle Badge
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(if (levelCompleted) DevEmeraldLight.copy(alpha = 0.2f) else DevSky.copy(alpha = 0.15f))
                            .border(1.dp, if (levelCompleted) DevEmeraldLight else DevSky, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${level.levelNumber}",
                            color = if (levelCompleted) DevEmeraldLight else DevSky,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "LEVEL ${level.levelNumber}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DevSky,
                                fontFamily = FontFamily.Monospace
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = when (level.difficulty) {
                                    "Beginner" -> DevEmerald.copy(alpha = 0.2f)
                                    "Intermediate" -> DevSky.copy(alpha = 0.2f)
                                    "Advanced" -> DevAmber.copy(alpha = 0.2f)
                                    else -> DevRose.copy(alpha = 0.2f)
                                }
                            ) {
                                Text(
                                    text = level.difficulty,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (level.difficulty) {
                                        "Beginner" -> DevEmeraldLight
                                        "Intermediate" -> DevSky
                                        "Advanced" -> DevAmberLight
                                        else -> DevRose
                                    },
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = level.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Toggle",
                    tint = Slate400
                )
            }

            Text(
                text = level.description,
                fontSize = 12.sp,
                color = Slate300,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Topics Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                level.topics.forEach { topic ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Slate900,
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text(
                            text = topic,
                            fontSize = 11.sp,
                            color = Slate300,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Lessons List
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Divider(color = Slate800)
                    level.lessons.forEach { lesson ->
                        val isDone = completedLessons.contains(lesson.id)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Slate900)
                                .clickable { onSelectLesson(lesson) }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = "Status",
                                    tint = if (isDone) DevEmeraldLight else Slate500,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column {
                                    Text(
                                        text = lesson.title,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "${lesson.durationMinutes} min • ${lesson.language}",
                                        fontSize = 11.sp,
                                        color = Slate400
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Open",
                                tint = DevSky,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LessonDetailView(
    lesson: Lesson,
    isCompleted: Boolean,
    isBookmarked: Boolean,
    savedNote: String,
    onBack: () -> Unit,
    onComplete: () -> Unit,
    onToggleBookmark: () -> Unit,
    onSaveNote: (String) -> Unit,
    onAskAi: (String) -> Unit
) {
    val context = LocalContext.current
    var currentTab by remember { mutableStateOf("Lesson") }
    var noteText by remember { mutableStateOf(savedNote) }

    // Quiz state
    var selectedOptionIndex by remember { mutableIntStateOf(-1) }
    var hasAnsweredQuiz by remember { mutableStateOf(false) }

    // Practical Challenge state
    val challenge = lesson.practicalChallenge
    var userCode by remember { mutableStateOf(challenge?.starterCode ?: "") }
    var challengeFeedback by remember { mutableStateOf<String?>(null) }
    var isChallengeSuccess by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate900)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Navigation Bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IconButton(onClick = onToggleBookmark) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) DevAmberLight else Slate400
                        )
                    }

                    IconButton(onClick = { onAskAi("Explain the core concept of lesson: ${lesson.title}") }) {
                        Icon(Icons.Default.Psychology, contentDescription = "AI Mentor", tint = DevSky)
                    }
                }
            }
        }

        // Lesson Title & Header
        item {
            Column {
                Text(
                    text = "LEVEL ${lesson.levelNumber} LESSON",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DevSky,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = lesson.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = lesson.overview,
                    fontSize = 13.sp,
                    color = Slate300
                )
            }
        }

        // Navigation Tabs: Lesson, Challenge, Quiz, Notes
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate850)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Lesson", "Challenge", "Quiz", "Notes").forEach { tab ->
                    val isSelected = currentTab == tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) DevSky else Color.Transparent)
                            .clickable { currentTab = tab }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Slate950 else Slate300
                        )
                    }
                }
            }
        }

        // TAB 1: LESSON CONTENT
        if (currentTab == "Lesson") {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Slate850)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Concept Breakdown",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DevSky
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = lesson.detailedContent,
                            fontSize = 13.sp,
                            color = Slate200,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Production Code Example",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DevIndigoLight
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CodeBlockView(
                            code = lesson.codeSnippet,
                            language = lesson.language,
                            onAskAi = { onAskAi("Explain this code step-by-step:\n$it") }
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                onComplete()
                                Toast.makeText(context, "Lesson completed! +50 XP awarded", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCompleted) DevEmerald else DevSky
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.Check,
                                contentDescription = "Complete",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isCompleted) "Completed (+50 XP Earned)" else "Mark as Completed (+50 XP)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // TAB 2: PRACTICAL CHALLENGE
        if (currentTab == "Challenge") {
            item {
                if (challenge != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Slate850)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = challenge.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DevAmberLight
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = challenge.instructions,
                                fontSize = 13.sp,
                                color = Slate200
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = userCode,
                                onValueChange = { userCode = it },
                                label = { Text("Write your solution") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 5,
                                textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 12.sp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = DevAmberLight,
                                    unfocusedBorderColor = Slate700
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        val isValid = Regex(challenge.testValidationRegex, RegexOption.DOT_MATCHES_ALL).matches(userCode)
                                        isChallengeSuccess = isValid
                                        challengeFeedback = if (isValid) {
                                            UserProgressRepository.completeChallenge(challenge.id, 75)
                                            "✓ Tests Passed! Awarded +75 XP. Excellent implementation."
                                        } else {
                                            "✕ Tests failed. Hint: ${challenge.hints.firstOrNull() ?: challenge.expectedOutputHint}"
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = DevAmber),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Run Tests", color = Slate950, fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = { userCode = challenge.solutionCode },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Show Solution", fontSize = 12.sp)
                                }
                            }

                            if (challengeFeedback != null) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isChallengeSuccess) DevEmerald.copy(alpha = 0.2f) else DevRose.copy(alpha = 0.2f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = challengeFeedback!!,
                                        color = if (isChallengeSuccess) DevEmeraldLight else DevRose,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Slate850)
                    ) {
                        Text(
                            text = "No interactive challenge for this introductory lesson. Check other levels or open the Code Playground!",
                            color = Slate400,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        // TAB 3: QUIZ
        if (currentTab == "Quiz") {
            val questions = lesson.quizQuestions
            if (questions.isNotEmpty()) {
                val q = questions.first()
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Slate850)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Knowledge Check",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DevSky
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = q.question,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            q.options.forEachIndexed { index, option ->
                                val isSelected = selectedOptionIndex == index
                                val isCorrect = index == q.correctIndex
                                val optionColor = if (hasAnsweredQuiz) {
                                    if (isCorrect) DevEmeraldLight else if (isSelected) DevRose else Slate200
                                } else {
                                    if (isSelected) DevSky else Slate200
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected && !hasAnsweredQuiz) DevSky.copy(alpha = 0.15f) else Slate900,
                                    border = if (isSelected) ButtonDefaults.outlinedButtonBorder else null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable(enabled = !hasAnsweredQuiz) {
                                            selectedOptionIndex = index
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "${('A' + index)}. ",
                                            fontWeight = FontWeight.Bold,
                                            color = optionColor
                                        )
                                        Text(
                                            text = option,
                                            color = optionColor,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            if (!hasAnsweredQuiz) {
                                Button(
                                    onClick = {
                                        if (selectedOptionIndex >= 0) {
                                            hasAnsweredQuiz = true
                                            if (selectedOptionIndex == q.correctIndex) {
                                                UserProgressRepository.completeChallenge("quiz_${lesson.id}", 30)
                                            }
                                        }
                                    },
                                    enabled = selectedOptionIndex >= 0,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = DevSky),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Submit Answer", color = Slate950, fontWeight = FontWeight.Bold)
                                }
                            } else {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (selectedOptionIndex == q.correctIndex) DevEmerald.copy(alpha = 0.2f) else DevRose.copy(alpha = 0.2f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = if (selectedOptionIndex == q.correctIndex) "✓ Correct! +30 XP" else "✕ Incorrect",
                                            fontWeight = FontWeight.Bold,
                                            color = if (selectedOptionIndex == q.correctIndex) DevEmeraldLight else DevRose
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = q.explanation,
                                            fontSize = 12.sp,
                                            color = Slate200
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                item {
                    Text("No quiz currently registered for this lesson.", color = Slate400)
                }
            }
        }

        // TAB 4: NOTES
        if (currentTab == "Notes") {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Slate850)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Lesson Notes & Highlights",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DevSky
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = noteText,
                            onValueChange = { noteText = it },
                            placeholder = { Text("Write personal study notes, reminders, or code snippets here...") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 6,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = DevSky,
                                unfocusedBorderColor = Slate700
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                onSaveNote(noteText)
                                Toast.makeText(context, "Note saved securely", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DevEmerald),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save Note", color = Slate950, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

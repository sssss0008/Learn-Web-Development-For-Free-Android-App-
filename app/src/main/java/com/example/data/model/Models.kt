package com.example.data.model

data class RoadmapLevel(
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val iconName: String,
    val xpReward: Int,
    val difficulty: String, // Beginner, Intermediate, Advanced, Professional
    val topics: List<String>,
    val description: String,
    val lessons: List<Lesson>
)

data class Lesson(
    val id: String,
    val levelNumber: Int,
    val title: String,
    val durationMinutes: Int,
    val overview: String,
    val detailedContent: String,
    val codeSnippet: String,
    val language: String,
    val practicalChallenge: PracticalChallenge? = null,
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val flashcards: List<Flashcard> = emptyList(),
    val keyTakeaways: List<String> = emptyList()
)

data class PracticalChallenge(
    val id: String,
    val title: String,
    val instructions: String,
    val starterCode: String,
    val expectedOutputHint: String,
    val testValidationRegex: String,
    val solutionCode: String,
    val hints: List<String>
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class Flashcard(
    val front: String,
    val back: String,
    val category: String
)

data class ProjectItem(
    val id: String,
    val title: String,
    val category: String, // Beginner, Intermediate, Advanced
    val description: String,
    val technologies: List<String>,
    val milestones: List<ProjectMilestone>,
    val architectureFlow: String,
    val requirements: List<String>,
    val sampleSnippet: String,
    val liveDemoUrl: String = "https://github.com/awiskaracharya",
    val estimatedHours: Int = 12
)

data class ProjectMilestone(
    val title: String,
    val completed: Boolean,
    val description: String
)

data class CommunityPost(
    val id: String,
    val authorName: String,
    val authorRole: String,
    val isCreator: Boolean = false,
    val title: String,
    val content: String,
    val tags: List<String>,
    val likesCount: Int,
    val repliesCount: Int,
    val timeAgo: String
)

data class Certificate(
    val id: String,
    val studentName: String,
    val courseName: String,
    val issuedDate: String,
    val verificationCode: String,
    val creatorName: String = "Awiskar Acharya",
    val creatorTitle: String = "Lead Web Architect & Educator",
    val creatorProfileUrl: String = "https://www.linkedin.com/in/awiskaracharya/"
)

data class DailyCodingChallenge(
    val day: String,
    val title: String,
    val difficulty: String,
    val category: String,
    val problem: String,
    val starterCode: String,
    val testCases: List<TestCase>,
    val solution: String,
    val xpReward: Int = 50
)

data class TestCase(
    val inputDescription: String,
    val expectedOutput: String
)

data class CodeSnippetItem(
    val id: String,
    val title: String,
    val language: String,
    val code: String,
    val description: String,
    val tags: List<String>
)

data class DevToolItem(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val iconName: String
)

data class HttpStatusItem(
    val code: Int,
    val name: String,
    val category: String, // 1xx, 2xx, 3xx, 4xx, 5xx
    val description: String,
    val exampleUseCase: String,
    val troubleshooting: String
)

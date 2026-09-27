package com.example.data.repository

import com.example.data.model.Certificate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserProgressState(
    val studentName: String = "Developer",
    val xp: Int = 1850,
    val levelTitle: String = "Level 4 • JavaScript Craftsman",
    val currentStreak: Int = 12,
    val completedLessonIds: Set<String> = setOf("l1_1", "l2_1"),
    val bookmarkedLessonIds: Set<String> = setOf("l1_1"),
    val notes: Map<String, String> = mapOf("l1_1" to "Remember: HTTPS encrypts traffic via TLS on port 443."),
    val solvedChallenges: Set<String> = setOf("c1_1"),
    val certificates: List<Certificate> = listOf(
        Certificate(
            id = "CERT-WDA-8041",
            studentName = "Developer",
            courseName = "HTML5 & Semantic Architecture",
            issuedDate = "September 2026",
            verificationCode = "WDA-HTML5-AWISKAR-9921",
            creatorName = "Awiskar Acharya",
            creatorTitle = "Lead Web Architect & Educator",
            creatorProfileUrl = "https://www.linkedin.com/in/awiskaracharya/"
        )
    ),
    val weeklyLessonsCompleted: Int = 3,
    val weeklyLessonsTarget: Int = 5,
    val weeklyChallengesCompleted: Int = 2,
    val weeklyChallengesTarget: Int = 3,
    val codingMinutesToday: Int = 42
)

object UserProgressRepository {

    private val _state = MutableStateFlow(UserProgressState())
    val state: StateFlow<UserProgressState> = _state.asStateFlow()

    fun completeLesson(lessonId: String, xpEarned: Int = 50) {
        _state.update { current ->
            val newCompleted = current.completedLessonIds + lessonId
            val newXp = current.xp + xpEarned
            current.copy(
                completedLessonIds = newCompleted,
                xp = newXp,
                weeklyLessonsCompleted = current.weeklyLessonsCompleted + 1
            )
        }
    }

    fun toggleBookmark(lessonId: String) {
        _state.update { current ->
            val updated = if (current.bookmarkedLessonIds.contains(lessonId)) {
                current.bookmarkedLessonIds - lessonId
            } else {
                current.bookmarkedLessonIds + lessonId
            }
            current.copy(bookmarkedLessonIds = updated)
        }
    }

    fun saveNote(lessonId: String, note: String) {
        _state.update { current ->
            current.copy(notes = current.notes + (lessonId to note))
        }
    }

    fun completeChallenge(challengeId: String, xpEarned: Int = 75) {
        _state.update { current ->
            val newSolved = current.solvedChallenges + challengeId
            current.copy(
                solvedChallenges = newSolved,
                xp = current.xp + xpEarned,
                weeklyChallengesCompleted = current.weeklyChallengesCompleted + 1
            )
        }
    }

    fun issueCertificate(courseName: String, codePrefix: String) {
        val newCert = Certificate(
            id = "CERT-WDA-${(1000..9999).random()}",
            studentName = _state.value.studentName,
            courseName = courseName,
            issuedDate = "September 2026",
            verificationCode = "WDA-$codePrefix-AWISKAR-${(1000..9999).random()}",
            creatorName = "Awiskar Acharya",
            creatorTitle = "Lead Web Architect & Educator",
            creatorProfileUrl = "https://www.linkedin.com/in/awiskaracharya/"
        )
        _state.update { current ->
            current.copy(
                certificates = current.certificates + newCert,
                xp = current.xp + 200
            )
        }
    }

    fun setStudentName(name: String) {
        _state.update { it.copy(studentName = name) }
    }
}

package com.example.data.repository

import com.example.data.model.CommunityPost

object CommunityRepository {

    val communityPosts: List<CommunityPost> = listOf(
        CommunityPost(
            id = "cp1",
            authorName = "Awiskar Acharya",
            authorRole = "Creator & Lead Architect",
            isCreator = true,
            title = "🚀 Welcome to WebDev Academy: Learn. Code. Build. Deploy.",
            content = "Welcome to WebDev Academy! Our mission is to take you from computer fundamentals to full-stack mastery. Make sure to solve the daily challenge and use the interactive labs regularly. Connect with me on LinkedIn: https://www.linkedin.com/in/awiskaracharya/!",
            tags = listOf("Welcome", "Roadmap", "Announcement"),
            likesCount = 1240,
            repliesCount = 89,
            timeAgo = "1 hour ago"
        ),
        CommunityPost(
            id = "cp2",
            authorName = "Awiskar Acharya",
            authorRole = "Creator & Lead Architect",
            isCreator = true,
            title = "💡 Pro Tip: Understanding the CSS Box Model with outline vs border",
            content = "When debugging layouts, avoid adding `border: 1px solid red` because borders add to element dimensions and cause layout shifts! Instead, use `outline: 1px solid red` which does not occupy physical space in the box model.",
            tags = listOf("CSS", "ProTip", "Debugging"),
            likesCount = 852,
            repliesCount = 44,
            timeAgo = "5 hours ago"
        ),
        CommunityPost(
            id = "cp3",
            authorName = "Jordan Reed",
            authorRole = "Full-Stack Student",
            isCreator = false,
            title = "Completed Capstone Project: SaaS TaskFlow Platform!",
            content = "Just passed the final milestone of the WebDev Academy Capstone with 94% test coverage. Built with React, Express, PostgreSQL, and Docker. Earned the Full-Stack Developer Certificate!",
            tags = listOf("Capstone", "ProjectShowcase", "Certification"),
            likesCount = 312,
            repliesCount = 28,
            timeAgo = "1 day ago"
        ),
        CommunityPost(
            id = "cp4",
            authorName = "Priya Sharma",
            authorRole = "Frontend Learner",
            isCreator = false,
            title = "Question: When should I choose CSS Grid over Flexbox?",
            content = "I'm practicing Level 3 modern CSS. Is Flexbox best for one-dimensional components like navbars and Grid for entire page 2D layouts?",
            tags = listOf("CSS", "Flexbox", "Grid"),
            likesCount = 145,
            repliesCount = 19,
            timeAgo = "2 days ago"
        )
    )
}

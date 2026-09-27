package com.example.data.repository

import com.example.data.model.Flashcard
import com.example.data.model.Lesson
import com.example.data.model.PracticalChallenge
import com.example.data.model.QuizQuestion
import com.example.data.model.RoadmapLevel

object CurriculumRepository {

    val roadmapLevels: List<RoadmapLevel> = listOf(
        RoadmapLevel(
            levelNumber = 1,
            title = "Web & Computer Fundamentals",
            subtitle = "Internet, Client/Server, Browsers, Protocols",
            iconName = "globe",
            xpReward = 150,
            difficulty = "Beginner",
            topics = listOf("Internet Architecture", "Client vs Server", "HTTP & HTTPS", "DNS & Domains", "Browsers & Rendering", "Cookies, Cache & Sessions"),
            description = "Understand how the global web infrastructure operates before writing code.",
            lessons = listOf(
                Lesson(
                    id = "l1_1",
                    levelNumber = 1,
                    title = "How the Internet Works & Client-Server Architecture",
                    durationMinutes = 15,
                    overview = "Learn how clients make requests to web servers across routers, DNS, and IP addresses.",
                    detailedContent = """
                        When you type `https://webdevacademy.io` into your browser:
                        1. **DNS Resolution**: Your browser asks a DNS server to translate the human-readable domain name into an IP address (e.g., 142.250.190.46).
                        2. **TCP/TLS Handshake**: A secure, encrypted connection is negotiated between your browser (client) and the remote host (server).
                        3. **HTTP Request**: The client sends an HTTP GET request asking for index.html.
                        4. **Server Response**: The web server processes the request and returns an HTTP response with headers and body payload.
                        5. **Rendering Engine**: The browser parses HTML into DOM, CSS into CSSOM, executes JavaScript, and paints pixels on screen.
                    """.trimIndent(),
                    codeSnippet = """
                        // Conceptual Client-Server HTTP Exchange
                        // 1. Client Request
                        GET /api/courses HTTP/1.1
                        Host: webdevacademy.com
                        User-Agent: Chrome/128.0
                        Accept: application/json
                        
                        // 2. Server Response
                        HTTP/1.1 200 OK
                        Content-Type: application/json; charset=utf-8
                        Cache-Control: max-age=3600
                        
                        {
                          "academy": "WebDev Academy",
                          "creator": "Awiskar Acharya",
                          "status": "Online"
                        }
                    """.trimIndent(),
                    language = "HTTP",
                    practicalChallenge = PracticalChallenge(
                        id = "c1_1",
                        title = "Simulate an HTTP Response",
                        instructions = "Write a mock JSON response body with fields 'status', 'version', and 'creator' set to 'Awiskar Acharya'.",
                        starterCode = "{\n  \"status\": \"success\",\n  \"creator\": \"\"\n}",
                        expectedOutputHint = "Must include creator: Awiskar Acharya",
                        testValidationRegex = ".*Awiskar Acharya.*",
                        solutionCode = "{\n  \"status\": \"success\",\n  \"version\": \"1.0\",\n  \"creator\": \"Awiskar Acharya\"\n}",
                        hints = listOf("Set creator to 'Awiskar Acharya'", "Ensure valid JSON syntax with double quotes")
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            question = "What is the primary role of a DNS server in web browsing?",
                            options = listOf(
                                "Translates human domain names to numeric IP addresses",
                                "Executes JavaScript on the server",
                                "Stores user session cookies",
                                "Encrypts video streaming packets"
                            ),
                            correctIndex = 0,
                            explanation = "DNS acts as the phonebook of the internet, resolving human domain names into IP addresses."
                        ),
                        QuizQuestion(
                            question = "What is the difference between HTTP and HTTPS?",
                            options = listOf(
                                "HTTPS adds TLS/SSL encryption for privacy and data integrity",
                                "HTTP is faster and more secure",
                                "HTTPS works only on mobile phones",
                                "There is no difference"
                            ),
                            correctIndex = 0,
                            explanation = "HTTPS transmits data over an encrypted TLS connection, protecting against snooping and tampering."
                        )
                    ),
                    flashcards = listOf(
                        Flashcard("Client", "The device, browser, or app that requests resources from a server.", "Fundamentals"),
                        Flashcard("Server", "A computer listening for incoming network requests and returning resources.", "Fundamentals"),
                        Flashcard("DNS", "Domain Name System: translates domain names into machine-readable IP addresses.", "Fundamentals")
                    ),
                    keyTakeaways = listOf(
                        "Clients initiate requests; servers process and reply with responses.",
                        "DNS maps human domain names to IP addresses.",
                        "HTTPS ensures end-to-end encrypted transport over TLS."
                    )
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 2,
            title = "HTML5 Architecture",
            subtitle = "Semantic Structure, Forms, SEO & Accessibility",
            iconName = "code",
            xpReward = 200,
            difficulty = "Beginner",
            topics = listOf("Document Skeleton", "Semantic Tags", "Modern Forms & Validation", "Tables & Lists", "Media & Audio/Video", "Accessibility (a11y)"),
            description = "The foundational skeleton of every webpage and web application.",
            lessons = listOf(
                Lesson(
                    id = "l2_1",
                    levelNumber = 2,
                    title = "Semantic HTML5 & Accessible Document Structure",
                    durationMinutes = 20,
                    overview = "Learn semantic tags like <main>, <header>, <nav>, <article>, <section>, and <aside>.",
                    detailedContent = """
                        Semantic elements clearly describe their meaning to both the browser and screen readers:
                        - `<header>`: Introductory content or navigational aids.
                        - `<nav>`: Primary navigation links.
                        - `<main>`: The unique core content of the document.
                        - `<article>`: Self-contained composition (blog post, widget, product card).
                        - `<section>`: Thematic grouping of content with a heading.
                        - `<footer>`: Author information, copyright, and legal links.
                    """.trimIndent(),
                    codeSnippet = """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                          <meta charset="UTF-8">
                          <meta name="viewport" content="width=device-width, initial-scale=1.0">
                          <title>WebDev Academy</title>
                        </head>
                        <body>
                          <header>
                            <h1>WebDev Academy</h1>
                            <nav aria-label="Main Navigation">
                              <ul>
                                <li><a href="#learn">Learn</a></li>
                                <li><a href="#code">Playground</a></li>
                              </ul>
                            </nav>
                          </header>
                          <main>
                            <article>
                              <h2>Semantic Architecture</h2>
                              <p>Created with precision by Awiskar Acharya.</p>
                            </article>
                          </main>
                        </body>
                        </html>
                    """.trimIndent(),
                    language = "HTML",
                    practicalChallenge = PracticalChallenge(
                        id = "c2_1",
                        title = "Build a Semantic Article Card",
                        instructions = "Create an <article> containing an <h2> title and a <p> description mentioning 'Awiskar Acharya'.",
                        starterCode = "<article>\n  <!-- Add h2 and p here -->\n</article>",
                        expectedOutputHint = "Must contain <h2> and <p> with Awiskar Acharya",
                        testValidationRegex = ".*<article>.*<h2>.*</h2>.*<p>.*Awiskar Acharya.*</p>.*</article>.*",
                        solutionCode = "<article>\n  <h2>Web Development</h2>\n  <p>Mentored by Awiskar Acharya</p>\n</article>",
                        hints = listOf("Wrap text inside <h2>", "Include Awiskar Acharya in the <p> tag")
                    ),
                    quizQuestions = listOf(
                        QuizQuestion(
                            question = "Why should developers prioritize semantic tags over generic <div> tags?",
                            options = listOf(
                                "Improves accessibility for screen readers and search engine indexing",
                                "<div> tags are deprecated in HTML5",
                                "Semantic tags make the web page load 10x faster automatically",
                                "They automatically style components without CSS"
                            ),
                            correctIndex = 0,
                            explanation = "Semantic HTML conveys meaning to assistive technologies and search engines."
                        )
                    )
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 3,
            title = "Modern CSS & Responsive Layouts",
            subtitle = "Box Model, Flexbox, Grid, Transitions, Variables",
            iconName = "palette",
            xpReward = 250,
            difficulty = "Beginner",
            topics = listOf("Box Model & Units (rem, em, ch)", "Flexbox Masterclass", "CSS Grid 2D Layouts", "Media & Container Queries", "CSS Variables", "Keyframe Animations"),
            description = "Transform bare structural markup into stunning, responsive, fluid user interfaces.",
            lessons = listOf(
                Lesson(
                    id = "l3_1",
                    levelNumber = 3,
                    title = "Flexbox Alignment & Justification Deep-Dive",
                    durationMinutes = 25,
                    overview = "Master main axis, cross axis, justify-content, align-items, flex-direction, and gap.",
                    detailedContent = """
                        Flexbox provides one-dimensional layout control along the **Main Axis** and **Cross Axis**.
                        Key properties on the container:
                        - `display: flex;`
                        - `flex-direction: row | column | row-reverse;`
                        - `justify-content: flex-start | center | space-between | space-around | space-evenly;`
                        - `align-items: stretch | center | flex-start | flex-end | baseline;`
                        - `gap: 1rem;`
                    """.trimIndent(),
                    codeSnippet = """
                        .dev-toolbar {
                          display: flex;
                          flex-direction: row;
                          justify-content: space-between;
                          align-items: center;
                          gap: 16px;
                          padding: 12px 24px;
                          background: #0f172a;
                          border-radius: 8px;
                        }
                        
                        .dev-item {
                          flex: 1 1 auto;
                        }
                    """.trimIndent(),
                    language = "CSS"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 4,
            title = "JavaScript: Core & Modern ES6+",
            subtitle = "DOM Manipulation, Asynchronous JS, Promises, Closures",
            iconName = "javascript",
            xpReward = 300,
            difficulty = "Intermediate",
            topics = listOf("Data Types & Scope", "Functions & Arrow Syntax", "Arrays & Object Destructuring", "DOM & Event Handling", "Promises & Async/Await", "Fetch API & Web Storage"),
            description = "The engine of interactivity in web applications, from browser events to async data fetching.",
            lessons = listOf(
                Lesson(
                    id = "l4_1",
                    levelNumber = 4,
                    title = "Promises, Async/Await and Error Handling",
                    durationMinutes = 30,
                    overview = "Master asynchronous JavaScript execution, handling API promises with try/catch blocks.",
                    detailedContent = """
                        JavaScript operates on a single-threaded Event Loop.
                        Asynchronous operations (fetching data, timers, file I/O) are handled via Web APIs and resolved through Promises.
                        Modern syntax uses `async` functions with `await` and `try...catch` blocks for clean, readable error handling.
                    """.trimIndent(),
                    codeSnippet = """
                        async function loadDeveloperRoadmap(courseId) {
                          try {
                            const response = await fetch(`https://api.webdevacademy.io/courses/` + courseId);
                            if (!response.ok) {
                              throw new Error(`HTTP error! status: ` + response.status);
                            }
                            const data = await response.json();
                            console.log("Course loaded successfully:", data.title);
                            return data;
                          } catch (error) {
                            console.error("Failed to fetch roadmap:", error.message);
                            throw error;
                          }
                        }
                    """.trimIndent(),
                    language = "JavaScript"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 5,
            title = "Git & GitHub Version Control",
            subtitle = "Commits, Branches, Merging, PRs, Merge Conflicts",
            iconName = "source-branch",
            xpReward = 200,
            difficulty = "Beginner",
            topics = listOf("Git Init, Add & Commit", "Branching & Merging", "Remotes & GitHub Workflow", "Resolving Merge Conflicts", "Rebase vs Merge", "Open Source PRs"),
            description = "Collaborative version tracking essential for every professional software engineer.",
            lessons = listOf(
                Lesson(
                    id = "l5_1",
                    levelNumber = 5,
                    title = "Git Workflow: Working Tree to Remote Repository",
                    durationMinutes = 20,
                    overview = "Learn the 4 stages: Working Directory -> Staging Area -> Local Repo -> Remote Repo.",
                    detailedContent = """
                        1. `git init` - initializes a new repository.
                        2. `git add .` - stages changes for snapshot.
                        3. `git commit -m "feat: setup project"` - records commit in local repository history.
                        4. `git branch -M main` - renames primary branch.
                        5. `git remote add origin <url>` - links local repo to GitHub.
                        6. `git push -u origin main` - pushes commits to remote server.
                    """.trimIndent(),
                    codeSnippet = """
                        # Typical feature branch flow
                        git checkout -b feature/auth-flow
                        # Edit your files...
                        git add src/auth/
                        git commit -m "feat(auth): implement JWT token verification"
                        git push origin feature/auth-flow
                    """.trimIndent(),
                    language = "Bash"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 6,
            title = "Responsive Web Design & UI/UX",
            subtitle = "Mobile-First, Design Systems, Typography, Contrast",
            iconName = "devices",
            xpReward = 250,
            difficulty = "Intermediate",
            topics = listOf("Mobile-First Breakpoints", "Fluid Typography & clamp()", "Visual Hierarchy & Contrast", "Design Tokens & Colors", "Micro-Interactions", "UX Heuristics"),
            description = "Craft delightful, accessible experiences across mobile, tablet, laptop, and ultra-wide screens.",
            lessons = listOf(
                Lesson(
                    id = "l6_1",
                    levelNumber = 6,
                    title = "Mobile-First CSS Breakpoints & Fluid Typography",
                    durationMinutes = 25,
                    overview = "Build layouts that scale smoothly using min-width media queries and CSS clamp().",
                    detailedContent = """
                        Mobile-first means writing baseline styles for small screens, then using `min-width` media queries for larger displays:
                        - Small (Mobile): default base styles (< 640px)
                        - Medium (Tablet): `@media (min-width: 768px)`
                        - Large (Desktop): `@media (min-width: 1024px)`
                        - Extra Large: `@media (min-width: 1280px)`
                    """.trimIndent(),
                    codeSnippet = """
                        /* Fluid title clamp(min, preferred, max) */
                        h1.hero-title {
                          font-size: clamp(2rem, 5vw + 1rem, 4.5rem);
                          line-height: 1.1;
                        }
                    """.trimIndent(),
                    language = "CSS"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 7,
            title = "TypeScript for Enterprise Web",
            subtitle = "Types, Interfaces, Generics, Enums, Type Guards",
            iconName = "code-braces",
            xpReward = 300,
            difficulty = "Intermediate",
            topics = listOf("Primitive & Union Types", "Interfaces vs Type Aliases", "Generics & Constraints", "Enums & Literal Types", "Type Narrowing", "tsconfig & Compiling"),
            description = "Supercharge JavaScript with static type safety, auto-completion, and bug prevention.",
            lessons = listOf(
                Lesson(
                    id = "l7_1",
                    levelNumber = 7,
                    title = "Generics and Advanced Type Narrowing",
                    durationMinutes = 25,
                    overview = "Build reusable, type-safe API wrappers with TypeScript Generics.",
                    detailedContent = """
                        Generics allow you to create components and functions that work over a variety of types rather than a single one.
                    """.trimIndent(),
                    codeSnippet = """
                        interface ApiResponse<T> {
                          status: "success" | "error";
                          data: T;
                          timestamp: number;
                        }
                        
                        interface StudentProfile {
                          id: string;
                          name: string;
                          xp: number;
                          completedCourses: string[];
                        }
                        
                        async function fetchAcademyData<T>(url: string): Promise<ApiResponse<T>> {
                          const res = await fetch(url);
                          return res.json();
                        }
                    """.trimIndent(),
                    language = "TypeScript"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 8,
            title = "Frontend Frameworks: React & Next.js",
            subtitle = "Components, JSX, Hooks, Server Components, SSR",
            iconName = "atom",
            xpReward = 350,
            difficulty = "Intermediate",
            topics = listOf("Component Lifecycle & JSX", "useState & useEffect", "Custom Hooks", "useContext & State Stores", "Next.js App Router", "SSR vs SSG vs Client"),
            description = "Build reactive, declarative user interfaces with React 19 and Next.js full-stack capabilities.",
            lessons = listOf(
                Lesson(
                    id = "l8_1",
                    levelNumber = 8,
                    title = "React State Management & Custom Hooks",
                    durationMinutes = 30,
                    overview = "Learn to isolate reactive business logic into clean custom hooks.",
                    detailedContent = """
                        Hooks allow functional components to hook into React state and lifecycle. Custom hooks let you extract component logic into reusable functions.
                    """.trimIndent(),
                    codeSnippet = """
                        import { useState, useEffect } from "react";
                        
                        export function useDeveloperStreak() {
                          const [streak, setStreak] = useState(0);
                          const [loading, setLoading] = useState(true);
                          
                          useEffect(() => {
                            // Check local storage or API
                            const saved = localStorage.getItem("dev_streak") || "7";
                            setStreak(parseInt(saved, 10));
                            setLoading(false);
                          }, []);
                          
                          return { streak, loading };
                        }
                    """.trimIndent(),
                    language = "JavaScript"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 9,
            title = "Backend Development: Node.js & APIs",
            subtitle = "Express, Middleware, Controllers, RESTful Routing",
            iconName = "server",
            xpReward = 350,
            difficulty = "Intermediate",
            topics = listOf("Event-Driven Architecture", "Express Router & Middleware", "RESTful Conventions", "Request Validation", "Error Middleware", "Environment Variables"),
            description = "Create performant, scalable server-side APIs handling business logic and data persistence.",
            lessons = listOf(
                Lesson(
                    id = "l9_1",
                    levelNumber = 9,
                    title = "Express REST API Architecture & Middleware Pipelines",
                    durationMinutes = 30,
                    overview = "Build professional API controllers with input validation and centralized error handlers.",
                    detailedContent = """
                        Express processes HTTP requests through a chain of middleware functions:
                        `(req, res, next) => { ... }`.
                        Controllers handle business logic, while services communicate with databases.
                    """.trimIndent(),
                    codeSnippet = """
                        import express from "express";
                        const app = express();
                        
                        app.use(express.json());
                        
                        app.get("/api/v1/courses", (req, res) => {
                          res.json([
                            { id: "html", title: "HTML5 Architecture", mentor: "Awiskar Acharya" },
                            { id: "node", title: "Node.js REST Services", mentor: "Awiskar Acharya" }
                          ]);
                        });
                        
                        app.listen(3000, () => console.log("Server listening on port 3000"));
                    """.trimIndent(),
                    language = "JavaScript"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 10,
            title = "Databases & SQL Mastery",
            subtitle = "PostgreSQL, Tables, Queries, Joins, Indexing, ORMs",
            iconName = "database",
            xpReward = 350,
            difficulty = "Intermediate",
            topics = listOf("Relational vs NoSQL", "SELECT, WHERE, ORDER BY", "Primary & Foreign Keys", "INNER & LEFT JOINS", "Indexes & Performance", "Prisma & Migration"),
            description = "Design bulletproof relational schemas and query complex datasets with SQL precision.",
            lessons = listOf(
                Lesson(
                    id = "l10_1",
                    levelNumber = 10,
                    title = "Relational Schemas, Constraints & Multi-Table Joins",
                    durationMinutes = 30,
                    overview = "Master primary keys, foreign key relationships, and query joins in PostgreSQL.",
                    detailedContent = """
                        Databases structure persistent records into tables with defined columns and types.
                        Using `JOIN` lets you combine data from multiple tables based on related columns.
                    """.trimIndent(),
                    codeSnippet = """
                        -- Schema Creation & Query
                        CREATE TABLE users (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL
                        );
                        
                        CREATE TABLE projects (
                          id SERIAL PRIMARY KEY,
                          title VARCHAR(150) NOT NULL,
                          user_id INTEGER REFERENCES users(id) ON DELETE CASCADE
                        );
                        
                        -- Query with INNER JOIN
                        SELECT users.name, projects.title
                        FROM users
                        INNER JOIN projects ON users.id = projects.user_id
                        WHERE users.name = 'Awiskar Acharya';
                    """.trimIndent(),
                    language = "SQL"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 11,
            title = "API Architecture: REST & GraphQL",
            subtitle = "Endpoints, Status Codes, Query Parameters, Headers",
            iconName = "api",
            xpReward = 300,
            difficulty = "Intermediate",
            topics = listOf("HTTP Verbs (GET, POST, PUT, DELETE)", "Status Code Categorization", "Pagination & Filtering", "GraphQL Schema & Queries", "API Rate Limiting", "Swagger/OpenAPI"),
            description = "Build and consume rock-solid web APIs following RESTful best practices.",
            lessons = listOf(
                Lesson(
                    id = "l11_1",
                    levelNumber = 11,
                    title = "RESTful Design Principles & Status Code Precision",
                    durationMinutes = 25,
                    overview = "Map CRUD operations cleanly to HTTP verbs and standard status codes.",
                    detailedContent = """
                        - 200 OK: Successful request.
                        - 201 Created: Resource successfully saved (POST).
                        - 400 Bad Request: Malformed payload.
                        - 401 Unauthorized: Authentication required.
                        - 403 Forbidden: Authenticated but insufficient permissions.
                        - 404 Not Found: Resource does not exist.
                        - 500 Internal Server Error: Unhandled backend fault.
                    """.trimIndent(),
                    codeSnippet = """
                        // Standard RESTful routes
                        GET    /api/projects          // List projects
                        POST   /api/projects          // Create new project
                        GET    /api/projects/:id      // Retrieve single project
                        PUT    /api/projects/:id      // Full replacement
                        PATCH  /api/projects/:id      // Partial update
                        DELETE /api/projects/:id      // Remove project
                    """.trimIndent(),
                    language = "HTTP"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 12,
            title = "Authentication & Authorization",
            subtitle = "JWT, Sessions, Cookies, OAuth2, Role-Based Access",
            iconName = "lock",
            xpReward = 350,
            difficulty = "Advanced",
            topics = listOf("Password Hashing with Argon2/Bcrypt", "Stateless JWT vs State Sessions", "HttpOnly Secure Cookies", "OAuth 2.0 & Social Login", "Role-Based Access Control (RBAC)", "Two-Factor Auth (2FA)"),
            description = "Secure user identities, prevent token theft, and manage granular user permissions.",
            lessons = listOf(
                Lesson(
                    id = "l12_1",
                    levelNumber = 12,
                    title = "Stateless JWT Tokens vs HttpOnly Session Cookies",
                    durationMinutes = 30,
                    overview = "Compare token storage options and prevent XSS token theft with HttpOnly cookies.",
                    detailedContent = """
                        Storing JWT tokens in `localStorage` exposes them to Cross-Site Scripting (XSS) attacks.
                        Production web applications prefer issuing `HttpOnly`, `Secure`, `SameSite=Strict` cookies that JavaScript cannot access.
                    """.trimIndent(),
                    codeSnippet = """
                        // Setting a secure cookie in Express
                        res.cookie("auth_token", token, {
                          httpOnly: true,
                          secure: process.env.NODE_ENV === "production",
                          sameSite: "strict",
                          maxAge: 7 * 24 * 60 * 60 * 1000 // 7 days
                        });
                    """.trimIndent(),
                    language = "JavaScript"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 13,
            title = "Automated Testing & QA",
            subtitle = "Unit, Integration, E2E, Vitest, Playwright, TDD",
            iconName = "check-decagram",
            xpReward = 300,
            difficulty = "Advanced",
            topics = listOf("Testing Pyramid", "Unit Testing with Vitest/Jest", "React Testing Library", "Mocking APIs & Dependencies", "End-to-End with Playwright", "Test-Driven Development"),
            description = "Write test suites that guarantee code correctness, stability, and zero regressions.",
            lessons = listOf(
                Lesson(
                    id = "l13_1",
                    levelNumber = 13,
                    title = "Unit Testing Pure Functions and Component Assertions",
                    durationMinutes = 25,
                    overview = "Write unit tests using assertions, test runners, and mocking strategies.",
                    detailedContent = """
                        Unit testing isolates individual units of code (functions, classes, components) and verifies their output against specified inputs.
                    """.trimIndent(),
                    codeSnippet = """
                        import { describe, it, expect } from "vitest";
                        import { calculateStudentXP } from "./gamification";
                        
                        describe("calculateStudentXP", () => {
                          it("awards bonus XP for a 7-day streak", () => {
                            const result = calculateStudentXP({ basePoints: 100, streakDays: 7 });
                            expect(result).toBe(150);
                          });
                        });
                    """.trimIndent(),
                    language = "TypeScript"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 14,
            title = "Web Security & OWASP Top 10",
            subtitle = "XSS, CSRF, SQL Injection, Content Security Policy",
            iconName = "shield-check",
            xpReward = 400,
            difficulty = "Advanced",
            topics = listOf("OWASP Top 10 Vulnerabilities", "Cross-Site Scripting (XSS)", "Cross-Site Request Forgery (CSRF)", "SQL Injection Defense", "Content Security Policy (CSP)", "CORS Configuration"),
            description = "Defend modern web applications against cyber threats, data leaks, and code injection.",
            lessons = listOf(
                Lesson(
                    id = "l14_1",
                    levelNumber = 14,
                    title = "Preventing XSS, SQLi & Setting Secure Headers",
                    durationMinutes = 35,
                    overview = "Master input sanitization, parameterized queries, and HTTP security headers.",
                    detailedContent = """
                        1. **SQL Injection**: Always use parameterized queries or trusted ORMs. Never concatenate strings into SQL queries.
                        2. **XSS**: Sanitize user inputs and encode HTML entities before rendering.
                        3. **Security Headers**: Use Helmet to set `Content-Security-Policy`, `X-Frame-Options`, and `Strict-Transport-Security`.
                    """.trimIndent(),
                    codeSnippet = """
                        // Safe SQL Parameterization
                        const query = 'SELECT * FROM users WHERE email = $1';
                        const values = [userEmail];
                        const result = await db.query(query, values);
                    """.trimIndent(),
                    language = "JavaScript"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 15,
            title = "DevOps, Docker & CI/CD Pipelines",
            subtitle = "Containers, GitHub Actions, Nginx, Cloud Deploy",
            iconName = "docker",
            xpReward = 400,
            difficulty = "Professional",
            topics = listOf("Docker Containers & Images", "Writing Production Dockerfiles", "Multi-Stage Builds", "GitHub Actions CI/CD", "Nginx Reverse Proxy", "Cloud Deployments (Vercel, AWS)"),
            description = "Containerize full-stack apps and automate builds, tests, and zero-downtime deployments.",
            lessons = listOf(
                Lesson(
                    id = "l15_1",
                    levelNumber = 15,
                    title = "Production Multi-Stage Dockerfile for Node & Web Apps",
                    durationMinutes = 30,
                    overview = "Build lean, minimal container images using multi-stage compilation.",
                    detailedContent = """
                        Multi-stage builds allow you to use heavy build tools (TypeScript compiler, bundlers) in the build stage, then copy only the compiled artifacts into a lightweight production runtime container.
                    """.trimIndent(),
                    codeSnippet = """
                        # Stage 1: Build
                        FROM node:20-alpine AS builder
                        WORKDIR /app
                        COPY package*.json ./
                        RUN npm ci
                        COPY . .
                        RUN npm run build
                        
                        # Stage 2: Production Runner
                        FROM node:20-alpine AS runner
                        WORKDIR /app
                        ENV NODE_ENV=production
                        COPY --from=builder /app/dist ./dist
                        COPY --from=builder /app/node_modules ./node_modules
                        EXPOSE 3000
                        CMD ["node", "dist/server.js"]
                    """.trimIndent(),
                    language = "Dockerfile"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 16,
            title = "Full-Stack Web Architecture",
            subtitle = "End-to-End Integration, Monolith vs Microservices",
            iconName = "layers-triple",
            xpReward = 450,
            difficulty = "Professional",
            topics = listOf("Three-Tier Architecture", "State Synchronization", "WebSockets & Real-time", "Caching with Redis", "File Uploads & S3", "Microservices vs Monoliths"),
            description = "Connect frontend UI, backend API routes, relational databases, and caching layers.",
            lessons = listOf(
                Lesson(
                    id = "l16_1",
                    levelNumber = 16,
                    title = "Connecting React Frontend to Node Backend and PostgreSQL",
                    durationMinutes = 35,
                    overview = "Trace data lifecycle from browser user click to database write and real-time response.",
                    detailedContent = """
                        Full-stack architecture synchronizes client-side state with persistent storage:
                        1. Client: User submits form -> Client validates and dispatches POST request.
                        2. Network: Secure HTTPS transmission with authorization bearer token.
                        3. API Layer: Route intercepts -> Validates schema -> Authenticates user token.
                        4. Controller: Executes business logic.
                        5. Database: Writes ACID transaction into PostgreSQL table.
                        6. Response: Returns updated entity to client -> React updates UI state seamlessly.
                    """.trimIndent(),
                    codeSnippet = """
                        // Full-stack data flow summary
                        Browser (React UI)
                            │  HTTPS POST { title: "New Project" }
                            ▼
                        Node.js / Express Server
                            │  authMiddleware() -> validateSchema()
                            ▼
                        PostgreSQL Database
                            │  INSERT INTO projects (...) RETURNING *;
                            ▼
                        Client updates cache & UI
                    """.trimIndent(),
                    language = "Architecture"
                )
            )
        ),
        RoadmapLevel(
            levelNumber = 17,
            title = "Professional Projects & Capstone",
            subtitle = "Production Portfolio, Code Reviews, Career Prep",
            iconName = "certificate",
            xpReward = 500,
            difficulty = "Professional",
            topics = listOf("Full-Stack Capstone Project", "Portfolio Presentation", "Technical Interview Prep", "System Design for Web Devs", "Resume & LinkedIn Optimization", "Certification Issuance"),
            description = "Synthesize all skills into portfolio-ready applications and prepare for tech industry hiring.",
            lessons = listOf(
                Lesson(
                    id = "l17_1",
                    levelNumber = 17,
                    title = "Building the Production Capstone & System Design",
                    durationMinutes = 40,
                    overview = "Design and build a scalable SaaS application meeting professional production standards.",
                    detailedContent = """
                        The WebDev Academy Capstone requires:
                        - Responsive, accessible frontend (React/Next.js)
                        - Secure backend API with authentication & authorization
                        - Normalized PostgreSQL database with indexes
                        - Automated testing suite
                        - Production Docker containerization and deployment plan
                        
                        Awarded upon completion: Full-Stack Web Developer Certificate signed by Awiskar Acharya.
                    """.trimIndent(),
                    codeSnippet = """
                        // WebDev Academy Capstone Milestone Verification
                        const capstoneRequirements = {
                          frontend: "React + TypeScript + Responsive CSS",
                          backend: "Node.js REST API with JWT Auth",
                          database: "PostgreSQL with migrations",
                          security: "OWASP Compliant & Helmet headers",
                          testing: "Unit & Integration test coverage > 80%",
                          certification: "Verified by Awiskar Acharya"
                        };
                    """.trimIndent(),
                    language = "TypeScript"
                )
            )
        )
    )

    val dailyChallenges: List<com.example.data.model.DailyCodingChallenge> = listOf(
        com.example.data.model.DailyCodingChallenge(
            day = "Today's Challenge",
            title = "Format URL Slug Generator",
            difficulty = "Medium",
            category = "JavaScript",
            problem = "Create a function `createSlug(title)` that converts any string into a lowercase, hyphen-separated SEO friendly slug without special characters.",
            starterCode = """
                function createSlug(title) {
                  // Your code here
                  return title.toLowerCase().trim().replace(/[^a-z0-9 ]/g, '').replace(/\s+/g, '-');
                }
                
                console.log(createSlug("WebDev Academy by Awiskar Acharya!"));
            """.trimIndent(),
            testCases = listOf(
                com.example.data.model.TestCase("Input: 'Hello World!'", "Output: 'hello-world'"),
                com.example.data.model.TestCase("Input: 'WebDev Academy'", "Output: 'webdev-academy'")
            ),
            solution = "function createSlug(t) { return t.toLowerCase().trim().replace(/[^a-z0-9 ]/g, '').replace(/\\s+/g, '-'); }"
        ),
        com.example.data.model.DailyCodingChallenge(
            day = "Tomorrow's Preview",
            title = "Array Deep Deduplication",
            difficulty = "Easy",
            category = "JavaScript",
            problem = "Write a function `uniqueArray(arr)` that returns only unique values maintaining original order.",
            starterCode = "function uniqueArray(arr) {\n  return [...new Set(arr)];\n}",
            testCases = listOf(
                com.example.data.model.TestCase("Input: [1, 2, 2, 3, 4, 4]", "Output: [1, 2, 3, 4]")
            ),
            solution = "const uniqueArray = arr => [...new Set(arr)];"
        )
    )

    fun getLevel(levelNumber: Int): RoadmapLevel? {
        return roadmapLevels.find { it.levelNumber == levelNumber }
    }
}

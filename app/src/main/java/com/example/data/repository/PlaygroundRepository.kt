package com.example.data.repository

data class SqlQueryResult(
    val columns: List<String>,
    val rows: List<List<String>>,
    val rowCount: Int,
    val executionTimeMs: Long
)

object PlaygroundRepository {

    // Default code templates
    const val DEFAULT_HTML = """<div class="academy-hero">
  <div class="badge">WebDev Academy</div>
  <h1>Build Real Web Applications</h1>
  <p>Learn. Code. Build. Deploy. Mentored by Awiskar Acharya.</p>
  <button id="cta-btn" class="glow-btn">Start Coding</button>
  <div id="output" class="status-msg"></div>
</div>"""

    const val DEFAULT_CSS = """.academy-hero {
  background: linear-gradient(135deg, #0f172a 0%, #1e1b4b 100%);
  color: #f8fafc;
  padding: 32px;
  border-radius: 16px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
  text-align: center;
  border: 1px solid #334155;
  box-shadow: 0 10px 30px rgba(0,0,0,0.5);
}

.badge {
  display: inline-block;
  background: rgba(56, 189, 248, 0.15);
  color: #38bdf8;
  padding: 6px 14px;
  border-radius: 9999px;
  font-size: 12px;
  font-weight: 600;
  margin-bottom: 12px;
  border: 1px solid #38bdf8;
}

h1 {
  font-size: 26px;
  margin: 8px 0;
  color: #ffffff;
}

p {
  color: #94a3b8;
  font-size: 14px;
  margin-bottom: 20px;
}

.glow-btn {
  background: #6366f1;
  color: #ffffff;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s, background 0.2s;
}

.glow-btn:hover {
  background: #4f46e5;
  transform: translateY(-2px);
}

.status-msg {
  margin-top: 16px;
  color: #34d399;
  font-weight: 500;
  font-size: 14px;
}"""

    const val DEFAULT_JS = """document.getElementById("cta-btn").addEventListener("click", () => {
  const output = document.getElementById("output");
  output.textContent = "⚡ Ready to build! Welcome to WebDev Academy by Awiskar Acharya.";
  console.log("Interactive Playground initiated successfully.");
});"""

    // Mock SQL Tables & Data
    val usersTable = listOf(
        listOf("1", "Awiskar Acharya", "awiskar@webdevacademy.io", "Lead Mentor", "9500"),
        listOf("2", "Elena Rostova", "elena@example.com", "Full-Stack Student", "4200"),
        listOf("3", "Marcus Chen", "marcus@example.com", "Frontend Dev", "3100"),
        listOf("4", "Sara Miller", "sara@example.com", "Backend Engineer", "5800")
    )

    val coursesTable = listOf(
        listOf("101", "HTML5 & Semantic Architecture", "Frontend", "Beginner", "Awiskar Acharya"),
        listOf("102", "Modern CSS & Flexbox/Grid", "Frontend", "Beginner", "Awiskar Acharya"),
        listOf("103", "JavaScript Mastery & Async", "Frontend", "Intermediate", "Awiskar Acharya"),
        listOf("104", "Node.js REST Services", "Backend", "Intermediate", "Awiskar Acharya"),
        listOf("105", "PostgreSQL & Database Design", "Database", "Intermediate", "Awiskar Acharya"),
        listOf("106", "Docker & CI/CD Pipelines", "DevOps", "Professional", "Awiskar Acharya")
    )

    fun executeMockSql(query: String): SqlQueryResult {
        val trimmed = query.trim().uppercase()
        val startTime = System.currentTimeMillis()

        return when {
            trimmed.contains("FROM COURSES") -> {
                val cols = listOf("id", "title", "category", "level", "mentor")
                val rows = if (trimmed.contains("WHERE") && trimmed.contains("FRONTEND")) {
                    coursesTable.filter { it[2] == "Frontend" }
                } else if (trimmed.contains("AWISKAR")) {
                    coursesTable.filter { it[4] == "Awiskar Acharya" }
                } else {
                    coursesTable
                }
                SqlQueryResult(cols, rows, rows.size, System.currentTimeMillis() - startTime + 8)
            }
            trimmed.contains("FROM USERS") -> {
                val cols = listOf("id", "name", "email", "role", "xp")
                val rows = if (trimmed.contains("AWISKAR")) {
                    usersTable.filter { it[1].contains("Awiskar") }
                } else {
                    usersTable
                }
                SqlQueryResult(cols, rows, rows.size, System.currentTimeMillis() - startTime + 5)
            }
            trimmed.contains("JOIN") -> {
                val cols = listOf("student_name", "enrolled_course", "mentor")
                val rows = listOf(
                    listOf("Awiskar Acharya", "HTML5 & Semantic Architecture", "Awiskar Acharya"),
                    listOf("Elena Rostova", "JavaScript Mastery & Async", "Awiskar Acharya"),
                    listOf("Marcus Chen", "Modern CSS & Flexbox/Grid", "Awiskar Acharya"),
                    listOf("Sara Miller", "Node.js REST Services", "Awiskar Acharya")
                )
                SqlQueryResult(cols, rows, rows.size, System.currentTimeMillis() - startTime + 14)
            }
            else -> {
                val cols = listOf("id", "status", "message")
                val rows = listOf(
                    listOf("1", "SUCCESS", "Executed: ${query.take(40)}... (1 row affected)")
                )
                SqlQueryResult(cols, rows, 1, System.currentTimeMillis() - startTime + 4)
            }
        }
    }

    // Terminal Commands simulator
    fun executeTerminalCommand(input: String): String {
        val cmd = input.trim()
        val parts = cmd.split(" ").filter { it.isNotBlank() }
        if (parts.isEmpty()) return ""

        return when (parts[0].lowercase()) {
            "help" -> """
                WebDev Academy Interactive Shell v2.4
                Available commands:
                  ls               - List files in current project directory
                  cat <file>       - Output contents of a file
                  pwd              - Print working directory
                  node -v          - Check Node.js version
                  npm run dev      - Launch local development server
                  git status       - Check git working tree status
                  git log          - Show recent git commits
                  whoami           - Display current developer profile
                  clear            - Clear terminal screen
            """.trimIndent()

            "ls" -> """
                package.json
                tsconfig.json
                src/
                  ├── index.html
                  ├── styles.css
                  ├── App.tsx
                  └── api.ts
                public/
                  └── favicon.ico
                README.md
            """.trimIndent()

            "pwd" -> "/home/developer/workspace/webdev-academy-app"

            "whoami" -> "developer@webdevacademy (Mentored by Awiskar Acharya)"

            "node" -> if (parts.size > 1 && parts[1] == "-v") "v20.14.0 (LTS Iron)" else "Welcome to Node.js v20.14.0. Type .help for more information."

            "npm" -> when {
                parts.size >= 3 && parts[1] == "run" && parts[2] == "dev" -> """
                    > webdev-academy@1.0.0 dev
                    > vite
                    
                    VITE v5.2.11  ready in 240 ms
                    
                    ➜  Local:   http://localhost:5173/
                    ➜  Network: http://192.168.1.100:5173/
                    ➜  press h + enter to show help
                """.trimIndent()
                parts.size >= 2 && parts[1] == "test" -> """
                    PASS src/App.test.tsx
                    PASS src/api.test.ts
                    Test Suites: 2 passed, 2 total
                    Tests:       12 passed, 12 total
                    Time:        1.42s
                """.trimIndent()
                else -> "Usage: npm run [script] | npm install | npm test"
            }

            "cat" -> when (parts.getOrNull(1)?.lowercase()) {
                "package.json" -> """
                    {
                      "name": "webdev-academy-fullstack",
                      "version": "1.0.0",
                      "creator": "Awiskar Acharya",
                      "scripts": {
                        "dev": "vite",
                        "build": "tsc && vite build",
                        "test": "vitest"
                      },
                      "dependencies": {
                        "react": "^19.0.0",
                        "react-dom": "^19.0.0"
                      }
                    }
                """.trimIndent()
                "readme.md" -> "# WebDev Academy Project\nCreated by Awiskar Acharya.\nEmpowering developers worldwide."
                else -> "cat: ${parts.getOrNull(1) ?: ""}: No such file or directory"
            }

            "git" -> when (parts.getOrNull(1)?.lowercase()) {
                "status" -> """
                    On branch main
                    Your branch is up to date with 'origin/main'.
                    
                    Changes not staged for commit:
                      (use "git add <file>..." to update what will be committed)
                        modified:   src/App.tsx
                        modified:   src/styles.css
                    
                    no changes added to commit (use "git add" to track)
                """.trimIndent()
                "log" -> """
                    commit a4f8e91d (HEAD -> main, origin/main)
                    Author: Awiskar Acharya <awiskar@webdevacademy.io>
                    Date:   Thu Sep 25 18:22:10 2026
                    
                        feat: implement interactive coding playgrounds & dev tools
                    
                    commit b2e7c30a
                    Author: Awiskar Acharya <awiskar@webdevacademy.io>
                    Date:   Wed Sep 24 14:10:05 2026
                    
                        feat: initial commit for WebDev Academy platform
                """.trimIndent()
                "branch" -> "* main"
                else -> "git: '${parts.getOrNull(1)}' is not a git command. See 'git --help'."
            }

            else -> "bash: ${parts[0]}: command not found. Type 'help' for command list."
        }
    }
}

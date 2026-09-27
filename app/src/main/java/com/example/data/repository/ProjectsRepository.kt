package com.example.data.repository

import com.example.data.model.ProjectItem
import com.example.data.model.ProjectMilestone

object ProjectsRepository {

    val projectsList: List<ProjectItem> = listOf(
        ProjectItem(
            id = "p1",
            title = "Personal Developer Portfolio",
            category = "Beginner",
            description = "Craft a sleek, accessible, responsive developer portfolio featuring dark mode, project showcase cards, and interactive contact form.",
            technologies = listOf("HTML5", "Modern CSS", "Flexbox/Grid", "JavaScript"),
            milestones = listOf(
                ProjectMilestone("1. Semantic HTML Structure", true, "Define header, hero section, skills grid, and contact modal"),
                ProjectMilestone("2. CSS Design System & Theme", true, "Setup CSS variables for dark/light themes and fluid typography"),
                ProjectMilestone("3. Interactive JS Filter", false, "Filter projects by tag and trigger modal window")
            ),
            architectureFlow = "HTML5 Skeleton ➔ Responsive CSS Variables ➔ Vanilla JS Interactivity ➔ GitHub Pages Deploy",
            requirements = listOf(
                "Fully mobile-responsive on viewports from 320px to 4K",
                "Accessible semantic tags with WCAG AAA color contrast",
                "Light/Dark theme toggle with local storage persistence",
                "Interactive project showcase filter"
            ),
            sampleSnippet = """
                <!-- Portfolio Hero Component -->
                <section class="hero-container">
                  <span class="role-chip">Full-Stack Engineer</span>
                  <h1 class="hero-name">Hi, I'm Awiskar Acharya</h1>
                  <p class="hero-bio">Building scalable web applications & empowering developers.</p>
                </section>
            """.trimIndent(),
            estimatedHours = 8
        ),
        ProjectItem(
            id = "p2",
            title = "TaskFlow Kanban SaaS Board",
            category = "Beginner",
            description = "A responsive drag-and-drop task management board with category columns, priority badges, and persistent browser storage.",
            technologies = listOf("JavaScript", "HTML Drag-and-Drop API", "LocalStorage", "CSS Flexbox"),
            milestones = listOf(
                ProjectMilestone("1. Layout Columns (Todo, In-Progress, Done)", true, "Build flex container and styled task card components"),
                ProjectMilestone("2. Drag & Drop Event Handlers", false, "Implement dragstart, dragover, and drop event listeners"),
                ProjectMilestone("3. LocalStorage Synchronization", false, "Auto-persist board state on every card move")
            ),
            architectureFlow = "Kanban UI ➔ Drag & Drop API ➔ State Reducer ➔ LocalStorage Persistence",
            requirements = listOf(
                "Create, edit, delete tasks with title and priority tags",
                "Drag cards seamlessly between columns",
                "State persists across browser reloads"
            ),
            sampleSnippet = """
                function handleCardDrop(e, targetColumnId) {
                  e.preventDefault();
                  const taskId = e.dataTransfer.getData("text/plain");
                  moveTaskToColumn(taskId, targetColumnId);
                  saveBoardState();
                }
            """.trimIndent(),
            estimatedHours = 10
        ),
        ProjectItem(
            id = "p3",
            title = "E-Commerce Storefront & Cart Engine",
            category = "Intermediate",
            description = "A complete shopping experience with category filtering, real-time cart drawer, price calculation, and mock Stripe checkout.",
            technologies = listOf("React", "TypeScript", "Tailwind CSS", "Context API"),
            milestones = listOf(
                ProjectMilestone("1. Product Grid & Filter Bar", true, "Render responsive grid with search and price range sliders"),
                ProjectMilestone("2. Cart Context & State Management", true, "Global cart store handling add, remove, and quantity updates"),
                ProjectMilestone("3. Checkout Flow & Form Validation", false, "Multi-step checkout with instant input validation")
            ),
            architectureFlow = "React Storefront ➔ Cart Context Reducer ➔ Mock REST API ➔ Checkout Simulation",
            requirements = listOf(
                "Filter catalog by categories, search keyword, and pricing",
                "Cart drawer with live quantity increment/decrement",
                "Calculate subtotal, tax, and promotional discounts"
            ),
            sampleSnippet = """
                interface CartItem {
                  id: string;
                  title: string;
                  price: number;
                  quantity: number;
                }
                
                export const useCart = () => useContext(CartContext);
            """.trimIndent(),
            estimatedHours = 16
        ),
        ProjectItem(
            id = "p4",
            title = "Full-Stack Blog CMS with Markdown",
            category = "Intermediate",
            description = "Production-ready publishing platform with Markdown editor, authenticated author dashboard, and PostgreSQL database.",
            technologies = listOf("Node.js", "Express", "PostgreSQL", "Prisma", "JWT"),
            milestones = listOf(
                ProjectMilestone("1. Database Schema & Prisma Models", true, "Define User, Post, Category, and Tag tables"),
                ProjectMilestone("2. Auth Middleware & REST Controllers", true, "Implement JWT verification and CRUD endpoints"),
                ProjectMilestone("3. Markdown Parser & Image Uploads", false, "Render sanitized HTML from raw markdown content")
            ),
            architectureFlow = "Client UI ➔ Express REST Routes ➔ JWT Auth Guard ➔ Prisma ORM ➔ PostgreSQL",
            requirements = listOf(
                "Secure user registration and login with bcrypt hashing",
                "Draft, schedule, and publish posts with slug generation",
                "Markdown rendering with syntax highlighting for code blocks"
            ),
            sampleSnippet = """
                router.post("/posts", requireAuth, async (req, res) => {
                  const { title, content, tags } = req.body;
                  const slug = createSlug(title);
                  const post = await prisma.post.create({
                    data: { title, slug, content, authorId: req.user.id }
                  });
                  res.status(201).json(post);
                });
            """.trimIndent(),
            estimatedHours = 20
        ),
        ProjectItem(
            id = "p5",
            title = "Real-Time Collaborative Workspace",
            category = "Advanced",
            description = "Enterprise team collaboration platform featuring live WebSocket messaging, user presence indicators, and document co-editing.",
            technologies = listOf("Next.js", "WebSockets / Socket.io", "Redis", "Docker"),
            milestones = listOf(
                ProjectMilestone("1. WebSocket Connection Hub", true, "Setup resilient bi-directional socket connections with reconnect logic"),
                ProjectMilestone("2. Real-time Cursor & Presence", false, "Broadcast active users and typing indicators across channels"),
                ProjectMilestone("3. Redis Pub/Sub Scaling", false, "Scale socket events across multiple server nodes using Redis")
            ),
            architectureFlow = "Browser Socket ➔ Load Balancer ➔ Node WebSocket Server ➔ Redis Pub/Sub ➔ DB",
            requirements = listOf(
                "Sub-50ms message latency across concurrent users",
                "Presence system displaying online, idle, and offline statuses",
                "Optimistic UI updates with rollback on network disconnection"
            ),
            sampleSnippet = """
                io.on("connection", (socket) => {
                  socket.on("join-room", (roomId) => {
                    socket.join(roomId);
                    socket.to(roomId).emit("user-joined", { id: socket.id });
                  });
                });
            """.trimIndent(),
            estimatedHours = 32
        ),
        ProjectItem(
            id = "p6",
            title = "WebDev Academy Production Capstone",
            category = "Advanced",
            description = "The ultimate comprehensive web development project: a production-ready educational SaaS integrating frontend, backend, PostgreSQL, OAuth, testing, and Docker deployment.",
            technologies = listOf("React 19", "Node.js", "PostgreSQL", "Docker", "Vitest", "Playwright"),
            milestones = listOf(
                ProjectMilestone("1. High-Level System Architecture", true, "Define data flow, API contracts, and ER diagrams"),
                ProjectMilestone("2. Frontend Component System", true, "Develop responsive UI matching Figma specifications"),
                ProjectMilestone("3. Secure API & Database Transactions", false, "ACID compliant endpoints with rate limiting"),
                ProjectMilestone("4. Automated CI/CD & Dockerization", false, "Continuous testing workflow with multi-stage build"),
                ProjectMilestone("5. Code Review & Verification", false, "Complete assessment and certification signed by Awiskar Acharya")
            ),
            architectureFlow = "Vite/React Client ➔ Nginx Proxy ➔ Express Backend ➔ PostgreSQL Cluster ➔ Redis Cache",
            requirements = listOf(
                "End-to-end full stack with zero external template dependencies",
                "Automated test coverage exceeding 80%",
                "Containerized with Docker Compose for single-command launch",
                "Certificate of Completion issued upon submission"
            ),
            sampleSnippet = """
                // Final Capstone Verification Manifest
                export const CAPSTONE_MANIFEST = {
                  project: "WebDev Academy Full-Stack Platform",
                  mentor: "Awiskar Acharya",
                  gradeThreshold: "90%",
                  deploymentTarget: "Production Docker Cluster"
                };
            """.trimIndent(),
            estimatedHours = 40
        )
    )

    fun generateCustomProject(
        frontend: String,
        backend: String,
        database: String,
        projectType: String,
        difficulty: String
    ): ProjectItem {
        return ProjectItem(
            id = "generated_${System.currentTimeMillis()}",
            title = "$projectType with $frontend & $backend",
            category = difficulty,
            description = "Custom roadmap generated for a $difficulty $projectType using $frontend on the client, $backend on the server, and $database for persistence.",
            technologies = listOf(frontend, backend, database, "REST API", "Docker"),
            milestones = listOf(
                ProjectMilestone("1. Scaffold $frontend & Configure Routing", false, "Initialize project and structure component trees"),
                ProjectMilestone("2. Build $backend API with $database", false, "Design schema and implement CRUD endpoints"),
                ProjectMilestone("3. Connect Client to Server API", false, "Implement async data fetching, error boundaries, and loading states"),
                ProjectMilestone("4. Authentication & Security Audit", false, "Implement token auth, CSRF protection, and input validation"),
                ProjectMilestone("5. Testing & Cloud Deployment", false, "Write unit tests and deploy with CI/CD pipeline")
            ),
            architectureFlow = "$frontend (Client) ➔ HTTP/REST ➔ $backend (Server) ➔ $database (Persistent Storage)",
            requirements = listOf(
                "Clean separation between frontend presentation and backend services",
                "Proper data validation on both client and server sides",
                "Responsive design compatible with mobile and desktop"
            ),
            sampleSnippet = "// Generated architecture blueprint\n// Frontend: $frontend\n// Backend: $backend\n// Database: $database",
            estimatedHours = if (difficulty == "Beginner") 12 else if (difficulty == "Intermediate") 24 else 45
        )
    }
}

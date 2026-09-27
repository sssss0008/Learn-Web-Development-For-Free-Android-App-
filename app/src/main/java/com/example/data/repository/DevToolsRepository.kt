package com.example.data.repository

import com.example.data.model.DevToolItem
import com.example.data.model.HttpStatusItem

object DevToolsRepository {

    val toolsList: List<DevToolItem> = listOf(
        DevToolItem("json", "JSON Formatter & Validator", "Data", "Beautify, minify, validate, and inspect JSON structures.", "code-json"),
        DevToolItem("regex", "Regex Lab & Tester", "Testing", "Test regular expressions in real-time with pattern analysis.", "regex"),
        DevToolItem("http", "HTTP Status Explorer", "Network", "Search and examine 1xx–5xx codes, scenarios, and debugging tips.", "web"),
        DevToolItem("color", "Color Tools & Palette", "Design", "Convert HEX, RGB, HSL, inspect contrast, and build color shades.", "palette"),
        DevToolItem("shadow", "CSS Box Shadow Generator", "Design", "Visually design multi-layer soft shadows and copy CSS code.", "box-shadow"),
        DevToolItem("encoder", "Base64 & URL Encoder", "Security", "Encode/decode strings, URL components, and Base64 payloads.", "lock-reset"),
        DevToolItem("storage", "Web Storage Simulator", "Browser", "Explore localStorage, sessionStorage, and cookie behaviors.", "database"),
        DevToolItem("api_tester", "API Request Inspector", "Network", "Simulate GET, POST, PUT, DELETE requests with headers & response.", "send")
    )

    val httpStatusList: List<HttpStatusItem> = listOf(
        HttpStatusItem(200, "OK", "2xx", "Standard response for successful HTTP requests.", "User retrieved successfully.", "Verify response payload matches expected schema."),
        HttpStatusItem(201, "Created", "2xx", "The request has succeeded and a new resource has been created.", "POST /api/users returns 201 with newly created user object.", "Check Location header for the new resource URL."),
        HttpStatusItem(204, "No Content", "2xx", "The server successfully processed the request and is not returning any content.", "DELETE /api/items/42 successfully removed item.", "Do not expect a JSON body in the response."),
        HttpStatusItem(301, "Moved Permanently", "3xx", "The requested resource has been definitively assigned a new permanent URI.", "Redirecting old HTTP URLs to modern HTTPS.", "Update bookmarks and hardcoded links to the new URI."),
        HttpStatusItem(304, "Not Modified", "3xx", "Indicates that the resource has not been modified since the version specified by If-Modified-Since.", "Client revalidating cached CSS bundle.", "Browser serves from disk cache; no body sent over network."),
        HttpStatusItem(400, "Bad Request", "4xx", "The server cannot process the request due to client error (e.g. malformed syntax).", "Missing required field in JSON payload.", "Validate request body schema and headers before dispatching."),
        HttpStatusItem(401, "Unauthorized", "4xx", "The request requires user authentication credentials or token.", "Attempting to access user settings without Bearer token.", "Provide valid Authorization header or log in again."),
        HttpStatusItem(403, "Forbidden", "4xx", "The server understood the request, but refuses to authorize it.", "A regular user trying to access admin dashboard endpoints.", "Verify current user role and permissions in JWT."),
        HttpStatusItem(404, "Not Found", "4xx", "The origin server did not find a current representation for the target resource.", "Navigating to a deleted post URL or typo in endpoint.", "Verify spelling of URL route path and parameters."),
        HttpStatusItem(429, "Too Many Requests", "4xx", "The user has sent too many requests in a given amount of time (rate limited).", "Exceeded 60 requests per minute API quota.", "Inspect Retry-After header and implement exponential backoff."),
        HttpStatusItem(500, "Internal Server Error", "5xx", "The server encountered an unexpected condition that prevented it from fulfilling the request.", "Unhandled exception or database crash in backend route handler.", "Check server-side logs and stack trace."),
        HttpStatusItem(502, "Bad Gateway", "5xx", "The server, while acting as a gateway or proxy, received an invalid response.", "Nginx proxy unable to communicate with upstream Node.js service.", "Ensure backend daemon or container is running and port is open."),
        HttpStatusItem(503, "Service Unavailable", "5xx", "The server is currently unable to handle the request due to maintenance or overload.", "Scheduled system upgrade or traffic spike.", "Wait and retry, or implement circuit breaker pattern.")
    )

    fun formatJson(input: String): String {
        return try {
            val trimmed = input.trim()
            if (!trimmed.startsWith("{") && !trimmed.startsWith("[")) {
                return "Error: Input must begin with { or ["
            }
            var indent = 0
            val sb = StringBuilder()
            var inQuotes = false
            for (char in trimmed) {
                when (char) {
                    '"' -> {
                        inQuotes = !inQuotes
                        sb.append(char)
                    }
                    '{', '[' -> {
                        sb.append(char)
                        if (!inQuotes) {
                            indent++
                            sb.append("\n").append("  ".repeat(indent))
                        }
                    }
                    '}', ']' -> {
                        if (!inQuotes) {
                            indent = maxOf(0, indent - 1)
                            sb.append("\n").append("  ".repeat(indent))
                        }
                        sb.append(char)
                    }
                    ',' -> {
                        sb.append(char)
                        if (!inQuotes) {
                            sb.append("\n").append("  ".repeat(indent))
                        }
                    }
                    ':' -> {
                        sb.append(char)
                        if (!inQuotes) sb.append(" ")
                    }
                    ' ', '\n', '\r', '\t' -> {
                        if (inQuotes) sb.append(char)
                    }
                    else -> sb.append(char)
                }
            }
            sb.toString()
        } catch (e: Exception) {
            "JSON Format Error: ${e.message}"
        }
    }
}

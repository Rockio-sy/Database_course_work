const express = require("express");
const cors = require("cors");
const path = require("path");
const app = express();

app.use(cors({
    exposedHeaders: ["Authorization"] // Allow frontend to access Authorization header
}));
app.use(express.json()); // Parse JSON bodies
app.use(express.static("public")); // Serve static files

// Fake user database (replace with a real database)
const users = {
    "admin": "password123" // Example user
};

// Serve login page
app.get("/login", (req, res) => {
    res.sendFile(path.join(__dirname, "public", "login.html"));
});

// Serve registration page
app.get("/register", (req, res) => {
    res.sendFile(path.join(__dirname, "public", "register.html"));
});

// ✅ Authentication Route
app.post("/auth/login", (req, res) => {
    const { username, password } = req.body;

    console.log("Login request received:", username, password);

    if (users[username] && users[username] === password) {
        const token = "Bearer fake-jwt-token"; // Replace with real JWT generation

        res.setHeader("Authorization", token); // Set the token in the header
        res.status(200).json({ message: "Login successful" });
    } else {
        res.status(401).json({ message: "Invalid username or password" });
    }
});

// Start server
const PORT = 3000;
app.listen(PORT, () => {
    console.log(`🚀 Server running at http://localhost:${PORT}`);
});

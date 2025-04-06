// Handle login form submission
document.getElementById("loginForm")?.addEventListener("submit", async function(event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("http://localhost:8080/auth/login?username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password), {
            method: "POST"
        });

        if (response.ok) {
            const userId = await response.text(); // Assuming backend returns user ID as text
            localStorage.setItem("userId", userId);
            alert("Login successful! Redirecting...");
            window.location.href = "dashboard.html"; // Redirect to dashboard after login
        }else if (response === 400){
            alert("Check your fields.")
        }
        else {
            alert("Login failed. Check your username and password.");
        }
    } catch (error) {
        alert("Error logging in: " + error.message);
    }
});

// Handle register form submission
document.getElementById("registerForm")?.addEventListener("submit", async function(event) {
    event.preventDefault();

    const fullName = document.getElementById("fullName").value;
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const user = { fullName, username, email, password };

    try {
        const response = await fetch("http://localhost:8080/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        });

        if (response.ok) {
            const savedUser = await response.json();
            alert("Registration successful! Redirecting to login...");
            window.location.href = "login.html"; // Redirect to login after registration
        } else {
            // If the response status is 400, handle it as a validation error
            const errorMessages = await response.json();

            // Generate a friendly message based on the backend error response
            let friendlyMessage = "Please correct the following errors:\n";

            for (const [field, message] of Object.entries(errorMessages)) {
                friendlyMessage += `- ${field}: ${message}\n`;  // Display field name and error message
            }

            // Show the user-friendly error message
            alert(friendlyMessage);

            // Optionally, you can also display the error in the HTML (e.g., inside a div)
            displayErrorMessages(errorMessages);
        }
    } catch (error) {
        alert("Error registering: " + error.message);
    }
});

// Function to display error messages inside the form (optional)
function displayErrorMessages(errorMessages) {
    const errorContainer = document.getElementById("errorMessages");
    errorContainer.innerHTML = ""; // Clear any previous error messages

    Object.entries(errorMessages).forEach(([field, message]) => {
        const errorItem = document.createElement("li");
        errorItem.textContent = `${field}: ${message}`;
        errorContainer.appendChild(errorItem);
    });
}


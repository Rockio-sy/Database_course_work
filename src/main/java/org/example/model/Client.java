package org.example.model;

import java.sql.Timestamp;

public class Client {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;

    public Client() {
    }

    // Constructor for creating a client (without ID for new clients)
    public Client(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Constructor for all fields (including ID for retrieval or update)
    public Client(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        // ANSI color codes
        String reset = "\033[0m";  // Reset all styles
        String bold = "\033[1m";   // Bold text
        String red = "\033[31m";   // Red text
        String green = "\033[32m"; // Green text
        String yellow = "\033[33m"; // Yellow text
        String blue = "\033[34m";  // Blue text
        String purple = "\033[35m"; // Purple text
        String cyan = "\033[36m";  // Cyan text

        return bold + "Client{" + reset +
                bold + red + " id=" + reset + cyan + id + reset +
                bold + green + ", name='" + reset + yellow + name + '\'' + reset +
                bold + blue + ", email='" + reset + purple + email + '\'' + reset +
                bold + green + ", phoneNumber='" + reset + yellow + phoneNumber + '\'' + reset +
                bold + red + " }" + reset;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

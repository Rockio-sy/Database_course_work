package org.example.model;

import java.sql.Timestamp;

public class Subscription {

    private Long id;
    private String name;
    private double price;
    private String features;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;

    public Subscription(){}
    // Constructor for creating a subscription (without ID for new subscriptions)
    public Subscription(String name, double price, String features) {
        this.name = name;
        this.price = price;
        this.features = features;
    }

    public Subscription(Long id, String name, double price, String features) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.features = features;
    }

    // Constructor for all fields (including ID for retrieval or update)
    public Subscription(Long id, String name, double price, String features, java.sql.Timestamp createdAt, java.sql.Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.features = features;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
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

        return bold + "Subscription{" + reset +
                bold + red + " id=" + reset + cyan + id + reset +
                bold + green + ", name='" + reset + yellow + name + '\'' + reset +
                bold + blue + ", price=" + reset + purple + price + reset +
                bold + green + ", features='" + reset + yellow + features + '\'' + reset +
                bold + blue + ", createdAt=" + reset + purple + createdAt + reset +
                bold + green + ", updatedAt=" + reset + yellow + updatedAt + reset +
                bold + red + " }" + reset;
    }
}

package org.example.model;


import java.time.LocalDate;

public class ClientSubscription {

    private Long clientId;
    private Long subscriptionId;
    private LocalDate startDate;
    private LocalDate endDate;

    public ClientSubscription(){}
    // Constructor for creating a client-subscription relation (without IDs for new mappings)
    public ClientSubscription(Long clientId, Long subscriptionId, LocalDate startDate, LocalDate endDate) {
        this.clientId = clientId;
        this.subscriptionId = subscriptionId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

        return bold + "ClientSubscription{" + reset +
                bold + red + " clientId=" + reset + cyan + clientId + reset +
                bold + green + ", subscriptionId=" + reset + yellow + subscriptionId + reset +
                bold + blue + ", startDate=" + reset + purple + startDate + reset +
                bold + green + ", endDate=" + reset + yellow + endDate + reset +
                bold + red + " }" + reset;
    }
}

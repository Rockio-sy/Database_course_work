package org.example.repository;

import org.example.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a Subscription
    public void addSubscription(Subscription subscription) {
        String sql = "INSERT INTO subscriptions (name, price, features) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, subscription.getName(), subscription.getPrice(), subscription.getFeatures());
    }

    // Get All Subscriptions
    public List<Subscription> getAllSubscriptions() {
        String sql = "SELECT * FROM subscriptions";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Subscription(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getString("features")
        ));
    }

    // Get a Subscription by ID
    public Subscription getSubscriptionById(Long subscriptionId) {
        String sql = "SELECT * FROM subscriptions WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{subscriptionId}, (rs, rowNum) -> new Subscription(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("features")
            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Update a Subscription
    public void updateSubscription(Subscription subscription) {
        String sql = "UPDATE subscriptions SET name = ?, price = ?, features = ? WHERE id = ?";
        jdbcTemplate.update(sql, subscription.getName(), subscription.getPrice(), subscription.getFeatures(), subscription.getId());
    }

    // Delete a Subscription
    public void deleteSubscription(Long subscriptionId) {
        String sql = "DELETE FROM subscriptions WHERE id = ?";
        jdbcTemplate.update(sql, subscriptionId);
    }
}


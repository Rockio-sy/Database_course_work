package org.example.repository;

import org.example.model.ClientSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientSubscriptionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Assign Subscription to Client
    public void assignSubscriptionToClient(ClientSubscription clientSubscription) {
        String sql = "INSERT INTO client_subscriptions (client_id, subscription_id, start_date, end_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, clientSubscription.getClientId(), clientSubscription.getSubscriptionId(),
                clientSubscription.getStartDate(), clientSubscription.getEndDate());
    }

    // Get All Client Subscriptions
    public List<ClientSubscription> getAllClientSubscriptions() {
        String sql = "SELECT * FROM client_subscriptions";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ClientSubscription(
                rs.getLong("client_id"),
                rs.getLong("subscription_id"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate()
        ));
    }

    // Remove Subscription from Client
    public void removeSubscriptionFromClient(Long clientId, Long subscriptionId) {
        String sql = "DELETE FROM client_subscriptions WHERE client_id = ? AND subscription_id = ?";
        jdbcTemplate.update(sql, clientId, subscriptionId);
    }
}

package org.example.repository;

import org.example.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Add a Client
    public void addClient(Client client) {
        String sql = "INSERT INTO clients (name, email, phone_number) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, client.getName(), client.getEmail(), client.getPhoneNumber());
    }

    // Get All Clients
    public List<Client> getAllClients() {
        String sql = "SELECT * FROM clients";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Client(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone_number")
        ));
    }

    // Get a Client by ID
    public Client getClientById(Long clientId) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{clientId}, (rs, rowNum) -> new Client(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone_number")
            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Update a Client
    public void updateClient(Client client) {
        String sql = "UPDATE clients SET name = ?, email = ?, phone_number = ? WHERE id = ?";
        jdbcTemplate.update(sql, client.getName(), client.getEmail(), client.getPhoneNumber(), client.getId());
    }

    // Delete a Client
    public void deleteClient(Long clientId) {
        String sql = "DELETE FROM clients WHERE id = ?";
        jdbcTemplate.update(sql, clientId);
    }

    public boolean isExist(String name, String email, String phone){
        String sql = "SELECT 1 FROM clients WHERE  name = ? OR email = ? OR phone_number = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, name, email, phone);
        return count > 0;
    }
}

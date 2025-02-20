package org.example.service;

import org.example.model.Client;
import org.example.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Add a Client
    public boolean addClient(Client client) {
        if(clientRepository.isExist(client.getName(), client.getEmail(), client.getPhoneNumber())){
            return false;
        }
        clientRepository.addClient(client);
        return true;
    }

    // Get All Clients
    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    // Get a Client by ID
    public Client getClientById(Long clientId) {
        return clientRepository.getClientById(clientId);
    }

    // Update a Client
    public void updateClient(Client client) {
        if (clientRepository.getClientById(client.getId()) != null) {
            clientRepository.updateClient(client);
        } else {
            throw new RuntimeException("Client not found!");
        }
    }

    // Delete a Client
    public void deleteClient(Long clientId) {
        if (clientRepository.getClientById(clientId) != null) {
            clientRepository.deleteClient(clientId);
        } else {
            throw new RuntimeException("Client not found!");
        }
    }
}

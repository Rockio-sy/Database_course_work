package org.example.service;

import org.example.model.ClientSubscription;
import org.example.repository.ClientSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientSubscriptionService {

    @Autowired
    private ClientSubscriptionRepository clientSubscriptionRepository;

    // Assign Subscription to Client
    public void assignSubscriptionToClient(ClientSubscription clientSubscription) {
        clientSubscriptionRepository.assignSubscriptionToClient(clientSubscription);
    }

    // Get All Client Subscriptions
    public List<ClientSubscription> getAllClientSubscriptions() {
        return clientSubscriptionRepository.getAllClientSubscriptions();
    }

    // Remove Subscription from Client
    public void removeSubscriptionFromClient(Long clientId, Long subscriptionId) {
        clientSubscriptionRepository.removeSubscriptionFromClient(clientId, subscriptionId);
    }
}

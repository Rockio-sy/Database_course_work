package org.example.service;

import org.example.model.Subscription;
import org.example.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // Add a Subscription
    public void addSubscription(Subscription subscription) {
        subscriptionRepository.addSubscription(subscription);
    }

    // Get All Subscriptions
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.getAllSubscriptions();
    }

    // Get a Subscription by ID
    public Subscription getSubscriptionById(Long subscriptionId) {
        return subscriptionRepository.getSubscriptionById(subscriptionId);
    }

    // Update a Subscription
    public void updateSubscription(Subscription subscription) {
        if (subscriptionRepository.getSubscriptionById(subscription.getId()) != null) {
            subscriptionRepository.updateSubscription(subscription);
        } else {
            throw new RuntimeException("Subscription not found!");
        }
    }

    // Delete a Subscription
    public void deleteSubscription(Long subscriptionId) {
        if (subscriptionRepository.getSubscriptionById(subscriptionId) != null) {
            subscriptionRepository.deleteSubscription(subscriptionId);
        } else {
            throw new RuntimeException("Subscription not found!");
        }
    }
}

package com.driver.services;

import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto) {
        // Fetch the user from the repository
        System.out.println("User ID received: " + subscriptionEntryDto.getUserId());
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        // Calculate cost based on subscription type and number of screens
        int cost = calculateCost(subscriptionEntryDto.getSubscriptionType(), subscriptionEntryDto.getNoOfScreensRequired());

        // Create and save subscription
        Subscription subscription = new Subscription(
                subscriptionEntryDto.getSubscriptionType(),
                subscriptionEntryDto.getNoOfScreensRequired(),
                new java.util.Date(),
                cost,
                user
        );

        subscriptionRepository.save(subscription);
        return cost; // Return total amount to be paid
    }

    public Integer upgradeSubscription(Integer userId) throws Exception {
        // Fetch the user and subscription
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        Subscription subscription = subscriptionRepository.findByCustomer(user);
        if (subscription == null) {
            throw new RuntimeException("Subscription not found for user!");
        }

        // Check if already on ELITE plan
        if (subscription.getSubscriptionType() == SubscriptionType.ELITE) {
            throw new Exception("Already the best Subscription");
        }

        // Determine the upgraded subscription type
        SubscriptionType upgradedType = subscription.getSubscriptionType() == SubscriptionType.BASIC
                ? SubscriptionType.PRO
                : SubscriptionType.ELITE;

        // Calculate the new cost
        int newCost = calculateCost(upgradedType, subscription.getNoOfScreensSubscribed());
        int priceDifference = newCost - subscription.getTotalAmountPaid();

        // Update subscription
        subscription.setSubscriptionType(upgradedType);
        subscription.setTotalAmountPaid(newCost);
        subscriptionRepository.save(subscription);

        return priceDifference; // Return additional amount to be paid
    }

    public Integer calculateTotalRevenueOfHotstar() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        int totalRevenue = 0;
        for (Subscription subscription : subscriptions) {
            totalRevenue += subscription.getTotalAmountPaid();
        }
        return totalRevenue;
    }

    private int calculateCost(SubscriptionType type, int screens) {
        switch (type) {
            case BASIC:
                return 500 + (200 * screens);
            case PRO:
                return 800 + (250 * screens);
            case ELITE:
                return 1000 + (350 * screens);
            default:
                return 0;
        }
    }
}

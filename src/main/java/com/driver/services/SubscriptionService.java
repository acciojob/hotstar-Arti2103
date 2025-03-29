package com.driver.services;

import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(SubscriptionService.class.getName());

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto) {
        logger.info("Processing buySubscription for User ID: " + subscriptionEntryDto.getUserId());

        // Fetch user from the repository
        User user = userRepository.findById(subscriptionEntryDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Calculate subscription cost
        int cost = calculateCost(subscriptionEntryDto.getSubscriptionType(), subscriptionEntryDto.getNoOfScreensRequired());

        // Create new subscription
        Subscription subscription = new Subscription(
                subscriptionEntryDto.getSubscriptionType(),
                subscriptionEntryDto.getNoOfScreensRequired(),
                new Date(),
                cost,
                user
        );

        // Save subscription
        subscriptionRepository.save(subscription);
        logger.info("Subscription purchased successfully. Amount Paid: " + cost);
        return cost;
    }

    public Integer upgradeSubscription(Integer userId) throws Exception {
        logger.info("Processing upgradeSubscription for User ID: " + userId);

        // Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Fetch subscription
        Subscription subscription = Optional.ofNullable(subscriptionRepository.findByCustomer(user))
                .orElseThrow(() -> new RuntimeException("Subscription not found for user!"));

        // Check if already on ELITE plan
        if (subscription.getSubscriptionType() == SubscriptionType.ELITE) {
            throw new Exception("Already the best Subscription");
        }

        // Determine the upgraded subscription type
        SubscriptionType upgradedType = (subscription.getSubscriptionType() == SubscriptionType.BASIC)
                ? SubscriptionType.PRO
                : SubscriptionType.ELITE;

        // Calculate the new cost and price difference
        int newCost = calculateCost(upgradedType, subscription.getNoOfScreensSubscribed());
        int priceDifference = newCost - subscription.getTotalAmountPaid();

        // Update subscription details
        subscription.setSubscriptionType(upgradedType);
        subscription.setTotalAmountPaid(newCost);
        subscriptionRepository.save(subscription);

        logger.info("Subscription upgraded successfully. Additional Amount Paid: " + priceDifference);
        return priceDifference;
    }

    public Integer calculateTotalRevenueOfHotstar() {
        logger.info("Calculating total revenue of Hotstar...");
        return subscriptionRepository.findAll().stream()
                .mapToInt(Subscription::getTotalAmountPaid)
                .sum();
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
                throw new IllegalArgumentException("Invalid subscription type: " + type);
        }
    }
}

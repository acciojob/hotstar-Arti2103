package com.driver.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    private int noOfScreensSubscribed;

    @Temporal(TemporalType.DATE)
    private Date startSubscriptionDate;

    private int totalAmountPaid;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User customer;  // ✅ Changed from `user` to `customer`

    // Default constructor
    public Subscription() {}

    public Subscription(SubscriptionType subscriptionType, int noOfScreensSubscribed, Date startSubscriptionDate, int totalAmountPaid, User customer) {
        this.subscriptionType = subscriptionType;
        this.noOfScreensSubscribed = noOfScreensSubscribed;
        this.startSubscriptionDate = startSubscriptionDate;
        this.totalAmountPaid = totalAmountPaid;
        this.customer = customer;
    }
    public Subscription(SubscriptionType subscriptionType, int noOfScreensSubscribed, Date startSubscriptionDate, int totalAmountPaid) {
        this.subscriptionType = subscriptionType;
        this.noOfScreensSubscribed = noOfScreensSubscribed;
        this.startSubscriptionDate = startSubscriptionDate;
        this.totalAmountPaid = totalAmountPaid;
    }


    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public SubscriptionType getSubscriptionType() { return subscriptionType; }
    public void setSubscriptionType(SubscriptionType subscriptionType) { this.subscriptionType = subscriptionType; }

    public int getNoOfScreensSubscribed() { return noOfScreensSubscribed; }
    public void setNoOfScreensSubscribed(int noOfScreensSubscribed) { this.noOfScreensSubscribed = noOfScreensSubscribed; }

    public Date getStartSubscriptionDate() { return startSubscriptionDate; }
    public void setStartSubscriptionDate(Date startSubscriptionDate) { this.startSubscriptionDate = startSubscriptionDate; }

    public int getTotalAmountPaid() { return totalAmountPaid; }
    public void setTotalAmountPaid(int totalAmountPaid) { this.totalAmountPaid = totalAmountPaid; }

    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }
}

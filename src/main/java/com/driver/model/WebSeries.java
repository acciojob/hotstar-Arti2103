package com.driver.model;


import javax.persistence.*;

@Entity
@Table
public class WebSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String seriesName;

    private int ageLimit;

    private double rating;
    @Enumerated(EnumType.STRING)

    private SubscriptionType subscriptionType; //This denotes with which of subscriptionType this webseries comes ie. BASIC,PRO, ELITE

    @ManyToOne
    @JoinColumn
    private ProductionHouse productionHouse;
    public WebSeries() {
        // Default constructor required by JPA
    }
    public WebSeries(String seriesName, int ageLimit, double rating, SubscriptionType subscriptionType) {
        this.seriesName = seriesName;
        this.ageLimit = ageLimit;
        this.rating = rating;
        this.subscriptionType = subscriptionType;
    }

    public WebSeries(String seriesName, int ageLimit, double rating, SubscriptionType subscriptionType, ProductionHouse productionHouse) {
        this.seriesName = seriesName;
        this.ageLimit = ageLimit;
        this.rating = rating;
        this.subscriptionType = subscriptionType;
        this.productionHouse = productionHouse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public ProductionHouse getProductionHouse() {
        return productionHouse;
    }

    public void setProductionHouse(ProductionHouse productionHouse) {
        this.productionHouse = productionHouse;
    }
}

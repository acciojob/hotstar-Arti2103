package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;
    private String mobNo;


    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)  // Changed from "user" to "customer"
    private Subscription subscription;

    // Constructors
    public User() {}

    public User(int id, String name, int age, String mobNo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mobNo = mobNo;
    }

    public User(String name, int age, String mobNo) {
        this.name = name;
        this.age = age;
        this.mobNo = mobNo;
    }
    public User(int id, String name, int age, Subscription subscription) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.subscription = subscription;
    }
    public User(int id, String name, int age, String mobNo, Subscription subscription) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mobNo = mobNo;
        this.subscription = subscription;
    }


    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getMobNo() { return mobNo; }
    public void setMobNo(String mobNo) { this.mobNo = mobNo; }

    public Subscription getSubscription() { return subscription; }
    public void setSubscription(Subscription subscription) { this.subscription = subscription; }
}

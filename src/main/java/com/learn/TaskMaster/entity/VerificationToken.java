package com.learn.TaskMaster.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_user_id", nullable = false)
    private User user;

    private Date expiryDate;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public VerificationToken(long id, User user, Date expiryDate) {
        this.id = id;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public VerificationToken() {

    }
}

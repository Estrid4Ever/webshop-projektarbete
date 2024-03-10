package com.webshop.webshopprojektarbete.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Random;

@Entity
public class Confirmation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    //@Column(name = "user_id", nullable = false, length = 200)
    @Column(name = "user_id", insertable=false, updatable=false, length = 200)
    private String userId;
    @Basic
    @Column(name = "token", nullable = false, length = 45)
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email", nullable = false)
    private Users usersByUserId;
    public Confirmation (Users user){
        this.usersByUserId = user;
        this.token = generateRandomNumber();
    }
    private String generateRandomNumber(){
        Random rand = new Random();
        return String.valueOf(rand.nextInt(10000));

    }

    public Confirmation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Confirmation that = (Confirmation) o;
        return id == that.id && Objects.equals(userId, that.userId) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, token);
    }

    public Users getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }
}

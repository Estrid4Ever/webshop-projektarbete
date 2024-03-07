package com.webshop.webshopprojektarbete.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "email", nullable = false, length = 200)
    private String email;
    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic
    @Column(name = "lastname", nullable = false, length = 45)
    private String lastname;
    @Basic
    @Column(name = "password", nullable = false, length = 45)
    private String password;
    @Basic
    @Column(name = "is_admin", nullable = false)
    private byte isAdmin;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<Order> ordersByEmail;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return isAdmin == users.isAdmin && Objects.equals(email, users.email) && Objects.equals(name, users.name) && Objects.equals(lastname, users.lastname) && Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, lastname, password, isAdmin);
    }

    public Collection<Order> getOrdersByEmail() {
        return ordersByEmail;
    }

    public void setOrdersByEmail(Collection<Order> ordersByEmail) {
        this.ordersByEmail = ordersByEmail;
    }
}

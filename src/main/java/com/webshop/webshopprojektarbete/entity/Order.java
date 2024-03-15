package com.webshop.webshopprojektarbete.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "customer_order")
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    //@Column(name = "user_id", nullable = false, length = 200)
    @Column(name = "user_id", insertable=false, updatable=false, length = 200)
    private String userId;
    @Basic
    @Column(name = "status", nullable = false)
    private Object status;
    @Basic
    @Column(name = "order_time", nullable = false)
    private Timestamp orderTime;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email", nullable = false)
    private Users usersByUserId;
    @OneToMany(mappedBy = "orderByOrderId")
    private Collection<Orderline> orderlinesById;

    public Order() {
    }

    public Order(String userId, Object status, LocalDateTime orderTime, Users usersByUserId) {
        this.userId = userId;
        this.status = status;
        this.orderTime = Timestamp.valueOf(orderTime);
        this.usersByUserId = usersByUserId;
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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(userId, order.userId) && Objects.equals(status, order.status) && Objects.equals(orderTime, order.orderTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, status, orderTime);
    }

    public Users getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(Users usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    public Collection<Orderline> getOrderlinesById() {
        return orderlinesById;
    }

    public void setOrderlinesById(Collection<Orderline> orderlinesById) {
        this.orderlinesById = orderlinesById;
    }
}

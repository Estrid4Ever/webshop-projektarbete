package com.webshop.webshopprojektarbete.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Products {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic
    @Column(name = "color", nullable = false, length = 45)
    private String color;
    @Basic
    @Column(name = "size", nullable = false)
    private int size;
    @Basic
    @Column(name = "brand", nullable = false, length = 45)
    private String brand;
    @Basic
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "image_path")
    private String imagePath;
    @OneToMany(mappedBy = "productsByProductId")
    private Collection<Orderline> orderlinesById;

    public Products(String name, String color, int size, String brand, int price, String imagePath) {
        this.name = name;
        this.color = color;
        this.size = size;
        this.brand = brand;
        this.price = price;
        this.imagePath = imagePath;
    }

    public Products() {
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return "Color: " + getColor() + "\nBrand: " + getBrand();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return id == products.id && size == products.size && price == products.price && Objects.equals(name, products.name) && Objects.equals(color, products.color) && Objects.equals(brand, products.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, size, brand, price);
    }

    public Collection<Orderline> getOrderlinesById() {
        return orderlinesById;
    }

    public void setOrderlinesById(Collection<Orderline> orderlinesById) {
        this.orderlinesById = orderlinesById;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}

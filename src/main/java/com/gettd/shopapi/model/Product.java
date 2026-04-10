package com.gettd.shopapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data //from lombok, generates getters,setters,constructors,toString(),etc.
@Entity //key annotation, tell Hibernate this class represents a table
@Table(name = "products") //tell Hibernate what to name table in MySQL (default will be "product", we define it to be "products")
public class Product {
    @Id //mark this row (int id) as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tell MySQL to auto increment the id
    private int id;

    @Column(nullable = false) //NOT NULL in SQL (cannot save a product without name (and price and stock)
    private String name;

    @Column(length = 500) //max string length
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;
}

/*
Product.java with @Entity
        ↓
Spring Boot started and scanned for @Entity classes
        ↓
Hibernate translated it into SQL:
  CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price DOUBLE NOT NULL,
    stock INT NOT NULL
  )
        ↓
Table now exists in MySQL
 */

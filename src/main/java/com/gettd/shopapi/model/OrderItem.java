package com.gettd.shopapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //many order items can belong to one order
    @JsonBackReference //the "child" side, gets excluded from JSON output to break the loop
    @JoinColumn(name = "order_id", nullable = false) //foreign key
    private Order order;

    @ManyToOne //many order items can belong to one type of product
    @JoinColumn(name = "product_id", nullable = false) //foreign key (this is to check stock)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double priceAtPurchase;
}

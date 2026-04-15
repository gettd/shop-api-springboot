package com.gettd.shopapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //many orders can belong to one user
    @JoinColumn(name = "user_id", nullable = false) //foreign key that links order table with user table
    private User user;

    //OneToMany -> one order has many order items
    //mappedBy order -> means that order field in OrderItem is the owner of the relationship (OrderItem holds foreign key that links to Order)
    //cascade CascateType.all -> when save/delete Order, OrderItems are saved/deleted too
    @JsonManagedReference // the "parent" side, gets serialized normally
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist //runs when entity is saved for the first time to set the timestamp and default status
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = "PENDING";
    }
}

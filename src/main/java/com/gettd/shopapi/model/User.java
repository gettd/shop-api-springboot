package com.gettd.shopapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true) //unique means no two user can share the same value
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; //the role will be either "USER" or "ADMIN" in our case
}

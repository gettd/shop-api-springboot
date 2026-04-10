package com.gettd.shopapi.repository;

import com.gettd.shopapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

//JpaRepository already contains all the basic database operations. We're just inheriting them.

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{ //works with Product entity, with primary key type Long ('private Long id' in entity)

    //we can add custom queries just by following a naming convention, Spring reads the method name and figures out the SQL automatically:

    // Spring reads method names and generates:
    // SELECT * FROM products WHERE name = ?
    List<Product> findByName(String name);

    // SELECT * FROM products WHERE price <= ?
    List<Product> findByPriceLessThanEqual(Double price);

    // SELECT * FROM products WHERE name LIKE %keyword%
    List<Product> findByNameContaining(String keyword);

    // SELECT * FROM products WHERE price BETWEEN ? AND ?
    List<Product> findByPriceBetween(Double min, Double max);

    //Spring generates the query purely from the method name. This is called 'derived queries'
}

/*
default:
Method              What it does
==============================================
findAll()           get all products
findById(id)        get one product by id
save(product)       create or update product
deleteById(id)      delete one product by id
count()             count total products
existsById(id)      check if product exists

 */
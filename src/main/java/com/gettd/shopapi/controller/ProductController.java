package com.gettd.shopapi.controller;

import com.gettd.shopapi.model.Product;
import com.gettd.shopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //mark as controller and convert returned java object to JSON
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //GET -> fetch data
    // GET /api/products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // 200 OK — success
    }

    // GET /api/products/1
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) { //PathVariable extracts value from the url
        return productService.getProductById(id)
                .map(ResponseEntity::ok)                    // 200 OK — success
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found — resource doesn't exist
    }

    //POST -> create new
    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) { //RequestBody extracts JSON body from the request and convert to java object
        Product created = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201 Created — something was created
    }

    //PUT-> update existing
    // PUT /api/products/1
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updated = productService.updateProduct(id, product);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE -> delete something
    // DELETE /api/products/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();  // 204 No Content — success but nothing to return
    }
}

// The HTTP status codes returned from ResponseEntity is important because they tell the caller(frontend) what happened

/*
Postman
  ↕ HTTP (JSON)
ProductController   ← receives requests, sends responses
  ↕ Java objects
ProductService      ← handles business logic
  ↕ Java objects
ProductRepository   ← talks to database
  ↕ SQL
MySQ
 */
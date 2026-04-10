package com.gettd.shopapi.service;

import com.gettd.shopapi.model.Product;
import com.gettd.shopapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired //dependency injection (we don't have to manually create ProductRepository object)
    private ProductRepository productRepository;
    //same as: ProductRepository productRepository = new ProductRepository();

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get single product by id
    public Optional<Product> getProductById(Long id) { //optional can handle case where product dont exists (instead of returning null, it returns empty Optional)
        return productRepository.findById(id);
    }

    // Create a new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Update existing product
    //fetch before saving to avoid overwriting things I didn't mean to
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found")); //if product doesn't exist, throw error immediately

        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStock(updatedProduct.getStock());

        return productRepository.save(existing);
    }

    // Delete a product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

/*
Postman sends GET /api/products
        ↓
Controller receives the request
        ↓
Controller calls productService.getAllProducts()
        ↓
Service calls productRepository.findAll()
        ↓
Repository runs SELECT * FROM products in MySQL
        ↓
Results travel back up as Java objects
        ↓
Controller sends them back as JSON
        ↓
Postman receives [{id: 1, name: "Laptop"...}]
 */

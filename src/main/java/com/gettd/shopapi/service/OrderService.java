package com.gettd.shopapi.service;

import com.gettd.shopapi.repository.*;
import com.gettd.shopapi.model.*;
import com.gettd.shopapi.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Order placeOrder(String userEmail, OrderRequest request){
        // 1. Find the user placing the order
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Build the order
        Order order = new Order();
        order.setUser(user);

        // 3. Build each order item
        List<OrderItem> items = request.getItems().stream().map(itemRequest -> { //stream is similar to a loop, it is just a cleaner way

            // Find the product
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));

            // Check stock
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }

            // Deduct stock
            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            // Create order item
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPriceAtPurchase(product.getPrice());

            return item;

        }).collect(Collectors.toList());

        // 4. Calculate total
        Double total = items.stream()
                .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                .sum();

        order.setItems(items);
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    // Get all orders for a specific user
    public List<Order> getMyOrders(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }

    // Get all orders (admin use)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}

/*
1. Find the user by email
2. Create a new Order object
3. For each item in the request:
   → Find the product in the database
   → Check there's enough stock
   → Deduct the quantity from stock
   → Lock in the price at time of purchase
4. Calculate the total price
5. Save everything to the database
 */
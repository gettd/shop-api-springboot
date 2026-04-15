package com.gettd.shopapi.controller;

import com.gettd.shopapi.dto.OrderRequest;
import com.gettd.shopapi.model.Order;
import com.gettd.shopapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //placing order
    //NOTE: Now passing email as header manually(normally extract from jwt)
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestHeader("X-User-Email") String userEmail, @RequestBody OrderRequest request){
        Order order = orderService.placeOrder(userEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // Get my orders
    @GetMapping("/me")
    public ResponseEntity<List<Order>> getMyOrders(@RequestHeader("X-User-Email") String userEmail) {
        return ResponseEntity.ok(orderService.getMyOrders(userEmail));
    }

    // Get all orders (admin)
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}

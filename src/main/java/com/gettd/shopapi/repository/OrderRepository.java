package com.gettd.shopapi.repository;

import com.gettd.shopapi.model.Order;
import com.gettd.shopapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);  // get all orders belonging to a specific user
}

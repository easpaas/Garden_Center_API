package io.catalyte.training.services;

import org.bson.types.ObjectId;
import io.catalyte.training.domains.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

  List<Order> findOrders(Order order);
  ResponseEntity<Order> getOrderById(ObjectId id);
  ResponseEntity<Order> addOrder(Order order);
  ResponseEntity<Order> updateOrder(ObjectId id, Order newOrder);
  void deleteOrder(ObjectId id);
}

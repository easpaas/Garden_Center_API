package io.catalyte.training.controllers;

import io.catalyte.training.domains.Order;
import io.catalyte.training.domains.User;
import io.catalyte.training.services.OrderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  OrderService orderService;

  /**
   * Maps a GET request to service layer. Retrieve all orders.
   * @return a list of all orders.
   */
  @GetMapping
  public List<Order> findOrders(Order order){
    return orderService.findOrders(order);
  }

  /**
   * Maps a GET request to service layer. Retrieve order by id.
   * @param id the id of order to be fetched.
   * @return the order with the given id.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable ObjectId id) {
    return orderService.getOrderById(id);
  }

  /**
   * Maps a POST request to service layer. Validates request body and adds a new order.
   * @param order the order containing all required fields.
   * @return order which was saved.
   */
  @PostMapping
  public ResponseEntity<Order> addOrder(@Valid @RequestBody Order order){
    return orderService.addOrder(order);
  }

  /**
   * Maps a PUT request to service layer. Validates request body and updates
   * old order with new order information.
   * @param id of order to update.
   * @param order the new order to save.
   * @return the updated order.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Order> updateOrder(@PathVariable ObjectId id, @Valid @RequestBody Order order){
    return orderService.updateOrder(id, order);
  }

  /**
   * Maps a DELETE request to service layer. Deletes order by id.
   * @param id the id of the order to be deleted.
   * @return a response indicating success with no content.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Order> deleteOrder(@PathVariable ObjectId id) {
    orderService.deleteOrder(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}

package io.catalyte.training.services;

import io.catalyte.training.domains.Order;
import io.catalyte.training.exceptions.ServerError;
import io.catalyte.training.repositories.OrderRepo;
import io.catalyte.training.repositories.QuerySearchRepo;
import org.bson.types.ObjectId;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.catalyte.training.constants.StringConstants.SERVER_ERROR_MESSAGE;

@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepo orderRepo;
  private QuerySearchRepo querySearchRepo;

  public OrderServiceImpl(OrderRepo orderRepo, QuerySearchRepo querySearchRepo) {
    this.orderRepo = orderRepo;
    this.querySearchRepo = querySearchRepo;
  }


  /**
   * Retrieve order by query parameters if order object has fields. Otherwise,
   * retrieve all orders.
   * @param order the order object to run a query on.
   * @return a list of orders that match given query.
   */
  @Override
  public List<Order> findOrders(Order order) {
    try {
      if (order.hasNoFields()) {
        return orderRepo.findAll();
      } else {
        return querySearchRepo.searchOrders(order);
      }
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Adds given order to database.
   * @param order the new order to be added.
   * @return the newly created order object.
   */
  @Override
  public ResponseEntity<Order> addOrder(Order order) {
    try {
      return new ResponseEntity<>(orderRepo.save(order), HttpStatus.CREATED);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Retrieve order with given Id.
   * @param id the id of order to be fetched.
   * @return order
   */
  @Override
  public ResponseEntity<Order> getOrderById(ObjectId id) {
    try {
      if (!orderRepo.existsById(id.toString())) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(orderRepo.findById(id), HttpStatus.OK);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Updates an existing order in the database with the given order. If there is no order with
   * the same id, it will return a 404.
   * @param id the id to match with an existing order.
   * @param newOrder new order to be updated.
   * @return the updated order in the database.
   */
  @Override
  public ResponseEntity<Order> updateOrder(ObjectId id, Order newOrder) {
    try {
      if (!orderRepo.existsById(id.toString())) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      newOrder.setId(id.toString());
      return new ResponseEntity<>(orderRepo.save(newOrder), HttpStatus.OK);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Deletes order with given id.
   * @param id the id of order to be deleted.
   */
  @Override
  public void deleteOrder(ObjectId id) {
    try {
      orderRepo.deleteById(id.toString());
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }
}

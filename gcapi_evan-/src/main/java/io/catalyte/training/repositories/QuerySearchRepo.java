package io.catalyte.training.repositories;

import io.catalyte.training.domains.Customer;
import io.catalyte.training.domains.Order;
import io.catalyte.training.domains.Product;
import io.catalyte.training.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuerySearchRepo {

  @Autowired
  private final MongoTemplate mongoTemplate;

  public QuerySearchRepo (MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  /**
   * GET request mapping from findUsers() to search all user fields.
   * @param user as object to perform query on.
   * @return a list of matching users.
   */
  public List<User> searchUsers(User user){
      return mongoTemplate.find(Query.query(new Criteria()
            .orOperator(Criteria.where("name").is(user.getName()),
                    Criteria.where("title").is(user.getTitle()),
                    Criteria.where("roles").in(user.getRoles()),
                    Criteria.where("email").is(user.getEmail()),
                    Criteria.where("password").is(user.getPassword()))
            ), User.class);
  }

  /**
   * GET request mapping from findCustomers() to search all customer fields.
   * @param customer as object to perform query on.
   * @return a list of customers.
   */
  public List<Customer> searchCustomers(Customer customer) {
    return mongoTemplate.find(Query.query(new Criteria()
            .orOperator(Criteria.where("name").is(customer.getName()),
                    Criteria.where("email").is(customer.getEmail()),
                    Criteria.where("address").is(customer.getAddress()))
    ), Customer.class);
  }

  /**
   * GET request mapping from findProducts() to search all product fields.
   * @param product as object to perform query on.
   * @return a list of products.
   */
  public List<Product> searchProducts(Product product) {
    return mongoTemplate.find(Query.query(new Criteria()
            .orOperator(Criteria.where("sku").is(product.getSku()),
                    Criteria.where("type").is(product.getType()),
                    Criteria.where("name").is(product.getName()),
                    Criteria.where("description").is(product.getDescription()),
                    Criteria.where("manufacturer").is(product.getManufacturer()),
                    Criteria.where("price").is(product.getPrice()))
    ), Product.class);
  }

  /**
   * GET request mapping from findOrders() to search all order fields.
   * @param order as object to perform query on.
   * @return a list of orders.
   */
  public List<Order> searchOrders(Order order) {
    return mongoTemplate.find(Query.query(new Criteria()
            .orOperator(Criteria.where("customerId").is(order.getCustomerId()),
                    Criteria.where("date").is(order.getDate()),
                    Criteria.where("item").is(order.getItems()),
                    Criteria.where("orderTotal").is(order.getOrderTotal()))
    ), Order.class);
  }
}

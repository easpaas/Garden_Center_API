package io.catalyte.training.services;

import io.catalyte.training.domains.Customer;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

  List<Customer> findCustomers(Customer customer);
  ResponseEntity<Customer> getCustomerById(ObjectId id);
  ResponseEntity<Customer> addCustomer(Customer customer);
  ResponseEntity<Customer> updateCustomer(ObjectId id, Customer newCustomer);
  void deleteCustomer(ObjectId id);

}

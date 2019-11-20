package io.catalyte.training.controllers;

import io.catalyte.training.domains.Customer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import io.catalyte.training.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  /**
   * Maps a GET request to service layer. Retrieve all customers.
   * @return a list of all customers.
   */
  @GetMapping
  public List<Customer> findCustomers(Customer customer){
    return customerService.findCustomers(customer);
  }

  /**
   * Maps a GET request to service layer. Retrieve customer by id.
   * @param id the id of customer to be fetched.
   * @return the customer with the given id.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomerById(@PathVariable ObjectId id) {
    return customerService.getCustomerById(id);
  }

  /**
   * Maps a POST request to service layer. Validates request body and adds a new customer.
   * @param customer the customer containing all required fields.
   * @return customer which was saved.
   */
  @PostMapping
  public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer){
    return customerService.addCustomer(customer);
  }

  /**
   * Maps a PUT request to service layer. Validates request body and updates
   * old customer with new customer information.
   * @param id of customer to update.
   * @param customer the new customer to save.
   * @return the updated customer.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Customer> updateCustomer(@PathVariable ObjectId id, @Valid @RequestBody Customer customer){
    return customerService.updateCustomer(id, customer);
  }

  /**
   * Maps a DELETE request to service layer. Deletes customer by id.
   * @param id the id of the customer to be deleted.
   * @return a response indicating success with no content.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCustomer(@PathVariable ObjectId id) {
    customerService.deleteCustomer(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

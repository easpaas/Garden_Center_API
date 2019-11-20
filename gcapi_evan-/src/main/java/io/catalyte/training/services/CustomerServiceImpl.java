package io.catalyte.training.services;

import io.catalyte.training.domains.Customer;
import io.catalyte.training.exceptions.ServerError;
import io.catalyte.training.repositories.CustomerRepo;
import io.catalyte.training.repositories.QuerySearchRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.catalyte.training.constants.StringConstants.SERVER_ERROR_MESSAGE;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepo customerRepo;
  private QuerySearchRepo querySearchRepo;

  @Autowired
  public CustomerServiceImpl(CustomerRepo customerRepo, QuerySearchRepo querySearchRepo) {
    this.customerRepo = customerRepo;
    this.querySearchRepo = querySearchRepo;
  }


  /**
   * Retrieve customer by query parameters if customer object has fields. Otherwise,
   * retrieve all customers.
   * @param customer the customer object to run a query on.
   * @return a list of customers that match given query.
   */
  @Override
  public List<Customer> findCustomers(Customer customer) {
    try {
      if (customer.hasNoFields()) {
        return customerRepo.findAll();
      } else {
        return querySearchRepo.searchCustomers(customer);
      }
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Adds given customer to database without duplicate email.
   * @param customer the new customer to be added.
   * @return the newly created customer object.
   */
  @Override
  public ResponseEntity<Customer> addCustomer(Customer customer) {
    try {
      //Duplicate email will return a 409.
      if (customerRepo.existsByEmail(customer.getEmail())) {
        return new ResponseEntity(HttpStatus.CONFLICT);
      }

      //Address with null fields will return a 400
      if (customer.getAddress().hasNoFields()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

      return new ResponseEntity<>(customerRepo.save(customer), HttpStatus.CREATED);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Retrieve customer with given Id.
   * @param id the id of customer to be fetched.
   * @return customer
   */
  @Override
  public ResponseEntity<Customer> getCustomerById(ObjectId id) {
    try {
      if (!customerRepo.existsById(id.toString())) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(customerRepo.findById(id), HttpStatus.OK);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Updates an existing customer in the database without duplicate email.
   * @param id the id to match with an existing user.
   * @param newCustomer new customer to be updated.
   * @return the updated customer in the database.
   */
  @Override
  public ResponseEntity<Customer> updateCustomer(ObjectId id, Customer newCustomer) {
    try {
      if (!customerRepo.existsById(id.toString())) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      newCustomer.setId(id.toString());


      return new ResponseEntity<>(customerRepo.save(newCustomer), HttpStatus.OK);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Deletes customer with given id.
   * @param id the id of customer to be deleted.
   */
  @Override
  public void deleteCustomer(ObjectId id) {
    try {
      customerRepo.deleteById(id.toString());
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }
}

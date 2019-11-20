package io.catalyte.training.services;

import io.catalyte.training.domains.Address;
import io.catalyte.training.domains.Customer;
import io.catalyte.training.exceptions.ServerError;
import io.catalyte.training.repositories.CustomerRepo;
import io.catalyte.training.repositories.QuerySearchRepo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {
  @Mock
  Customer mockCustomer;

  @Mock
  Address mockAddress;

  @Mock
  CustomerRepo mockCustomerRepo;

  @Mock
  QuerySearchRepo mockQuerySearchRepo;

  @InjectMocks
  CustomerServiceImpl customerService;

  private static List<Customer> customerList = new ArrayList<>();

  @BeforeClass
  public static void beforeClass(){
    Customer testCustomerOne = new Customer();
    Customer testCustomerTwo = new Customer();

    ObjectId idOne = new ObjectId();
    ObjectId idTwo = new ObjectId();

    testCustomerOne.setId(idOne.toString());
    testCustomerTwo.setId(idTwo.toString());

    customerList.add(testCustomerOne);
    customerList.add(testCustomerTwo);
  }

  @Before
  public void before() {

    //findCustomers method
    when(mockCustomer.hasNoFields()).thenReturn(true);
    when(mockCustomerRepo.findAll()).thenReturn(customerList);
    when(mockQuerySearchRepo.searchCustomers(mockCustomer)).thenReturn(customerList);

    //addCustomer method
    when(mockCustomer.getEmail()).thenReturn("Email");
    when(mockCustomerRepo.existsByEmail(any(String.class))).thenReturn(false);

    //getCustomerById method
    when(mockCustomerRepo.findById(any(ObjectId.class))).thenReturn(mockCustomer);

    //updateCustomer method
    when(mockCustomerRepo.existsById(any(String.class))).thenReturn(false);
  }


  @Test
  public void getAllCustomersHappyPath() {

    assertEquals(customerList, customerService.findCustomers(mockCustomer));
  }

  @Test
  public void getCustomersByQueryHappyPath() {

    when(mockCustomer.hasNoFields()).thenReturn(false);
    assertEquals(customerList, customerService.findCustomers(mockCustomer));
  }

  @Test
  public void addCustomerHappyPath() {
    Customer testCustomer = new Customer();
    testCustomer.setEmail("Email");

    assertEquals(new ResponseEntity(HttpStatus.CREATED), customerService.addCustomer(testCustomer));
  }

  @Test
  public void updateCustomerHappyPath()  {
    ObjectId id = new ObjectId();
    Customer testCustomer = new Customer();

    testCustomer.setEmail("Email");

    when(mockCustomerRepo.existsById(any(String.class))).thenReturn(true);

    assertEquals(new ResponseEntity(HttpStatus.OK), customerService.updateCustomer(id, testCustomer));
  }

  @Test
  public void deleteCustomerHappyPath() {

    doNothing().when(mockCustomerRepo).deleteById(isA(String.class));
    customerService.deleteCustomer(new ObjectId());
  }

  @Test
  public void existByEmailCreatesConflict() {
    ObjectId id = new ObjectId();

    //addCustomer method
    when(mockCustomerRepo.existsByEmail(any(String.class))).thenReturn(true);
    assertEquals(new ResponseEntity(HttpStatus.CONFLICT), customerService.addCustomer(mockCustomer));

    //updateCustomer method
    when(mockCustomerRepo.existsById(any(String.class))).thenReturn(true);
    assertEquals(new ResponseEntity(HttpStatus.CONFLICT), customerService.updateCustomer(id, mockCustomer));
  }

  @Test
  public void getCustomerById() {

    assertEquals(mockCustomer, customerService.getCustomerById(new ObjectId()));
  }

  @Test
  public void existsByIdNotFound() {
    ObjectId id = new ObjectId();

    assertEquals(new ResponseEntity(HttpStatus.NOT_FOUND), customerService.updateCustomer(id, mockCustomer));
  }

  @Test(expected = ServerError.class)
  public void updateCustomerThrowsException() {

    when(mockCustomerRepo.existsById(any()))
            .thenThrow(DataAccessResourceFailureException.class);

    customerService.updateCustomer(new ObjectId(), mockCustomer);
  }

  @Test(expected = ServerError.class)
  public void deleteCustomerThrowsException() {

    doThrow(new DataAccessResourceFailureException("")).when(mockCustomerRepo).deleteById(isA(String.class));
    customerService.deleteCustomer(new ObjectId());

  }
}
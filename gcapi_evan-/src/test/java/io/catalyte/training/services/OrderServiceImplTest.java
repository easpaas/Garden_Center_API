package io.catalyte.training.services;

import io.catalyte.training.domains.Order;
import io.catalyte.training.exceptions.ServerError;
import io.catalyte.training.repositories.OrderRepo;
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
public class OrderServiceImplTest {
  @Mock
  Order mockOrder;

  @Mock
  OrderRepo mockOrderRepo;

  @Mock
  QuerySearchRepo mockQuerySearchRepo;

  @InjectMocks
  OrderServiceImpl orderService;

  private static List<Order> orderList = new ArrayList<>();


  @BeforeClass
  public static void beforeClass(){
    Order testOrderOne = new Order();
    Order testOrderTwo = new Order();

    ObjectId idOne = new ObjectId();
    ObjectId idTwo = new ObjectId();

    testOrderOne.setId(idOne.toString());
    testOrderTwo.setId(idTwo.toString());

    orderList.add(testOrderOne);
    orderList.add(testOrderTwo);
  }

  @Before
  public void before() {

    //findProducts method
    when(mockOrder.hasNoFields()).thenReturn(true);
    when(mockOrderRepo.findAll()).thenReturn(orderList);
    when(mockQuerySearchRepo.searchOrders(mockOrder)).thenReturn(orderList);


    //addProducts method
//    when(mockOrder.getSku()).thenReturn("");
//    when(mockOrderRepo.existsBySku(any(String.class))).thenReturn(false);

    //getProductById method
    when(mockOrderRepo.findById(any(ObjectId.class))).thenReturn(mockOrder);

    //updateProduct method
    when(mockOrderRepo.existsById(any(String.class))).thenReturn(false);
  }

  @Test
  public void getAllOrdersHappyPath() {

    assertEquals(orderList, orderService.findOrders(mockOrder));
  }

  @Test
  public void getOrdersByQueryHappyPath() {

    when(mockOrder.hasNoFields()).thenReturn(false);
    assertEquals(orderList, orderService.findOrders(mockOrder));
  }

  @Test
  public void addOrderHappyPath() {
    Order testOrder = new Order();

    assertEquals(new ResponseEntity(HttpStatus.CREATED), orderService.addOrder(testOrder));
  }

  @Test
  public void updateOrderHappyPath() {
    ObjectId id = new ObjectId();
    Order testOrder = new Order();


    when(mockOrderRepo.existsById(any(String.class))).thenReturn(true);

    assertEquals(new ResponseEntity(HttpStatus.OK), orderService.updateOrder(id, testOrder));
  }

  @Test
  public void deleteUserHappyPath() {

    doNothing().when(mockOrderRepo).deleteById(isA(String.class));
    orderService.deleteOrder(new ObjectId());
  }

  @Test
  public void getOrderById() {

    assertEquals(mockOrder, orderService.getOrderById(new ObjectId()));
  }

  @Test
  public void existsByIdNotFound() {
    ObjectId id = new ObjectId();

    assertEquals(new ResponseEntity(HttpStatus.NOT_FOUND), orderService.updateOrder(id, mockOrder));
  }

  @Test(expected = ServerError.class)
  public void updateOrderThrowsException() {

    when(mockOrderRepo.existsById(any()))
            .thenThrow(DataAccessResourceFailureException.class);

    orderService.updateOrder(new ObjectId(), mockOrder);
  }

  @Test(expected = ServerError.class)
  public void deleteOrderThrowsException() {

    doThrow(new DataAccessResourceFailureException("")).when(mockOrderRepo).deleteById(isA(String.class));
    orderService.deleteOrder(new ObjectId());

  }
}
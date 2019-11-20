package io.catalyte.training.services;

import io.catalyte.training.domains.Product;
import io.catalyte.training.exceptions.ServerError;
import io.catalyte.training.repositories.ProductRepo;
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
public class ProductServiceImplTest {

  @Mock
  Product mockProduct;

  @Mock
  ProductRepo mockProductRepo;

  @Mock
  QuerySearchRepo mockQuerySearchRepo;

  @InjectMocks
  ProductServiceImpl productService;

  private static List<Product> productList = new ArrayList<>();


  @BeforeClass
  public static void beforeClass(){
    Product testProductOne = new Product();
    Product testProductTwo = new Product();

    ObjectId idOne = new ObjectId();
    ObjectId idTwo = new ObjectId();

    testProductOne.setId(idOne.toString());
    testProductTwo.setId(idTwo.toString());

    productList.add(testProductOne);
    productList.add(testProductTwo);
  }

  @Before
  public void before() {

    //findProducts method
    when(mockProduct.hasNoFields()).thenReturn(true);
    when(mockProductRepo.findAll()).thenReturn(productList);
    when(mockQuerySearchRepo.searchProducts(mockProduct)).thenReturn(productList);


    //addProducts method
    when(mockProduct.getSku()).thenReturn("");
    when(mockProductRepo.existsBySku(any(String.class))).thenReturn(false);

    //getProductById method
    when(mockProductRepo.findById(any(ObjectId.class))).thenReturn(mockProduct);

    //updateProduct method
    when(mockProductRepo.existsById(any(String.class))).thenReturn(false);
  }

  @Test
  public void getAllProductsHappyPath() {

    assertEquals(productList, productService.findProducts(mockProduct));

  }

  @Test
  public void getProductsByQueryHappyPath() {

    when(mockProduct.hasNoFields()).thenReturn(false);
    assertEquals(productList, productService.findProducts(mockProduct));
  }

  @Test
  public void addProductHappyPath() {
    Product testProduct = new Product();

    testProduct.setSku("");
    productList.add(mockProduct);

    assertEquals(new ResponseEntity(HttpStatus.CREATED), productService.addProduct(testProduct));
  }

  @Test
  public void updateProductHappyPath() {
    ObjectId id = new ObjectId();
    Product testProduct = new Product();
    testProduct.setSku("");

    when(mockProductRepo.existsById(any(String.class))).thenReturn(true);

    assertEquals(new ResponseEntity(HttpStatus.OK), productService.updateProduct(id, testProduct));
  }

  @Test
  public void deleteProductHappyPath() {

    doNothing().when(mockProductRepo).deleteById(isA(String.class));
    productService.deleteProduct(new ObjectId());

  }

  @Test
  public void existBySkuCreatesConflict() {
    ObjectId id = new ObjectId();

    //addProduct method
    when(mockProductRepo.existsBySku(any(String.class))).thenReturn(true);
    assertEquals(new ResponseEntity(HttpStatus.CONFLICT), productService.addProduct(mockProduct));

    //updateProduct method
    when(mockProductRepo.existsById(any(String.class))).thenReturn(true);
    assertEquals(new ResponseEntity(HttpStatus.CONFLICT), productService.updateProduct(id, mockProduct));
  }

  @Test
  public void existsByIdNotFound() {
    ObjectId id = new ObjectId();

    assertEquals(new ResponseEntity(HttpStatus.NOT_FOUND), productService.updateProduct(id, mockProduct));
  }

  @Test
  public void getProductById() {

    assertEquals(mockProduct, productService.getProductById(new ObjectId()));
  }

  @Test(expected = ServerError.class)
  public void updateProductThrowsException() {

    when(mockProductRepo.existsById(any()))
            .thenThrow(DataAccessResourceFailureException.class);

    productService.updateProduct(new ObjectId(), mockProduct);
  }

  @Test(expected = ServerError.class)
  public void deleteProductThrowsException() {

    doThrow(new DataAccessResourceFailureException("")).when(mockProductRepo).deleteById(isA(String.class));
    productService.deleteProduct(new ObjectId());

  }

}
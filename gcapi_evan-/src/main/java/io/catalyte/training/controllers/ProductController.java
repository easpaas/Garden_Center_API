package io.catalyte.training.controllers;

import io.catalyte.training.domains.Product;
import io.catalyte.training.services.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductService productService;


  /**
   * Maps a GET request to service layer. Retrieve all products.
   * @return a list of all products.
   */
  @GetMapping
  public List<Product> findProducts(Product product){
    return productService.findProducts(product);
  }

  /**
   * Maps a GET request to service layer. Retrieve product by id.
   * @param id the id of product to be fetched.
   * @return the product with the given id.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable ObjectId id) {
    return productService.getProductById(id);
  }

  /**
   * Maps a POST request to service layer. Validates request body and adds a new product.
   * @param product the product containing all required fields.
   * @return product which was saved.
   */
  @PostMapping
  public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
    return productService.addProduct(product);
  }

  /**
   * Maps a PUT request to service layer. Validates request body and updates
   * old product with new product information.
   * @param id of product to update.
   * @param product the new product to save.
   * @return the updated product.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable ObjectId id, @Valid @RequestBody Product product){
    return productService.updateProduct(id, product);
  }

  /**
   * Maps a DELETE request to service layer. Deletes product by id.
   * @param id the id of the product to be deleted.
   * @return a response indicating success with no content.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCustomer(@PathVariable ObjectId id) {
    productService.deleteProduct(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

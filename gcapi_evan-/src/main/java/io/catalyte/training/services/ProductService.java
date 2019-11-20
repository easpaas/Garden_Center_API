package io.catalyte.training.services;

import io.catalyte.training.domains.Product;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

  List<Product> findProducts(Product product);
  ResponseEntity<Product> getProductById(ObjectId id);
  ResponseEntity<Product> addProduct(Product product);
  ResponseEntity<Product> updateProduct(ObjectId id, Product newProduct);
  void deleteProduct(ObjectId id);

}

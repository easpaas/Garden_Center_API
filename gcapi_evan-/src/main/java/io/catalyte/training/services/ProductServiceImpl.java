package io.catalyte.training.services;

import io.catalyte.training.domains.Product;
import io.catalyte.training.exceptions.ServerError;
import io.catalyte.training.repositories.ProductRepo;
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
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepo productRepo;
  private QuerySearchRepo querySearchRepo;

  @Autowired
  public ProductServiceImpl(ProductRepo productRepo, QuerySearchRepo querySearchRepo) {
    this.productRepo = productRepo;
    this.querySearchRepo = querySearchRepo;
  }

  /**
   * Retrieve product by query parameters if product object has fields. Otherwise,
   * retrieve all products.
   * @param product the product object to run a query on.
   * @return a list of products that match given query.
   */
  @Override
  public List<Product> findProducts(Product product) {
    try {
      if (product.hasNoFields()) {
        return productRepo.findAll();
      } else {
        return querySearchRepo.searchProducts(product);
      }
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Adds given product to database without duplicate sku.
   * @param product the new product to be added.
   * @return the newly created product object.
   */
  @Override
  public ResponseEntity<Product> addProduct(Product product) {
    try {
      //Duplicate sku will return 409.
      if (productRepo.existsBySku(product.getSku())) {
        return new ResponseEntity(HttpStatus.CONFLICT);
      } else {
        return new ResponseEntity<>(productRepo.save(product), HttpStatus.CREATED);
      }
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Retrieve product with given Id.
   * @param id the id of product to be fetched.
   * @return product
   */
  @Override
  public ResponseEntity<Product> getProductById(ObjectId id) {
    try {
      //No product found will return a 404.
      if (!productRepo.existsById(id.toString())) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(productRepo.findById(id), HttpStatus.OK);
    } catch (DataAccessResourceFailureException e){
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Updates an existing product in the database without duplicate sku.
   * @param id the id to match with an existing product.
   * @param newProduct new product to be updated.
   * @return the updated product in the database.
   */
  @Override
  public ResponseEntity<Product> updateProduct(ObjectId id, Product newProduct) {
    try {
      //No product found will return a 404.
      if (!productRepo.existsById(id.toString())) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      newProduct.setId(id.toString());

      return new ResponseEntity<>(productRepo.save(newProduct), HttpStatus.OK);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Deletes product with given id.
   * @param id the id of product to be deleted.
   */
  @Override
  public void deleteProduct(ObjectId id) {
    try {
      productRepo.deleteById(id.toString());
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }
}

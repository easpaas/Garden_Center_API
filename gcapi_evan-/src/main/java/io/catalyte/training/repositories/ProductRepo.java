package io.catalyte.training.repositories;

import io.catalyte.training.domains.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {

  Product findById(ObjectId id);
  boolean existsBySku(String sku);
}

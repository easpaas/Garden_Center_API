package io.catalyte.training.repositories;

import io.catalyte.training.domains.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends MongoRepository<Customer, String> {

  Customer findById(ObjectId id);
  boolean existsByEmail(String email);
}

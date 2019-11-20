package io.catalyte.training.repositories;

import io.catalyte.training.domains.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends MongoRepository<Order, String> {

  Order findById(ObjectId id);
}

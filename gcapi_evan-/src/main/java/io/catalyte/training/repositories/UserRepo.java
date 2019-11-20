package io.catalyte.training.repositories;

import io.catalyte.training.domains.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {

  User findById(ObjectId id);
  boolean existsByEmail(String email);
  Optional<User> findByEmail(String username);
}

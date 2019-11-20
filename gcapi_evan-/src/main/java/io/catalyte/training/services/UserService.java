package io.catalyte.training.services;

import io.catalyte.training.domains.User;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

  List<User> findUsers(User user);
  ResponseEntity<User> getUserById(ObjectId id);
  ResponseEntity<User> addUser(User user);
  ResponseEntity<User> updateUser(ObjectId id, User newUser);
  void deleteUser(ObjectId id);
}

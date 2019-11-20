package io.catalyte.training.controllers;

import io.catalyte.training.domains.User;
import io.catalyte.training.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Maps a GET request to service layer. Retrieve all users.
   * @return a list of all users.
   */
  @GetMapping
  public List<User> findUsers(User user){
    return userService.findUsers(user);
  }

  /**
   * Maps a GET request to service layer. Retrieve user by id.
   * @param id the id of user to be fetched.
   * @return the user with the given id.
   */
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable ObjectId id) {
    return userService.getUserById(id);
  }

  /**
   * Maps a POST request to service layer. Validates request body and adds a new user.
   * @param user the user containing all required fields.
   * @return user which was saved.
   */
  @PostMapping
  public ResponseEntity<User> addUser(@Valid @RequestBody User user){
      return userService.addUser(user);
  }

  /**
   * Maps a PUT request to service layer. Validates request body and updates
   * old user with new user information.
   * @param id of user to update.
   * @param user the new user to save.
   * @return the updated user.
   */
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable ObjectId id, @Valid @RequestBody User user) {
    return userService.updateUser(id, user);
  }

  /**
   * Maps a DELETE request to service layer. Deletes user by id.
   * @param id the id of the user to be deleted.
   * @return a response indicating success with no content.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable ObjectId id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}

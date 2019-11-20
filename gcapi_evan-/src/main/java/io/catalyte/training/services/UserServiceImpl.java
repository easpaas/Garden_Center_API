package io.catalyte.training.services;

import io.catalyte.training.domains.User;
import io.catalyte.training.exceptions.ServerError;
import io.catalyte.training.repositories.QuerySearchRepo;
import io.catalyte.training.repositories.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.catalyte.training.constants.StringConstants.SERVER_ERROR_MESSAGE;

@Service
public class UserServiceImpl implements UserService {
  private UserRepo userRepo;
  private QuerySearchRepo querySearchRepo;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(UserRepo userRepo, QuerySearchRepo querySearchRepo) {
    this.userRepo = userRepo;
    this.querySearchRepo = querySearchRepo;
  }

  /**
   * Retrieve user by query parameters if user object has fields. Otherwise,
   * retrieve all users.
   * @param user the user object to run a query on.
   * @return a list of users that match given query.
   */
  @Override
  public List<User> findUsers(User user) {
   try {
     if (user.hasNoFields()) {
       return userRepo.findAll();
     } else {
       return querySearchRepo.searchUsers(user);
     }
   }catch (DataAccessResourceFailureException e) {
     throw new ServerError(SERVER_ERROR_MESSAGE);
   }
  }

  /**
   * Adds given user to database without duplicate email and
   * validates that role can only be Admin and/or Employee.
   * @param user the new user to be added.
   * @return the newly created User object.
   */
  @Override
  public ResponseEntity<User> addUser(User user) {
    try {
      //Duplicate email will return a 409.
      if (userRepo.existsByEmail(user.getEmail())) {
        return new ResponseEntity(HttpStatus.CONFLICT);
      }
      //Invalid role will return a 400.
      for (Object role : user.getRoles()) {
        if (!role.equals("Admin") && !role.equals("Employee")) {
          return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
      }
      //encode user password
      user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

      return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Retrieve user with given Id.
   * @param id the id of user to be fetched.
   * @return user
   */
  @Override
  public ResponseEntity<User> getUserById(ObjectId id) {
    try {
      //Invalid Id will return a 404.
      if (!userRepo.existsById(id.toString())) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(userRepo.findById(id), HttpStatus.OK);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Updates an existing user in the database without duplicate email
   * and validates that role of user can only be Admin and/or Employee.
   * @param id the id to match with an existing user.
   * @param newUser new user to be updated.
   * @return the updated user in the database.
   */
  @Override
  public ResponseEntity<User> updateUser(ObjectId id, User newUser) {
    try {
      //Invalid Id will return a 404.
      if (!userRepo.existsById(id.toString())) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
      newUser.setId(id.toString());
      //Invalid role will return a 400.
      for (Object role : newUser.getRoles()){
        if (!role.equals("Admin") && !role.equals("Employee")) {
          return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
      }
      //encode user password
      newUser.setPassword(this.bCryptPasswordEncoder.encode(newUser.getPassword()));
      return new ResponseEntity<>(userRepo.save(newUser), HttpStatus.OK);
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }

  /**
   * Deletes user with given id.
   * @param id the id of user to be deleted.
   */
  @Override
  public void deleteUser(ObjectId id) {
    try {
      userRepo.deleteById(id.toString());
    } catch (DataAccessResourceFailureException e) {
      throw new ServerError(SERVER_ERROR_MESSAGE);
    }
  }
}

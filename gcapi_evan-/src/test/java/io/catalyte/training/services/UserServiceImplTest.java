package io.catalyte.training.services;

import io.catalyte.training.domains.User;
import io.catalyte.training.exceptions.ServerError;
import io.catalyte.training.repositories.QuerySearchRepo;
import io.catalyte.training.repositories.UserRepo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  @Mock
  User mockUser;

  @Mock
  UserRepo mockUserRepo;

  @Mock
  QuerySearchRepo mockQuerySearchRepo;

  @InjectMocks
  UserServiceImpl userService;

  private static List<User> userList = new ArrayList<>();
  private static ArrayList rolesList = new ArrayList<>();

  @BeforeClass
  public static void beforeClass(){
    User testUserOne = new User();
    User testUserTwo = new User();

    ObjectId idOne = new ObjectId();
    ObjectId idTwo = new ObjectId();

    testUserOne.setId(idOne.toString());
    testUserTwo.setId(idTwo.toString());

    userList.add(testUserOne);
    userList.add(testUserTwo);
  }

  @Before
  public void before() {

    //findUsers method
    when(mockUser.hasNoFields()).thenReturn(true);
    when(mockUserRepo.findAll()).thenReturn(userList);
    when(mockQuerySearchRepo.searchUsers(mockUser)).thenReturn(userList);

    //addUsers method
    when(mockUser.getEmail()).thenReturn("Email");
    when(mockUserRepo.existsByEmail(any(String.class))).thenReturn(false);
    //when(mockUserRepo.save(mockUser)).thenReturn(mockUser);

    //getUserById method
    when(mockUserRepo.findById(any(ObjectId.class))).thenReturn(mockUser);

    //updateUser method
    when(mockUserRepo.existsById(any(String.class))).thenReturn(false);
  }

  @Test
  public void getAllUsersHappyPath() {

    assertEquals(userList, userService.findUsers(mockUser));
  }

  @Test
  public void getUsersByQueryHappyPath() {

    when(mockUser.hasNoFields()).thenReturn(false);
    assertEquals(userList, userService.findUsers(mockUser));
  }

  @Test
  public void addUserHappyPath() {
    User testUser = new User();

    testUser.setEmail("Email");
    rolesList.add("Employee");
    testUser.setRoles(rolesList);

    assertEquals(new ResponseEntity(HttpStatus.CREATED), userService.addUser(testUser));
  }

  @Test
  public void updateUserHappyPath()  {
    ObjectId id = new ObjectId();
    User testUser = new User();

    testUser.setEmail("Email");
    rolesList.add("Employee");
    testUser.setRoles(rolesList);

    when(mockUserRepo.existsById(any(String.class))).thenReturn(true);

    assertEquals(new ResponseEntity(HttpStatus.OK), userService.updateUser(id, testUser));
  }

  @Test
  public void deleteUserHappyPath() {

    doNothing().when(mockUserRepo).deleteById(isA(String.class));
    userService.deleteUser(new ObjectId());
  }

  @Test
  public void existByEmailCreatesConflict() {
    ObjectId id = new ObjectId();

    //addUser and updateUser methods
    when(mockUserRepo.existsByEmail(any(String.class))).thenReturn(true);
    assertEquals(new ResponseEntity(HttpStatus.CONFLICT), userService.addUser(mockUser));

    //updateUser method
    when(mockUserRepo.existsById(any(String.class))).thenReturn(true);
    assertEquals(new ResponseEntity(HttpStatus.CONFLICT), userService.updateUser(id, mockUser));
  }

  @Test
  public void invalidRoleCreatesBadRequest() {
    User testUser = new User();
    ObjectId id = new ObjectId();

    rolesList.add("invalidRole");
    testUser.setRoles(rolesList);

    //addUser method
    assertEquals(new ResponseEntity(HttpStatus.BAD_REQUEST), userService.addUser(testUser));

    //updateUser method
    when(mockUserRepo.existsById(any(String.class))).thenReturn(true);
    assertEquals(new ResponseEntity(HttpStatus.BAD_REQUEST), userService.updateUser(id, testUser));
  }

  @Test
  public void getUserById() {

    assertEquals(mockUser, userService.getUserById(new ObjectId()));
  }

  @Test
  public void existsByIdNotFound() {
    ObjectId id = new ObjectId();

    assertEquals(new ResponseEntity(HttpStatus.NOT_FOUND), userService.updateUser(id, mockUser));
  }

  @Test(expected = ServerError.class)
  public void updateUserThrowsException() {

    when(mockUserRepo.existsById(any()))
            .thenThrow(DataAccessResourceFailureException.class);

    userService.updateUser(new ObjectId(), mockUser);
  }

  @Test(expected = ServerError.class)
  public void deleteUserThrowsException() {

    doThrow(new DataAccessResourceFailureException("")).when(mockUserRepo).deleteById(isA(String.class));
    userService.deleteUser(new ObjectId());

  }
}
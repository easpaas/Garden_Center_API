package io.catalyte.training.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains all string constants used throughout the application.
 */
public class StringConstants {

  //General
  public static final String NOT_FOUND = "Not Found";
  public static final String BAD_REQUEST = "Bad Request";
  public static final String CONFLICT = "Conflict";
  public static final String SERVER_ERROR = "Server Error";
  public static final String SERVER_ERROR_MESSAGE = "An unexpected error occurred.";
  public static final String REQUIRED_FIELD = " is a required field.";
  public static final String INVALID_POSITIVE = " must be greater than 0.";

  //User Domain
  public static final String USER_COLLECTION = "users";
  public static final Set<String> USER_FIELDS = new HashSet<>();
  static {
    USER_FIELDS.add("name");
    USER_FIELDS.add("title");
    USER_FIELDS.add("roles");
    USER_FIELDS.add("email");
    USER_FIELDS.add("password");
  }
}

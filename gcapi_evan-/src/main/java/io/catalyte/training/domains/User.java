package io.catalyte.training.domains;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

@Document(collection = "user")
public class User {

  @Id
  private ObjectId id;

  @NotBlank(message = "Name" + REQUIRED_FIELD)
  private String name;
  @NotBlank(message = "Title" + REQUIRED_FIELD)
  private String title;
  @NotNull(message = "Roles" + REQUIRED_FIELD)
  private ArrayList<String> roles;
  @NotBlank(message = "Email" + REQUIRED_FIELD)
  @Email(message = "Email should be valid")
  private String email;
  @Size(min = 8, message = "Password must be a minimum of 8 characters")
  private String password;


  //Getters and Setters
  public String getId() {
    return id.toHexString();
  }
  public void setId(String id) {
    this.id = new ObjectId(id);
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public ArrayList<String> getRoles() {
    return roles;
  }
  public void setRoles(ArrayList<String> roles) {
    this.roles = roles;
  }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }



  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", title='" + title + '\'' +
            ", roles=" + roles +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
  }

  /**
   * Checks if object fields exist
   * @return true if object has null fields or false if object has non null fields.
   */
  public boolean hasNoFields() {
    return id == null && name == null && title == null && roles == null && email == null && password == null;
  }
}

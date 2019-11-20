package io.catalyte.training.domains;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

@Document(collection = "customer")
public class Customer {

  @Id
  private ObjectId id;

  @NotBlank(message = "Name" + REQUIRED_FIELD)
  private String name;
  @Email(message = "Email should be valid")
  private String email;
  @Valid
  @NotNull(message = "Address" + REQUIRED_FIELD)
  private Address address;


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

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public Address getAddress() {
    return address;
  }
  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "Customer{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", address=" + address +
            '}';
  }

  /**
   * Checks if object fields exist
   * @return true if object has null fields or false if object has non null fields.
   */
  public boolean hasNoFields() {
    return id == null && name == null && email == null && address == null;
  }
}

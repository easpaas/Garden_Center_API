package io.catalyte.training.domains;

import javax.validation.constraints.*;

import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

public class Address  {

  @NotBlank(message = "Street" + REQUIRED_FIELD)
  private String street;
  @NotBlank(message = "City" + REQUIRED_FIELD)
  private String city;
  @NotBlank(message = "State" + REQUIRED_FIELD)
  @Pattern(regexp = "^(?:A[LKSZREP]|C[AOT]|D[EC]|F[LM]|G[AU]|HI|I[ADLN]|K[SY]|LA|M[ADEHINOPST]|N[CDEHJMVY]|O[HKR]|P[ARW]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY])$")
  private String state;
  @NotBlank(message = "ZipCode" + REQUIRED_FIELD)
  @Size(min = 5, max = 10, message = "Valid Zip format must be 55555 or 55555-4444")
  @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$")
  private String zipCode;


  //Getters and Setters
  public String getStreet() {
    return street;
  }
  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  @Override
  public String toString() {
    return "Address{" +
            "street='" + street + '\'' +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", zipCode='" + zipCode + '\'' +
            '}';
  }

  /**
   * Checks if object fields exist
   * @return true if object has null fields or false if object has non null fields.
   */
  public boolean hasNoFields() {
    return street == null && city == null && state == null && zipCode == null;
  }

}

package io.catalyte.training.domains;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static io.catalyte.training.constants.StringConstants.INVALID_POSITIVE;
import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

@Document(collection = "product")
public class Product {

  @Id
  private ObjectId id;

  @NotBlank(message = "Sku" + REQUIRED_FIELD)
  private String sku;
  @NotBlank(message = "Type" + REQUIRED_FIELD)
  private String type;
  @NotBlank(message = "Name" + REQUIRED_FIELD)
  private String name;
  @NotBlank(message = "Description" + REQUIRED_FIELD)
  private String description;
  @NotBlank(message = "Manufacturer" + REQUIRED_FIELD)
  private String manufacturer;
  @Positive(message = "Price" + INVALID_POSITIVE)
  @NotNull
  private Double price;


  public String getId() {
    return id.toHexString();
  }
  public void setId(String id) {
    this.id = new ObjectId(id);
  }

  public String getSku() {
    return sku;
  }
  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public String getManufacturer() {
    return manufacturer;
  }
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public Double getPrice() {
    return price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", sku=" + sku +
            ", type='" + type + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", manufacturer='" + manufacturer + '\'' +
            ", price=" + price +
            '}';
  }

  /**
   * Checks if object fields exist
   * @return true if object has null fields or false if object has non null fields.
   */
  public boolean hasNoFields() {
    return id == null && sku == null && type == null && name == null && description == null && manufacturer == null && price == null;
  }

}


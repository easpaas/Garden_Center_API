package io.catalyte.training.domains;

import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static io.catalyte.training.constants.StringConstants.INVALID_POSITIVE;
import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

public class Item  {

  @NotNull(message = "ProductId" + REQUIRED_FIELD)
  private ObjectId productId;
  @Positive(message = "Quantity" + INVALID_POSITIVE)
  private Integer quantity;

  //Getters and Setters (grouped as Get/Set for each object)
  public String getProductId() {
    return productId.toHexString();
  }
  public void setProductId(String productId) {
    this.productId = new ObjectId(productId);
  }

  public Integer getQuantity() {
    return quantity;
  }
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "Item{" +
            "productId=" + productId +
            ", quantity='" + quantity + '\'' +
            '}';
  }
}

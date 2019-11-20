package io.catalyte.training.domains;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static io.catalyte.training.constants.StringConstants.INVALID_POSITIVE;
import static io.catalyte.training.constants.StringConstants.REQUIRED_FIELD;

@Document(collection = "order")
public class Order {

  @Id
  private ObjectId id;

  @NotNull(message = "CustomerId" + REQUIRED_FIELD)
  private ObjectId customerId;
  @NotBlank(message = "Date" + REQUIRED_FIELD)
  private String date;
  @NotNull(message = "Items" + REQUIRED_FIELD)
  @Valid
  private List<Item> items;
  @Positive(message = "OrderTotal" + INVALID_POSITIVE)
  @NotNull
  private Double orderTotal;

  public String getId() {
    return id.toHexString();
  }
  public void setId(String id) {
    this.id = new ObjectId(id);
  }

  public String getCustomerId() {
    return customerId.toHexString();
  }
  public void setCustomerId(String customerId) {
    this.customerId = new ObjectId(customerId);
  }

  public String getDate() {
    return date;
  }
  public void setDate(String date) {
    this.date = date;
  }

  public List<Item> getItems() {
    return items;
  }
  public void setItems(List<Item> items) {
    this.items = items;
  }

  public Double getOrderTotal() {
    return orderTotal;
  }
  public void setOrderTotal(Double orderTotal) {
    this.orderTotal = orderTotal;
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", customerId=" + customerId +
            ", date=" + date +
            ", items=" + items +
            ", orderTotal=" + orderTotal +
            '}';
  }

  /**
   * Checks if object fields exist
   * @return true if object has null fields or false if object has non null fields.
   */
  public boolean hasNoFields() {
    return id == null && customerId == null && date == null && items == null && orderTotal == null;
  }
}

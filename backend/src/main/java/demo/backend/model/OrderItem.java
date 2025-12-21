package demo.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
     @JsonProperty("itemid")
     private Integer itemId;

     @JsonProperty("itemname")
     private String itemName;

     @JsonProperty("quantity")
     private Integer quantity;

     @JsonProperty("subtotal")
     private Long subtotal;
}

package GroceryProject.OrderingProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemsResponse {

    private Long vegetableId;
    private String vegetableName;

    private int quantity;
    private Double pricePerUnit;
    private Double subTotal;
}

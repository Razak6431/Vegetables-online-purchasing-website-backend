package GroceryProject.OrderingProject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemRequest {
    @NotNull(message = "Vegetable ID is required")
    private Long vegetableId;

    @Positive(message = "Quantity must be greater than 0")
    private int quantity;


    private Double pricePerUnit;



}

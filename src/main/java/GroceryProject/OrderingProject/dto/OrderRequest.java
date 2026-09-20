package GroceryProject.OrderingProject.dto;

import GroceryProject.OrderingProject.Enum.OrderType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Order type is required")
    private OrderType orderType;

    @NotNull(message = "Order items are required")
    private List<OrderItemRequest>items;

    private Double amount;



}

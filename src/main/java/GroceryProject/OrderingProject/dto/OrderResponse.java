package GroceryProject.OrderingProject.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long orderId;
    private Double totalAmount;
    private String orderStatus;

    private String paymentStatus;

    private LocalDateTime createdAt;

    private List<OrderItemsResponse>items;

}

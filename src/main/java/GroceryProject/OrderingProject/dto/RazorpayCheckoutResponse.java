package GroceryProject.OrderingProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RazorpayCheckoutResponse {

    private Long orderId;
    private String key;             // Razorpay Key ID — safe for frontend
    private String razorpayOrderId; // Razorpay order ID
    private long amount;            // Amount in paise
    private String currency;
}
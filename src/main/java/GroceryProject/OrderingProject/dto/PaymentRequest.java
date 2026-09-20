package GroceryProject.OrderingProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentRequest {

    private Long orderId;
    private String razorpayPaymentId;

    private String razorpayOrderId;

    private String razorpaySignature;

}

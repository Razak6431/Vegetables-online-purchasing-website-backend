package GroceryProject.OrderingProject.controller;

import GroceryProject.OrderingProject.Entity.Order;
import GroceryProject.OrderingProject.Entity.Payment;
import GroceryProject.OrderingProject.Service.OrderService;
import GroceryProject.OrderingProject.Service.PaymentService;
import GroceryProject.OrderingProject.dto.RazorpayCheckoutResponse;
import com.razorpay.RazorpayException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class UserPaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;
    public UserPaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @PostMapping("/create/{orderId}")
    public ResponseEntity<Payment> createPayment(@PathVariable Long orderId) throws RazorpayException {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(paymentService.createPayment(order));
    }

    // Step 2: Verify payment after checkout
    @PostMapping("/verify/{orderId}")
    public ResponseEntity<Payment> verifyPayment(@PathVariable Long orderId,
                                                 @RequestParam String razorpayOrderId,
                                                 @RequestParam String razorpayPaymentId,
                                                 @RequestParam String razorpaySignature) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(paymentService.verifyAndSavePayment(
                razorpayOrderId, razorpayPaymentId, razorpaySignature, order));
    }

    // Step 3: Refund request (user can only refund their own successful orders)
    @DeleteMapping("/refund/{orderId}")
    public ResponseEntity<Payment> refundPayment(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        // Add security check: ensure logged-in user owns this order
        return ResponseEntity.ok(paymentService.refundPayment(order));
    }

    @GetMapping("/checkout/{orderId}")
    public ResponseEntity<RazorpayCheckoutResponse> getCheckoutDetails(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(paymentService.getCheckoutDetails(order));
    }


}

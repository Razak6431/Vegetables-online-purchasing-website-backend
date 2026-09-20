package GroceryProject.OrderingProject.controller;

import GroceryProject.OrderingProject.Entity.Order;
import GroceryProject.OrderingProject.Entity.Payment;
import GroceryProject.OrderingProject.Service.OrderService;
import GroceryProject.OrderingProject.Service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/payments")
public class AdminPaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    public AdminPaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    // Admin refund override
    @DeleteMapping("/refund/{orderId}")
    public ResponseEntity<Payment> refundPaymentAdmin(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(paymentService.refundPayment(order));
    }

    // Admin can view payment details for any order
    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrder(@PathVariable Long orderId) throws RazorpayException {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(paymentService.createPayment(order));
    }


}

package GroceryProject.OrderingProject.controller;

import GroceryProject.OrderingProject.Entity.Order;
import GroceryProject.OrderingProject.Service.OrderService;
import GroceryProject.OrderingProject.dto.OrderRequest;
import com.razorpay.RazorpayException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class UserOrderController {

    private final OrderService orderService;

    public UserOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) throws RazorpayException {
        return ResponseEntity.ok(orderService.createOrder(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order>getOrderById(@PathVariable Long id){
      return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @DeleteMapping("/orders/id/{id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }




}

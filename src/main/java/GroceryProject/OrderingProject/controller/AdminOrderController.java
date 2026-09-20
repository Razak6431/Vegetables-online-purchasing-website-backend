package GroceryProject.OrderingProject.controller;

import GroceryProject.OrderingProject.Entity.Order;
import GroceryProject.OrderingProject.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    // Get single order by ID (admin only)
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Order>>getOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }


    // Update order status (admin only)
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                   @RequestParam String newStatus) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, newStatus));
    }
}

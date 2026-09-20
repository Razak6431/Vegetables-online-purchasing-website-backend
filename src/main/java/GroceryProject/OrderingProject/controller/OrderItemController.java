package GroceryProject.OrderingProject.controller;

import GroceryProject.OrderingProject.Entity.Order;
import GroceryProject.OrderingProject.Entity.OrderItem;
import GroceryProject.OrderingProject.Service.OrderItemService;
import GroceryProject.OrderingProject.dto.OrderItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {

    private final OrderItemService orderItemService;
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }
    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItem>addItem(@PathVariable Long orderId, @RequestBody OrderItemRequest request){
        return ResponseEntity.ok(orderItemService.addItem(orderId, request));
    }
    @DeleteMapping("/items/{orderId}")
    public ResponseEntity<Void>deleteItem(@PathVariable Long orderId){
        orderItemService.removeItem(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/items/{id}/quantity")
    public ResponseEntity<OrderItem>updateItem(@PathVariable Long id,@RequestParam int newQuantity){
        return ResponseEntity.ok(orderItemService.updateItemQuantity(id,newQuantity));
    }
    @GetMapping
    public ResponseEntity<List<OrderItem>>getAllItemsByOrder(){
        return ResponseEntity.ok(orderItemService.getAllItemsByOrder());
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<OrderItem>getItemById(@PathVariable Long id){
        return ResponseEntity.ok(orderItemService.getItemId(id));
    }


}

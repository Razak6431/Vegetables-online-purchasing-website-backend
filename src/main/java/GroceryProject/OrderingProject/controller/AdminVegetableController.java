package GroceryProject.OrderingProject.controller;

import GroceryProject.OrderingProject.Entity.Vegetable;
import GroceryProject.OrderingProject.Service.VegetableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/vegetable")
public class AdminVegetableController {
    private final VegetableService vegetableService;


    public AdminVegetableController(VegetableService vegetableService) {
        this.vegetableService = vegetableService;
    }
    @PostMapping
    public ResponseEntity<Vegetable> addVegetable(@RequestBody Vegetable vegetable) {
        return ResponseEntity.ok(vegetableService.addVegetable(vegetable));
    }

    // Update existing vegetable
    @PutMapping("/{id}")
    public ResponseEntity<Vegetable> updateVegetable(@PathVariable Long id,
                                                     @RequestBody Vegetable vegetable) {
        return ResponseEntity.ok(vegetableService.updateVegetable(id, vegetable));
    }

    // Increase stock
    @PutMapping("/{id}/stock/increase")
    public ResponseEntity<Void> increaseStock(@PathVariable Long id,
                                              @RequestParam int quantity) {
        vegetableService.increaseStock(id, quantity);
        return ResponseEntity.noContent().build();
    }

    // Reduce stock
    @PutMapping("/{id}/stock/reduce")
    public ResponseEntity<Void> reduceStock(@PathVariable Long id,
                                            @RequestParam int quantity) {
        vegetableService.reduceStock(id, quantity);
        return ResponseEntity.noContent().build();
    }

    // Delete vegetable
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVegetable(@PathVariable Long id) {
        vegetableService.deleteVegetable(id);
        return ResponseEntity.noContent().build();
    }
}

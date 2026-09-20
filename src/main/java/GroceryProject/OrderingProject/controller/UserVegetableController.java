package GroceryProject.OrderingProject.controller;

import GroceryProject.OrderingProject.Entity.Vegetable;
import GroceryProject.OrderingProject.Service.VegetableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vegetable")
public class UserVegetableController {

    private final VegetableService vegetableService;


    public UserVegetableController(VegetableService vegetableService) {
        this.vegetableService = vegetableService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Vegetable>getVegetableByName(@PathVariable String name){
        return ResponseEntity.ok(vegetableService.getVegetableByName(name));
    }
    @GetMapping
    public ResponseEntity<List<Vegetable>>getAllVegetables(){
        return ResponseEntity.ok(vegetableService.getAllVegetables());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Vegetable>getVegetableById(@PathVariable Long id){
        return ResponseEntity.ok(vegetableService.findById(id));
    }


}

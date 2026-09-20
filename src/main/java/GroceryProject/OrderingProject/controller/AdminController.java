package GroceryProject.OrderingProject.controller;

import GroceryProject.OrderingProject.Entity.User;
import GroceryProject.OrderingProject.Service.UserService;
import GroceryProject.OrderingProject.dto.RegistrationRequest;
import GroceryProject.OrderingProject.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>>getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }



}

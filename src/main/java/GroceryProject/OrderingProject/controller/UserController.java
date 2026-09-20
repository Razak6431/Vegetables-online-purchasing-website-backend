package GroceryProject.OrderingProject.controller;

import GroceryProject.OrderingProject.Entity.User;
import GroceryProject.OrderingProject.Service.JwtService;
import GroceryProject.OrderingProject.Service.UserService;
import GroceryProject.OrderingProject.dto.LoginRequest;
import GroceryProject.OrderingProject.dto.LoginResponse;
import GroceryProject.OrderingProject.dto.RegistrationRequest;
import GroceryProject.OrderingProject.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(UserResponse.from(userService.userRegister(request)));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<UserResponse> registerAdmin(@RequestBody RegistrationRequest request){
        return ResponseEntity.ok(UserResponse.from(userService.registerAdmin(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        userService.login(request);

        User user = userService.getUserByEmail(request.getEmail());
        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(
                new LoginResponse(
                        token,
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                )
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(UserResponse.from(userService.getUserByEmail(email)));
    }
}

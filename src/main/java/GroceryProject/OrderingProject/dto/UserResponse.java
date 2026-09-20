package GroceryProject.OrderingProject.dto;

import GroceryProject.OrderingProject.Entity.Role;
import GroceryProject.OrderingProject.Entity.User;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<String> roles;

    public static UserResponse from(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRoles(
                user.getRoles().stream()
                        .map(Role::getName)
                        .toList()
        );
        return dto;
    }
}


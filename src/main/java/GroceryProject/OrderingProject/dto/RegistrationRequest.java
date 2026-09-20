package GroceryProject.OrderingProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequest {
    private Long id;
    @NotBlank(message = "name must required")
    private String name;
    @NotBlank(message = "email must required")
    private String email;

    @NotBlank(message = "password must required above 4 characters")
    @Size(min = 4,max = 50)
    private String password;

    @NotBlank(message = "Valid phone number")
    @Size(min=10,max = 13)
    private String phoneNumber;
}

package GroceryProject.OrderingProject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length=30)
    private String name;

    @Column(nullable = false,length = 255)
    private String email;

    @Column(nullable = false,length = 13)
    private String phoneNumber;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private boolean enabled=true;

    @Column(nullable = false,updatable = false)
    private boolean accountNonLocked=true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private List<Role> roles=new ArrayList<>();

    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
    }

    public void addRole(Role role){
        this.roles.add(role);
    }



}

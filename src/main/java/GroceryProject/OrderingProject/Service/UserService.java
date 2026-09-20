package GroceryProject.OrderingProject.Service;

import GroceryProject.OrderingProject.Entity.Role;
import GroceryProject.OrderingProject.Entity.User;
import GroceryProject.OrderingProject.Repository.RoleRepository;
import GroceryProject.OrderingProject.Repository.UserRepository;
import GroceryProject.OrderingProject.dto.LoginRequest;
import GroceryProject.OrderingProject.dto.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;



    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User userRegister(RegistrationRequest request){

     if(userRepository.existsByEmail(request.getEmail())){
         throw new RuntimeException("user already exists by this email "+request.getEmail());
     }

     if(userRepository.existsByName(request.getName())){
         throw new RuntimeException("user already exists by this name "+request.getName());
     }

        Role userRole=roleRepository.findByName("USER")
                .orElseGet(()->{
                   return roleRepository.save(Role.builder()
                           .name("USER")
                           .description("standard user")
                           .build());
                });

        User user=User.builder()
                .id(request.getId())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .enabled(true)
                .accountNonLocked(true)
                .build();

        user.addRole(userRole);

        return userRepository.save(user);


    }
    @Transactional
    public User registerAdmin(RegistrationRequest request){
        if(userRepository.existsByName(request.getName())){
            throw new RuntimeException("Admin already exists!!"+request.getName());
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Admin already exists by this email "+request.getEmail());
        }

        Role userRole=roleRepository.findByName("USER")
                .orElseGet(()->{
                  return  roleRepository.save(Role.builder()
                                    .name("USER")
                                    .description("standard user")

                            .build());
                });

        Role adminRole=roleRepository.findByName("ADMIN")
                .orElseGet(()->{
                    return roleRepository.save(Role.builder()
                                    .name("ADMIN")
                                    .description("standard admin")
                            .build());
                });

        User admin=User.builder()
                .id(request.getId())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .accountNonLocked(true)
                .build();

        admin.addRole(userRole);
        admin.addRole(adminRole);

        return userRepository.save(admin);



    }

    public LoginRequest login(LoginRequest request){
       User user=userRepository.getUserByEmail(request.getEmail());

       if(user==null){
           throw new RuntimeException("user not found ");
       }

       if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
           throw new RuntimeException("Invalid password");
       }


       return request;


    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    public List<User>getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByName(String name){
        return userRepository.getUserByName(name);
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("user not found by id = "+id));
    }


}

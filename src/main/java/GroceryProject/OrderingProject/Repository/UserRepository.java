package GroceryProject.OrderingProject.Repository;

import GroceryProject.OrderingProject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public boolean existsByName(String name);

    public boolean existsByEmail(String email);
  public User getUserByName(String name);
  public User getUserByEmail(String email);
}

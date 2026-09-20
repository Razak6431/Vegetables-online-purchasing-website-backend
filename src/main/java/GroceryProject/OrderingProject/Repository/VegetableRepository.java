package GroceryProject.OrderingProject.Repository;

import GroceryProject.OrderingProject.Entity.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VegetableRepository extends JpaRepository<Vegetable,Long> {

    public Vegetable findByName(String name);
}

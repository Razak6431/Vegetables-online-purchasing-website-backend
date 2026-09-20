package GroceryProject.OrderingProject.Service;

import GroceryProject.OrderingProject.Entity.Vegetable;
import GroceryProject.OrderingProject.Repository.VegetableRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VegetableService {
    private final VegetableRepository vegetableRepository;


    public VegetableService(VegetableRepository vegetableRepository) {
        this.vegetableRepository = vegetableRepository;
    }

    public Vegetable addVegetable(Vegetable vegetable){
        if(vegetable.getPricePerUnit()<=0){
            throw new RuntimeException("Price must be positive");
        }
        if (vegetable.getStockQuantity() < 0) {
            throw new RuntimeException("Stock cannot be negative");
        }

        return vegetableRepository.save(vegetable);
    }

    @Transactional
    public Vegetable updateVegetable(Long id,Vegetable updated){
      Vegetable existing=vegetableRepository.findById(id)
              .orElseThrow(()->new RuntimeException("Vegetable not found"));

      existing.setName(updated.getName());
      existing.setPricePerUnit(updated.getPricePerUnit());
      existing.setStockQuantity(updated.getStockQuantity());
      existing.setCategory(updated.getCategory());
      existing.setDescription(updated.getDescription());
      existing.setImageUrl(updated.getImageUrl());

      return vegetableRepository.save(existing);


    }

    //reduce stock with optimistic locking
    @Transactional
    public void reduceStock(Long id,int quantity){
        try{
            Vegetable veg=vegetableRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Vegetable not found"));

            if(veg.getStockQuantity()<quantity){
                throw new RuntimeException("Insufficient stock for "+veg.getName());
            }

            veg.setStockQuantity(veg.getStockQuantity()-quantity);
            vegetableRepository.saveAndFlush(veg); //flush ensures version check happens immediately
        }catch(OptimisticLockException e){
            throw new RuntimeException("Stock was updated by another transaction");
        }
    }

    @Transactional
    public void increaseStock(Long id,int quantity){
        Vegetable veg=vegetableRepository.findById(id)
                .orElseThrow(()->new RuntimeException("vegetable not found"));

        veg.setStockQuantity(veg.getStockQuantity()+quantity);

         vegetableRepository.save(veg);
    }

    public List<Vegetable>getAllVegetables(){
        return vegetableRepository.findAll();
    }

    public Vegetable getVegetableByName(String name){
        return vegetableRepository.findByName(name);
    }

    public Vegetable findById(Long id){
        return vegetableRepository.findById(id)
                .orElseThrow(()->new RuntimeException("vegetable not found by ID "+id));
    }


    public void deleteVegetable(Long id){
        vegetableRepository.deleteById(id);
        System.out.println("Vegetable deleted");
    }

}

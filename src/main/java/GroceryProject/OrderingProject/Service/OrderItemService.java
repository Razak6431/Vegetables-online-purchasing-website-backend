package GroceryProject.OrderingProject.Service;

import GroceryProject.OrderingProject.Entity.Order;
import GroceryProject.OrderingProject.Entity.OrderItem;
import GroceryProject.OrderingProject.Entity.Vegetable;
import GroceryProject.OrderingProject.Repository.OrderItemRepository;
import GroceryProject.OrderingProject.Repository.OrderRepository;
import GroceryProject.OrderingProject.Repository.VegetableRepository;
import GroceryProject.OrderingProject.dto.OrderItemRequest;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final VegetableRepository vegetableRepository;
    private final OrderRepository orderRepository;


    public OrderItemService(OrderItemRepository orderItemRepository, VegetableRepository vegetableRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.vegetableRepository = vegetableRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderItem addItem(Long orderId, OrderItemRequest request){
        Order order=orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found"));

        Vegetable veg=vegetableRepository.findById(request.getVegetableId())
                .orElseThrow(()->new RuntimeException("Vegetable not found"));

        if(veg.getStockQuantity()< request.getQuantity()){
            throw new RuntimeException("Insufficient stock for "+veg.getName());
        }

        try{
            //reduce stock safely
            veg.setStockQuantity(veg.getStockQuantity()- request.getQuantity());

            vegetableRepository.saveAndFlush(veg);

            double pricePerUnit= veg.getPricePerUnit();
            double subTotal=pricePerUnit* request.getQuantity();

            OrderItem item=OrderItem.builder()
                    .order(order)
                    .vegetable(veg)
                    .pricePerUnit(pricePerUnit)
                    .subTotal(subTotal)
                    .quantity(request.getQuantity())
                    .build();

            return orderItemRepository.save(item);


        }catch(OptimisticLockException e){
            throw new RuntimeException("Stock updated by another transaction, please retry", e);
        }

    }

    //update item quantity
    @Transactional
    public OrderItem updateItemQuantity(Long itemId,int newQuantity){

        OrderItem item=orderItemRepository.findById(itemId)
                .orElseThrow(()->new RuntimeException("OrderItem not found"));

        Vegetable veg=item.getVegetable();

        //restore old stock, then deduct new
        int availableStock=veg.getStockQuantity()+item.getQuantity();

        if(availableStock<newQuantity){
            throw new RuntimeException("Insufficient stock for "+veg.getName());
        }

        veg.setStockQuantity(availableStock-newQuantity);
        vegetableRepository.saveAndFlush(veg);


        item.setQuantity(newQuantity);
        item.setSubTotal(newQuantity * item.getPricePerUnit());


        return orderItemRepository.save(item);

    }

    @Transactional
    public void removeItem(Long itemId){
        OrderItem item=orderItemRepository.findById(itemId)
                .orElseThrow(()-> new RuntimeException("OrderItem not found"));

        Vegetable veg=item.getVegetable();
        veg.setStockQuantity(veg.getStockQuantity()+item.getQuantity());

        vegetableRepository.saveAndFlush(veg);

        orderItemRepository.delete(item);



    }

    public List<OrderItem>getAllItemsByOrder(){
        return orderItemRepository.findAll();
    }

    public OrderItem getItemId(Long itemId){
        return orderItemRepository.findById(itemId)
                .orElseThrow(()->new RuntimeException("OrderItem not found"));
    }



}

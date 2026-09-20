package GroceryProject.OrderingProject.Service;

import GroceryProject.OrderingProject.Entity.Order;
import GroceryProject.OrderingProject.Entity.OrderItem;
import GroceryProject.OrderingProject.Entity.User;
import GroceryProject.OrderingProject.Enum.OrderStatus;
import GroceryProject.OrderingProject.Repository.OrderRepository;
import GroceryProject.OrderingProject.dto.OrderRequest;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderItemService orderItemService;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository, UserService userService, OrderItemService orderItemService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderItemService = orderItemService;
        this.paymentService = paymentService;
    }
    @Transactional
    public Order createOrder(OrderRequest request) throws RazorpayException {
        User user=userService.getUserById(request.getUserId());

     Order order=Order.builder()
             .orderType(request.getOrderType())
             .user(user)
             .build();

     order=orderRepository.save(order);

     //Add items
     double totalAmount=0.0;
     for(var itemReq:request.getItems()){
         OrderItem item=orderItemService.addItem(order.getId(), itemReq);
         totalAmount+=item.getSubTotal();
     }
     order.setTotalAmount(totalAmount);

     paymentService.createPayment(order);
     return orderRepository.save(order);



    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(()->new RuntimeException("order not found"));
    }

    public List<Order> getOrdersByUser(Long userId){
        User user=userService.getUserById(userId);
        return orderRepository.findByUserId(user.getId());
    }

    public List<Order>getAllOrders(){
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrder(Long orderId,String newStatus){
        Order order=getOrderById(orderId);
        order.setOrderStatus(OrderStatus.valueOf(newStatus));
       return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(Long orderId){
        Order order=getOrderById(orderId);

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled");
        }

        //restore stock for each item
        for(OrderItem item:order.getItems()){
            var veg=item.getVegetable();
            veg.setStockQuantity(veg.getStockQuantity()+ item.getQuantity());
        }

        //update order status
        order.setOrderStatus(OrderStatus.CANCELLED);

        paymentService.refundPayment(order);
      return  orderRepository.save(order);




    }


}

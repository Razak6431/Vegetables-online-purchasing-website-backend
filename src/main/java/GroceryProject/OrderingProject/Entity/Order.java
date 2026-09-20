package GroceryProject.OrderingProject.Entity;

import GroceryProject.OrderingProject.Enum.OrderStatus;
import GroceryProject.OrderingProject.Enum.OrderType;
import GroceryProject.OrderingProject.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private OrderType orderType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @Column(nullable = false)
    private Double totalAmount=0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private PaymentStatus paymentstatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> items=new ArrayList<>();

    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
        this.paymentstatus=PaymentStatus.UNPAID;
        this.orderStatus=OrderStatus.PENDING;
    }


}

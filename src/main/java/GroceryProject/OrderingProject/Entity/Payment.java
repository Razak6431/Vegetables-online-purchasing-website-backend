package GroceryProject.OrderingProject.Entity;

import GroceryProject.OrderingProject.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private PaymentStatus status;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false,length = 10)
    private String currency;  //e.g "INR","USD"

    @Column(length = 100)
    private String transactionId; //Gateway transaction referance

    @Column(length = 100)
    private String razorpayOrderId;
    @Column(length = 100)
    private String razorpayPaymentId;



    @Column(length = 100)
    private String paymentMethod; //e.g "card",upi,net banking

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
        this.status=PaymentStatus.PENDING;
    }



}

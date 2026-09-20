package GroceryProject.OrderingProject.Entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "vegetable_id",nullable = false)
    private Vegetable vegetable;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Double pricePerUnit;  //pricePerKg

    @Column(nullable = false)
    private Double subTotal;


}

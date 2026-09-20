package GroceryProject.OrderingProject.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vegetables")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Vegetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 30,unique = true)
    private String name;  //tomato,onion

    @Column(nullable = false)
    private Double pricePerUnit;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(length = 30)
    private String category;  //e.g leafy, root, fruit

    @Column(length = 255)
    private String description; //for optional details

    @Column(length = 255)
    private String imageUrl; //optional product image

    @Version
    private Long version; //optimistic locking field (for concurrency control) means multiple requests control

}

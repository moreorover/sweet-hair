package martin.sweethair.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderProducts> orders;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private List<SaleItems> sales;
}

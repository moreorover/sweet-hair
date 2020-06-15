package martin.sweethair.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItems> orders;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleItems> sales;
}

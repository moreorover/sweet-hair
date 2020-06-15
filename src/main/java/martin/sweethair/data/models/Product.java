package martin.sweethair.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private Set<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "sale_items",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "sale_id")
    )
    private Set<Sale> sales;
}

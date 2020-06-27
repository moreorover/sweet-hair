package martin.sweethair.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int inStockCount;
    private int size;
    private String sizeUnit;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderProducts> orders;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private List<SaleProducts> sales;

    public void stockCountChangedFromOrder(int oldOrderProductQuantity, int newOrderProductQuantity) {
        this.inStockCount = this.inStockCount - oldOrderProductQuantity + newOrderProductQuantity;
    }

    public void stockCountChangedFromSale(int oldOrderProductQuantity, int newOrderProductQuantity) {
        this.inStockCount = this.inStockCount + oldOrderProductQuantity - newOrderProductQuantity;
    }
}

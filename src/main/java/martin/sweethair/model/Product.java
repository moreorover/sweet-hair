package martin.sweethair.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
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
    private double price;
    private double totalSpent;
    private double totalReceived;
    private double profit;

    @ManyToMany(mappedBy = "products")
    private List<Operation> operations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return inStockCount == product.inStockCount &&
                size == product.size &&
                Double.compare(product.price, price) == 0 &&
                Double.compare(product.totalSpent, totalSpent) == 0 &&
                Double.compare(product.totalReceived, totalReceived) == 0 &&
                Double.compare(product.profit, profit) == 0 &&
                name.equals(product.name) &&
                sizeUnit.equals(product.sizeUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inStockCount, size, sizeUnit, price, totalSpent, totalReceived, profit);
    }
}

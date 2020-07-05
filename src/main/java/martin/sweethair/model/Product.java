package martin.sweethair.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
    private double price;
    private double totalSpent;
    private double totalReceived;
    private double profit;

    @ManyToMany(mappedBy = "products")
    private List<Operation> operations = new ArrayList<>();
}

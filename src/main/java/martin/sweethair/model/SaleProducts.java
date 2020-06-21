package martin.sweethair.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "sale_items")
public class SaleProducts {

    @EmbeddedId
    private SaleProductsId saleItem;

    @ManyToOne
    @MapsId("saleId")
    private Sale sale;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private int quantity;
    private double unitPrice;

    public double getTotal() {
        return quantity * unitPrice;
    }
}

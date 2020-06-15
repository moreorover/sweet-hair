package martin.sweethair.data.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sales")
public class Sale extends BaseEntity{

    private LocalDateTime soldAt;
    private double total;
    private int itemsCount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SaleItems> products;
}

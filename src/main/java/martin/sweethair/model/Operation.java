package martin.sweethair.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Double total;
    private Double profit;
    private String note;

    @ManyToOne
    @JoinColumn(name = "operation_type", nullable = false)
    private OperationType operationType;

    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    private Client client;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "operation_products",
            joinColumns = @JoinColumn(name = "operation_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "operation")
    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return date.equals(operation.date) &&
                total.equals(operation.total) &&
                profit.equals(operation.profit) &&
                note.equals(operation.note) &&
                operationType.equals(operation.operationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, total, profit, note, operationType);
    }
}

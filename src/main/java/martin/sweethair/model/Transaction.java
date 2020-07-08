package martin.sweethair.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean paid;
    private double amount;
    private String note;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "operation", nullable = false)
    private Operation operation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return paid == that.paid &&
                Double.compare(that.amount, amount) == 0 &&
                note.equals(that.note) &&
                date.equals(that.date) &&
                transactionType.equals(that.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paid, amount, note, date, transactionType);
    }
}

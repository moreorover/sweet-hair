package martin.sweethair.data.projections;

import martin.sweethair.data.models.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

@Projection(name = "OrderCard", types = { Order.class })
public interface OrderCard {

    @Value("#{target.id}")
    long getId();
    
    LocalDate getPurchasedAt();
    double getTotal();
    int getItemsCount();
    String getCurrency();

    @Value("#{target.getSupplier().getName()}")
    String getSupplierName();
}

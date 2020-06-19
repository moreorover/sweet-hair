package martin.sweethair.data.projections;

import martin.sweethair.data.models.Order;
import martin.sweethair.data.models.OrderItems;
import martin.sweethair.data.models.Supplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.util.List;

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

    @Value("#{target.getProducts()}")
    List<OrderItems> getProducts();
}

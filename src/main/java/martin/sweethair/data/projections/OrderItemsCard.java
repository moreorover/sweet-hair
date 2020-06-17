package martin.sweethair.data.projections;

import martin.sweethair.data.models.OrderItems;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "OrderItemsCard", types = { OrderItems.class })
public interface OrderItemsCard {

    int getQuantity();
    double getUnitPrice();

    @Value("#{target.getProduct().getName()}")
    String getProductName();

    @Value("#{target.getProduct().getId()}")
    String getProductId();

}

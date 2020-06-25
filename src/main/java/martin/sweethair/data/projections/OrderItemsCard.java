package martin.sweethair.data.projections;

import martin.sweethair.model.OrderProducts;
import martin.sweethair.model.OrderProductsId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "OrderItemsCard", types = { OrderProducts.class })
public interface OrderItemsCard {

    int getQuantity();
    double getUnitPrice();

    @Value("#{target.orderItem()}")
    OrderProductsId getOrderItemId();

    @Value("#{target.getProduct().getName()}")
    String getProductName();

    @Value("#{target.getProduct().getId()}")
    String getProductId();

    @Value("#{target.getProduct()}")
    ProductProjection getProduct();

}

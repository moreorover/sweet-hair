package martin.sweethair.converter;

import martin.sweethair.data.models.OrderItems;
import martin.sweethair.data.models.OrderItemsId;
import martin.sweethair.repository.OrderItemsRepository;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class OrderItemsIdConverter implements BackendIdConverter {

    private OrderItemsRepository orderItemsRepository;

    @Override
    public Serializable fromRequestId(String s, Class<?> aClass) {

        String[] parts = s.split("_", 2);
        if (parts != null && parts.length == 2) {
            return new OrderItemsId(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        } else {
            throw new IllegalArgumentException("Id must consist of two longs separated by '_'!");
        }
    }

    @Override
    public String toRequestId(Serializable serializable, Class<?> aClass) {
        OrderItemsId id = (OrderItemsId) serializable;
        return String.format("%s_%s", id.getOrderId(), id.getProductId());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderItems.class.equals(aClass);
    }
}

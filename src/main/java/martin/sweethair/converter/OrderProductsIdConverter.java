package martin.sweethair.converter;

import martin.sweethair.model.OrderProducts;
import martin.sweethair.model.OrderProductsId;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class OrderProductsIdConverter implements BackendIdConverter {

    @Override
    public Serializable fromRequestId(String s, Class<?> aClass) {

        String[] parts = s.split("_", 2);
        if (parts != null && parts.length == 2) {
            return new OrderProductsId(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        } else {
            throw new IllegalArgumentException("Id must consist of two longs separated by '_'!");
        }
    }

    @Override
    public String toRequestId(Serializable serializable, Class<?> aClass) {
        OrderProductsId id = (OrderProductsId) serializable;
        return String.format("%s_%s", id.getOrderId(), id.getProductId());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderProducts.class.equals(aClass);
    }
}

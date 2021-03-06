package martin.sweethair;

import martin.sweethair.model.*;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ExposeEntityIdRestMvcConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfig) {
        restConfig.exposeIdsFor(Customer.class);
        restConfig.exposeIdsFor(Order.class);
        restConfig.exposeIdsFor(OrderProducts.class);
        restConfig.exposeIdsFor(Product.class);
        restConfig.exposeIdsFor(Sale.class);
        restConfig.exposeIdsFor(SaleProducts.class);
        restConfig.exposeIdsFor(Supplier.class);
    }
}

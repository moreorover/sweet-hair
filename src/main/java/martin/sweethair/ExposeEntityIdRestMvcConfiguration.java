package martin.sweethair;

import martin.sweethair.data.models.Product;
import martin.sweethair.data.models.Supplier;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ExposeEntityIdRestMvcConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration restConfig) {
        restConfig.exposeIdsFor(Product.class);
        restConfig.exposeIdsFor(Supplier.class);
    }
}

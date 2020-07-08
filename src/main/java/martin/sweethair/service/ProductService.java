package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.PostProductDto;
import martin.sweethair.dto.base.ProductDtoBase;
import martin.sweethair.dto.full.DtoFull;
import martin.sweethair.dto.full.ProductDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.Product;
import martin.sweethair.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ModelMapper modelMapper;

    private final ProductRepository productRepository;

    @Transactional
    public Product save(PostProductDto postProductDto) {
        Product newProduct = Product.builder()
                .name(postProductDto.getName())
                .inStockCount(postProductDto.getInStockCount())
                .size(postProductDto.getSize())
                .sizeUnit(postProductDto.getSizeUnit())
                .price(postProductDto.getPrice())
                .totalSpent(postProductDto.getTotalSpent())
                .totalReceived(postProductDto.getTotalReceived())
                .profit(postProductDto.getProfit())
                .build();

        return productRepository.save(newProduct);
    }

    @Transactional(readOnly = true)
    public Set<Product> getAll() {
        return new HashSet<>(productRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + id));
    }

    @Transactional
    public Product update(ProductDtoBase dtoBase) {
        Product product = this.getById(dtoBase.getId());

        if (product.getId().equals(dtoBase.getId())) {
            if (!product.equals(modelMapper.map(dtoBase, Product.class))) {
                product.setName(dtoBase.getName());
                product.setInStockCount(dtoBase.getInStockCount());
                product.setSize(dtoBase.getSize());
                product.setSizeUnit(dtoBase.getSizeUnit());
                product.setPrice(dtoBase.getPrice());
                product.setTotalSpent(dtoBase.getTotalSpent());
                product.setTotalReceived(dtoBase.getTotalReceived());
                product.setProfit(dtoBase.getProfit());
                productRepository.save(product);
            }
        }
        return product;
    }

    @Transactional
    public boolean deleteById(Long id) {
        Product product = this.getById(id);

        if (product.getOperations().isEmpty()){
            productRepository.delete(product);
            return true;
        }
        return false;
    }
}

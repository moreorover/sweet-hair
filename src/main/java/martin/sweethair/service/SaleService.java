package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.SaleProductDtoBase;
import martin.sweethair.dto.full.SaleDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.*;
import martin.sweethair.repository.CustomerRepository;
import martin.sweethair.repository.ProductRepository;
import martin.sweethair.repository.SaleProductsRepository;
import martin.sweethair.repository.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SaleService {

    private final ModelMapper modelMapper;

    private final SaleProductsRepository saleProductsRepository;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public SaleDtoFull save(SaleDtoFull saleDtoFull) {
        Customer customer = customerRepository.findById(saleDtoFull.getCustomer().getId())
                .orElseThrow(() -> new SpringDataException("No customer found with ID -> " + saleDtoFull.getCustomer().getId()));

        Sale newSale = Sale.builder()
                .customer(customer)
                .total(saleDtoFull.getTotal())
                .itemsCount(saleDtoFull.getItemsCount())
                .currency(saleDtoFull.getCurrency())
                .operationDate(saleDtoFull.getOperationDate())
                .build();
        saleRepository.save(modelMapper.map(newSale, Sale.class));

        Map<Long, Product> products = saleDtoFull
                .getProducts()
                .stream()
                .map(product -> productRepository.findById(product.getProduct().getId())
                        .orElseThrow(() -> new SpringDataException("No product found with ID -> " + product.getProduct().getId())))
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<SaleProducts> saleProducts = new ArrayList<>();

        saleDtoFull.getProducts().forEach(product -> {
            SaleProducts saleProduct = SaleProducts.builder()
                    .saleItem(new SaleProductsId(newSale.getId(), product.getProduct().getId()))
                    .sale(newSale)
                    .product(products.get(product.getProduct().getId()))
                    .quantity(product.getQuantity())
                    .unitPrice(product.getUnitPrice())
                    .build();
            saleProducts.add(saleProduct);

            Product p = productRepository.findById(product.getProduct().getId())
                    .orElseThrow(() -> new SpringDataException("No product found with ID -> " + product.getProduct().getId()));

            p.stockCountChangedFromSale(0 , product.getQuantity());
            productRepository.save(p);
        });

        if (newSale.getProducts() == null) {
            newSale.setProducts(saleProducts);
        } else {
            newSale.getProducts().addAll(saleProducts);
        }
        products.values().forEach(product -> product.getSales().addAll(saleProducts));
        saleProductsRepository.saveAll(saleProducts);
//        Order save = orderRepository.save(newOrder);
        saleDtoFull.setId(newSale.getId());
        return saleDtoFull;
    }

    @Transactional(readOnly = true)
    public Set<SaleDtoFull> getAll() {
        return saleRepository.findAll()
                .stream()
                .map(sale -> modelMapper.map(sale, SaleDtoFull.class))
                .collect(Collectors.toSet());
    }

    public SaleDtoFull getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No sale found with ID -> " + id));
        return modelMapper.map(sale, SaleDtoFull.class);
    }

    @Transactional
    public SaleDtoFull updateSale(SaleDtoFull saleDto) {
        Sale sale = saleRepository.findById(saleDto.getId())
                .orElseThrow(() -> new SpringDataException("No sale found with ID -> " + saleDto.getId()));

        Map<Long, SaleProductDtoBase> payloadProducts = saleDto
                .getProducts()
                .stream()
                .collect(Collectors.toMap(SaleProductDtoBase::getProductId, Function.identity()));

        sale.getProducts().forEach(saleProduct -> {
            SaleProductDtoBase o = payloadProducts.get(saleProduct.getProduct().getId());
            if (o == null) {
                saleProductsRepository.delete(saleProduct);
            } else {
                if (o.getUnitPrice() != saleProduct.getUnitPrice()) {
                    saleProduct.setUnitPrice(o.getUnitPrice());
                }
                if (o.getQuantity() != saleProduct.getQuantity()) {
                    saleProduct.getProduct().stockCountChangedFromSale(saleProduct.getQuantity(), o.getQuantity());
                    saleProduct.setQuantity(o.getQuantity());
                }
                payloadProducts.remove(saleProduct.getProduct().getId());
            }
        });

        if (payloadProducts.size() > 0) {
            payloadProducts.values().forEach(payloadProduct -> this.addProductToSale(sale, payloadProduct));
        }

        if (!sale.getCustomer().getId().equals(saleDto.getCustomer().getId())) {
            Customer customer = customerRepository.findById(saleDto.getCustomer().getId())
                    .orElseThrow(() -> new SpringDataException("No customer found with ID -> " + saleDto.getId()));

            sale.setCustomer(customer);
        }

        if (sale.getOperationDate() != saleDto.getOperationDate()) {
            sale.setOperationDate(saleDto.getOperationDate());
        }

        this.updateOrderTotals(sale, saleDto.getItemsCount(), saleDto.getTotal());

        Sale save = saleRepository.getOne(sale.getId());
        return modelMapper.map(save, SaleDtoFull.class);
    }

    public void deleteSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No sale found with ID -> " + id));

        sale.getProducts().forEach(saleProduct -> {
            Product product = productRepository.findById(saleProduct.getProduct().getId())
                    .orElseThrow(() -> new SpringDataException("No product found with ID -> " + saleProduct.getProduct().getId()));
            product.stockCountChangedFromSale(saleProduct.getQuantity(), 0);
            saleProductsRepository.delete(saleProduct);
        });

        saleRepository.deleteById(id);
    }

    @Transactional
    public void addProductToSale(Sale sale, SaleProductDtoBase saleProductDto) {
        Product product = productRepository.findById(saleProductDto.getProduct().getId())
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + saleProductDto.getProduct().getId()));

        product.stockCountChangedFromOrder(0 , saleProductDto.getQuantity());
        productRepository.save(product);

        SaleProducts newSaleProducts = SaleProducts.builder()
                .saleItem(new SaleProductsId(sale.getId(), saleProductDto.getProduct().getId()))
                .sale(sale)
                .product(product)
                .quantity(saleProductDto.getQuantity())
                .unitPrice(saleProductDto.getUnitPrice())
                .build();

        if (sale.getProducts() == null) {
            sale.setProducts(new ArrayList<>());
        }

        if (product.getOrders() == null) {
            product.setOrders(new ArrayList<>());
        }

        sale.getProducts().add(newSaleProducts);
        product.getSales().add(newSaleProducts);

        saleProductsRepository.save(newSaleProducts);
    }

    @Transactional
    public void updateOrderTotals(Sale sale, int quantity, double total) {
        sale.setItemsCount(quantity);
        sale.setTotal(total);
        saleRepository.save(sale);
    }
}

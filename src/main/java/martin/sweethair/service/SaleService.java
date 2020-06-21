package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.OrderDtoBase;
import martin.sweethair.dto.base.OrderProductDtoBase;
import martin.sweethair.dto.base.SaleDtoBase;
import martin.sweethair.dto.base.SaleProductDtoBase;
import martin.sweethair.dto.full.OrderDtoFull;
import martin.sweethair.dto.full.SaleDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.*;
import martin.sweethair.repository.ProductRepository;
import martin.sweethair.repository.SaleProductsRepository;
import martin.sweethair.repository.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SaleService {

    private final ModelMapper modelMapper;

    private final SaleProductsRepository saleProductsRepository;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    @Transactional
    public SaleDtoBase save(SaleDtoBase saleDtoBase) {
        Sale save = saleRepository.save(modelMapper.map(saleDtoBase, Sale.class));
        saleDtoBase.setId(save.getId());
        return saleDtoBase;
    }

    @Transactional(readOnly = true)
    public Set<SaleDtoFull> getAll() {
        return saleRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, SaleDtoFull.class))
                .collect(Collectors.toSet());
    }

    public SaleDtoFull getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No sale found with ID -> " + id));
        return modelMapper.map(sale, SaleDtoFull.class);
    }

    @Transactional
    public SaleDtoFull updateOrder(SaleDtoFull saleDtoFull) {
        Sale sale = saleRepository.findById(saleDtoFull.getId())
                .orElseThrow(() -> new SpringDataException("No sale found with ID -> " + saleDtoFull.getId()));
        // TODO implement update logic

        Sale save = saleRepository.save(sale);
        return modelMapper.map(save, SaleDtoFull.class);
    }

    public void deleteSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No sale found with ID -> " + id));

        sale.getProducts().forEach(saleProductsRepository::delete);

        saleRepository.deleteById(id);
    }

    @Transactional
    public SaleDtoFull addProduct(Long saleId, SaleProductDtoBase saleProductDtoBase) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new SpringDataException("No sale found with ID -> " + saleId));

        Product product = productRepository.findById(saleProductDtoBase.getProduct().getId())
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + saleProductDtoBase.getProduct().getId()));

        SaleProducts newSaleProducts = SaleProducts.builder()
                .saleItem(new SaleProductsId(saleId, saleProductDtoBase.getProduct().getId()))
                .sale(sale)
                .product(product)
                .quantity(saleProductDtoBase.getQuantity())
                .unitPrice(saleProductDtoBase.getUnitPrice())
                .build();

        sale.getProducts().add(newSaleProducts);
        product.getSales().add(newSaleProducts);
        sale.setTotal(sale.getProducts().stream().mapToDouble(SaleProducts::getTotal).sum());
        sale.setItemsCount(sale.getProducts().stream().mapToInt(SaleProducts::getQuantity).sum());

        saleProductsRepository.save(newSaleProducts);
        Sale save = saleRepository.save(sale);
        return modelMapper.map(save, SaleDtoFull.class);
    }
}

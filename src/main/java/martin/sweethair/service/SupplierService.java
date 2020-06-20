package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.ProductDto;
import martin.sweethair.dto.SupplierDto;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.Product;
import martin.sweethair.model.Supplier;
import martin.sweethair.repository.ProductRepository;
import martin.sweethair.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SupplierService {

    private final ModelMapper modelMapper;

    private final SupplierRepository supplierRepository;

    @Transactional
    public SupplierDto save(SupplierDto productDto) {
        Supplier save = supplierRepository.save(modelMapper.map(productDto, Supplier.class));
        productDto.setId(save.getId());
        return productDto;
    }

    @Transactional(readOnly = true)
    public Set<SupplierDto> getAll() {
        return supplierRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, SupplierDto.class))
                .collect(Collectors.toSet());
    }

    public SupplierDto getProductById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No supplier found with ID -> " + id));
        return modelMapper.map(supplier, SupplierDto.class);
    }

    public SupplierDto updateProduct(SupplierDto productDto) {
        Supplier supplier = supplierRepository.findById(productDto.getId())
                .orElseThrow(() -> new SpringDataException("No supplier found with ID -> " + productDto.getId()));;

        supplier.setName(productDto.getName());
        supplierRepository.save(supplier);
        return modelMapper.map(supplier, SupplierDto.class);
    }

    public void deleteProductById(Long id) {
        supplierRepository.deleteById(id);
    }
}

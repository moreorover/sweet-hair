package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.SupplierDtoBase;
import martin.sweethair.dto.full.SupplierDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.Supplier;
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
    public SupplierDtoBase save(SupplierDtoBase supplierDtoBase) {
        Supplier save = supplierRepository.save(modelMapper.map(supplierDtoBase, Supplier.class));
        supplierDtoBase.setId(save.getId());
        return supplierDtoBase;
    }

    @Transactional(readOnly = true)
    public Set<SupplierDtoFull> getAll() {
        return supplierRepository.findAll()
                .stream()
                .map(supplier -> modelMapper.map(supplier, SupplierDtoFull.class))
                .collect(Collectors.toSet());
    }

    public SupplierDtoFull getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No supplier found with ID -> " + id));
        return modelMapper.map(supplier, SupplierDtoFull.class);
    }

    @Transactional
    public SupplierDtoBase updateSupplier(SupplierDtoBase supplierDtoBase) {
        Supplier supplier = supplierRepository.findById(supplierDtoBase.getId())
                .orElseThrow(() -> new SpringDataException("No supplier found with ID -> " + supplierDtoBase.getId()));
        supplier.setName(supplierDtoBase.getName());
        supplier.setUrl(supplierDtoBase.getUrl());
        supplier.setLogo(supplierDtoBase.getLogo());
        supplierRepository.save(supplier);
        return modelMapper.map(supplier, SupplierDtoBase.class);
    }

    public void deleteProductById(Long id) {
        supplierRepository.deleteById(id);
    }
}

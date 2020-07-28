package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.PostOperationTypeDto;
import martin.sweethair.dto.base.OperationTypeDtoBase;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.OperationType;
import martin.sweethair.repository.OperationTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class OperationTypeService {

    private final ModelMapper modelMapper;

    private final OperationTypeRepository operationTypeRepository;

    @Transactional
    public OperationType save(PostOperationTypeDto dtoBase) {
        OperationType newClientType = OperationType.builder()
                .name(dtoBase.getName())
                .build();
        return operationTypeRepository.save(newClientType);
    }

    @Transactional(readOnly = true)
    public Set<OperationType> getAll() {
        return new HashSet<>(operationTypeRepository.findAll());
    }

    @Transactional(readOnly = true)
    public OperationType getById(Long id) {
        return operationTypeRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No operation type found with ID -> " + id));
    }

    @Transactional(readOnly = true)
    public OperationType getByName(String name) {
        OperationType operationType = operationTypeRepository.findFirstByName(name);
        if (operationType == null) {
            throw new SpringDataException("No operation type found with name -> " + name);
        }
        return operationType;
    }

    @Transactional
    public OperationType update(OperationTypeDtoBase dtoBase) {
        OperationType operationType = this.getById(dtoBase.getId());

        if (operationType.getId().equals(dtoBase.getId())) {
            if (!operationType.equals(modelMapper.map(dtoBase, OperationType.class))) {
                operationType.setName(dtoBase.getName());
                operationTypeRepository.save(operationType);
            }
        }
        return operationType;
    }

    @Transactional
    public boolean deleteById(Long id) {
        OperationType operationType = this.getById(id);

        if (operationType.getOperations().isEmpty()) {
            operationTypeRepository.delete(operationType);
            return true;
        }
        return false;
    }
}

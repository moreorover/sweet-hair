package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.PostOperationDto;
import martin.sweethair.dto.full.OperationDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.*;
import martin.sweethair.repository.OperationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class OperationService {

    private final ModelMapper modelMapper;

    private final OperationRepository operationRepository;
    private final OperationTypeService operationTypeService;
    private final ClientService clientService;

    @Transactional
    public Operation save(PostOperationDto operationDto) {
        OperationType operationType = operationTypeService.getByName(operationDto.getOperationType().getName());
        Client client =  clientService.getByName(operationDto.getClient().getName());

        Operation newOperation = Operation.builder()
                .date(operationDto.getDate())
                .total(operationDto.getTotal())
                .profit(operationDto.getProfit())
                .note(operationDto.getNote())
                .operationType(operationType)
                .client(client)
                .build();

        return operationRepository.save(newOperation);
    }

    @Transactional(readOnly = true)
    public Set<Operation> getAll() {
        return new HashSet<>(operationRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Operation getById(Long id) {
        return operationRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No operation found with ID -> " + id));
    }

    @Transactional
    public Operation update(OperationDtoFull dtoFull) {
        Operation operation = this.getById(dtoFull.getId());
        OperationType operationType = operationTypeService.getByName(dtoFull.getOperationType().getName());

        if (operation.getId().equals(dtoFull.getId())) {
            if (!operation.equals(modelMapper.map(dtoFull, Operation.class))) {
                operation.setDate(dtoFull.getDate());
                operation.setTotal(dtoFull.getTotal());
                operation.setProfit(dtoFull.getProfit());
                operation.setNote(dtoFull.getNote());
                operation.setOperationType(operationType);
                operationRepository.save(operation);
            }
        }
        return operation;
    }

    @Transactional
    public boolean deleteById(Long id) {
        Operation operation = this.getById(id);
        operationRepository.delete(operation);
        return true;
    }
}

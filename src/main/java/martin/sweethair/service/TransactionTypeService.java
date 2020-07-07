package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.TransactionTypeDtoBase;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.TransactionType;
import martin.sweethair.repository.TransactionTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionTypeService {

    private final ModelMapper modelMapper;

    private final TransactionTypeRepository transactionTypeRepository;

    @Transactional
    public TransactionType save(TransactionTypeDtoBase dtoBase) {
        TransactionType newTransactionType = TransactionType.builder()
                .name(dtoBase.getName())
                .build();

        return transactionTypeRepository.save(newTransactionType);
    }

    @Transactional
    public Set<TransactionType> getAll() {
        return new HashSet<>(transactionTypeRepository.findAll());
    }

    public TransactionType getById(Long id) {
        return transactionTypeRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No transaction type found with ID -> " + id));
    }

    public TransactionType getByName(String name) {
        TransactionType transactionType = transactionTypeRepository.findFirstByName(name);
        if (transactionType == null) {
            throw new SpringDataException("No transaction type found with name -> " + name);
        }
        return transactionType;
    }

    public TransactionType update(TransactionTypeDtoBase dtoBase) {
        TransactionType TransactionType = this.getById(dtoBase.getId());

        if (TransactionType.getId().equals(dtoBase.getId())) {
            if (!TransactionType.equals(modelMapper.map(dtoBase, TransactionType.class))) {
                TransactionType.setName(dtoBase.getName());
                transactionTypeRepository.save(TransactionType);
            }
        }
        return TransactionType;
    }

    public boolean deleteById(Long id) {
        TransactionType TransactionType = this.getById(id);

        if (TransactionType.getTransactions().isEmpty()) {
            transactionTypeRepository.delete(TransactionType);
            return true;
        }
        return false;
    }
}

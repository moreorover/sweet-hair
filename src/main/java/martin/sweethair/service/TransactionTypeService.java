package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.TransactionTypeDtoBase;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.Transaction;
import martin.sweethair.model.TransactionType;
import martin.sweethair.repository.TransactionTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
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

    @Transactional(readOnly = true)
    public Set<TransactionType> getAll() {
        return new HashSet<>(transactionTypeRepository.findAll());
    }

    @Transactional(readOnly = true)
    public TransactionType getById(Long id) {
        return transactionTypeRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No transaction type found with ID -> " + id));
    }

    @Transactional
    public TransactionType getByName(String name) {
        TransactionType transactionType = transactionTypeRepository.findFirstByName(name);
        if (transactionType == null) {
            throw new SpringDataException("No transaction type found with name -> " + name);
        }
        return transactionType;
    }

    @Transactional
    public TransactionType update(TransactionTypeDtoBase dtoBase) {
        TransactionType transactionType = this.getById(dtoBase.getId());

        if (transactionType.getId().equals(dtoBase.getId())) {
            if (!transactionType.equals(modelMapper.map(dtoBase, TransactionType.class))) {
                transactionType.setName(dtoBase.getName());
                transactionTypeRepository.save(transactionType);
            }
        }
        return transactionType;
    }

    @Transactional
    public boolean deleteById(Long id) {
        TransactionType transactionType = this.getById(id);

        List<Transaction> transactions = transactionType.getTransactions();

        if (transactionType.getTransactions().isEmpty()) {
            transactionTypeRepository.delete(transactionType);
            return true;
        }
        return false;
    }
}

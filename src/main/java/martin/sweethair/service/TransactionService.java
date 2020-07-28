package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.PostProductDto;
import martin.sweethair.dto.PostTransactionDto;
import martin.sweethair.dto.base.ProductDtoBase;
import martin.sweethair.dto.base.TransactionDtoBase;
import martin.sweethair.dto.full.TransactionDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.Operation;
import martin.sweethair.model.Product;
import martin.sweethair.model.Transaction;
import martin.sweethair.model.TransactionType;
import martin.sweethair.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private final ModelMapper modelMapper;

    private final TransactionRepository transactionRepository;
    private final TransactionTypeService transactionTypeService;
    private final OperationService operationService;

    @Transactional
    public Transaction save(PostTransactionDto postTransactionDto) {
        TransactionType transactionType = transactionTypeService.getByName(postTransactionDto.getTransactionType().getName());
        // TODO operation

        Transaction newProduct = Transaction.builder()
                .paid(postTransactionDto.isPaid())
                .amount(postTransactionDto.getAmount())
                .note(postTransactionDto.getNote())
                .date(postTransactionDto.getDate())
                .transactionType(transactionType)
                .build();

        return transactionRepository.save(newProduct);
    }

    @Transactional(readOnly = true)
    public Set<Transaction> getAll() {
        return new HashSet<>(transactionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No transaction found with ID -> " + id));
    }

    @Transactional
    public Transaction update(TransactionDtoFull dtoFull) {
        TransactionType transactionType = transactionTypeService.getByName(dtoFull.getTransactionType().getName());
        Transaction transaction = this.getById(dtoFull.getId());

        if (transaction.getId().equals(dtoFull.getId())) {
            if (!transaction.equals(modelMapper.map(dtoFull, Transaction.class))) {
                transaction.setPaid(dtoFull.isPaid());
                transaction.setAmount(dtoFull.getAmount());
                transaction.setNote(dtoFull.getNote());
                transaction.setDate(dtoFull.getDate());
                transaction.setTransactionType(transactionType);
                transactionRepository.save(transaction);
            }
        }
        return transaction;
    }

    @Transactional
    public boolean deleteById(Long id) {
        Transaction transaction = this.getById(id);
        transactionRepository.delete(transaction);
        return true;
    }
}

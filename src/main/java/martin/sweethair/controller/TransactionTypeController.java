package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.TransactionTypeDtoBase;
import martin.sweethair.dto.full.TransactionTypeDtoFull;
import martin.sweethair.model.TransactionType;
import martin.sweethair.service.TransactionTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/transaction_type")
@AllArgsConstructor
@Slf4j
public class TransactionTypeController {

    private final ModelMapper modelMapper;

    private final TransactionTypeService transactionTypeService;

    @PostMapping
    public ResponseEntity<TransactionTypeDtoBase> create(@RequestBody TransactionTypeDtoBase transactionTypeDtoBase) {
        TransactionType saved = transactionTypeService.save(transactionTypeDtoBase);
        TransactionTypeDtoBase savedTransactionType = modelMapper.map(saved, TransactionTypeDtoBase.class);
        return new ResponseEntity<>(savedTransactionType, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<TransactionTypeDtoBase>> getAll() {
        Set<TransactionType> TransactionTypes = transactionTypeService.getAll();
        Set<TransactionTypeDtoBase> TransactionTypeDtoBases = TransactionTypes.stream()
                .map(transactionType -> modelMapper.map(transactionType, TransactionTypeDtoBase.class))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(TransactionTypeDtoBases, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionTypeDtoFull> getById(@PathVariable("id") Long id) {
        TransactionType transactionType = transactionTypeService.getById(id);
        TransactionTypeDtoFull TransactionTypeDtoFull = modelMapper.map(transactionType, TransactionTypeDtoFull.class);
        return new ResponseEntity<>(TransactionTypeDtoFull, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransactionTypeDtoFull> updateProduct(@PathVariable("id") Long id, @RequestBody TransactionTypeDtoBase transactionType) {
        if (!Objects.equals(id, transactionType.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TransactionType updatedTransactionType = transactionTypeService.update(transactionType);
        TransactionTypeDtoFull TransactionTypeDtoFull = modelMapper.map(updatedTransactionType, TransactionTypeDtoFull.class);
        return new ResponseEntity<>(TransactionTypeDtoFull, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        if (transactionTypeService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

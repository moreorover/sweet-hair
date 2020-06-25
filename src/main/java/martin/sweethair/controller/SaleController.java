package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.SaleDtoBase;
import martin.sweethair.dto.base.SaleProductDtoBase;
import martin.sweethair.dto.full.SaleDtoFull;
import martin.sweethair.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/sale")
@AllArgsConstructor
@Slf4j
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleDtoFull> createSale(@RequestBody SaleDtoFull saleDtoFull) {
        SaleDtoFull save = saleService.save(saleDtoFull);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<SaleDtoFull>> getAllSales() {
        Set<SaleDtoFull> saleDtos = saleService.getAll();
        return new ResponseEntity<>(saleDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDtoFull> getSaleById(@PathVariable("id") Long id) {
        SaleDtoFull saleById = saleService.getSaleById(id);
        return new ResponseEntity<>(saleById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SaleDtoFull> updateSale(@PathVariable("id") Long id, @RequestBody SaleDtoFull saleDto) {
        if (!Objects.equals(id, saleDto.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SaleDtoFull updatedSale = saleService.updateSale(saleDto);
        return new ResponseEntity<>(updatedSale, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable("id") Long id) {
        saleService.deleteSaleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

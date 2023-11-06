package ma.nemo.assignment.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.service.SupplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supply")
@RequiredArgsConstructor
@Slf4j
public class SupplyController {
    private final SupplyService supplyService;
    @PostMapping
    public ResponseEntity<?> addProductToInventory(@Valid @RequestBody SupplyDto supplyDTO) {
        log.info("Create a new Supply Transaction: {}", supplyDTO);

        try {
            SupplyDto result = supplyService.addProductToInventory(supplyDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ProductNotFound e) {
            log.error("Product not found: {}", e.getMessage());
            return new ResponseEntity<>("Product not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
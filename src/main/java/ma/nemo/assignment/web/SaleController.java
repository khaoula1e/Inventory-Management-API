package ma.nemo.assignment.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.dto.SaleDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductQuantityNotInStock;
import ma.nemo.assignment.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleController.class);

    @PostMapping
    public ResponseEntity<?> addSale(@Valid @RequestBody SaleDto saleDTO) {
        try {
            LOGGER.info("New Sale for product: " + saleDTO.getProductCode());
            SaleDto result = saleService.addSale(saleDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (ProductNotFound e) {
            LOGGER.error("Product not found: " + e.getMessage());
            return new ResponseEntity<>("Product not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ProductQuantityNotInStock e) {
            LOGGER.error("Product quantity not in stock: " + e.getMessage());
            return new ResponseEntity<>("Product quantity not in stock: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}


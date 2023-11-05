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
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplyController.class);

    @PostMapping
    public ResponseEntity<?> addSale(@Valid @RequestBody SaleDto saleDTO) throws ProductNotFound, ProductQuantityNotInStock {
        LOGGER.info("New Sale for product : " + saleDTO.getProductCode());
        return new ResponseEntity<>(saleService.addSale(saleDTO), HttpStatus.CREATED);
    }


}

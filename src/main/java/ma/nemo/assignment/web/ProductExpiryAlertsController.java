package ma.nemo.assignment.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.service.SupplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expiry-alerts")
@RequiredArgsConstructor
@Slf4j
public class ProductExpiryAlertsController {
    private final SupplyService supplyService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductExpiryAlertsController.class);


    @GetMapping
    public ResponseEntity<List<ProductDto>> listProductsNearingExpiryDate(@RequestParam int thresholdDays){
        LOGGER.info("Listing products nearing their expiry date");
        List<ProductDto> productsNearingExpiryDate = supplyService.getProductsNearingExpiryDate(thresholdDays);
        return new ResponseEntity<>(productsNearingExpiryDate, HttpStatus.OK);
    }
}

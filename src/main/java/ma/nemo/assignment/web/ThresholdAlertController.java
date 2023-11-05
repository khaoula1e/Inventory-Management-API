package ma.nemo.assignment.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.dto.ThresholdRequest;
import ma.nemo.assignment.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ThresholdAlertController {
    private final ProductService productService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ThresholdAlertController.class);

    @PostMapping("/set-threshold")
    public ResponseEntity<ProductDto> setProductThreshold(@Valid @RequestBody ThresholdRequest request) {
        LOGGER.info("Setting threshold for product with code: {}", request.getProductCode());

        try {
            ProductDto updatedProduct = productService.setProductThreshold(request.getProductCode(), request.getThresholdQuantity());
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/threshold-alerts")
    public ResponseEntity<List<ProductDto>> getProductsBelowThreshold() {
        LOGGER.info("Listing products below their threshold");
        List<ProductDto> productsBelowThreshold = productService.getProductsBelowThreshold();

        if (productsBelowThreshold.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productsBelowThreshold, HttpStatus.OK);
    }
}

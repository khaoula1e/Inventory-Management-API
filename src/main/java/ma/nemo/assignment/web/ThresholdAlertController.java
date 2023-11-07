package ma.nemo.assignment.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.dto.ThresholdRequest;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.service.ProductService;
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

    @PostMapping("/set-threshold")
    public ResponseEntity<ProductDto> setProductThreshold(@Valid @RequestBody ThresholdRequest request) {
        log.info("Setting threshold for product with code: {}", request.getProductCode());

        try {
            ProductDto updatedProduct = productService.setProductThreshold(request.getProductCode(), request.getThresholdQuantity());
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFound e) {
            log.error("Product not found: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error while setting threshold: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/threshold-alerts")
    public ResponseEntity<List<ProductDto>> getAllProductBelowQuantityThreshold(){
        log.info("Getting list of product below quantity threshold");
        return new ResponseEntity<List<ProductDto>>(productService.getProductsBelowThreshold(), HttpStatus.OK);
    }


}

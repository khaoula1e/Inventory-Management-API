package ma.nemo.assignment.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.dto.ReturnDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.service.ReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/return")
@RequiredArgsConstructor
public class ReturnController {
    @Autowired
    private ReturnService returnService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnController.class);

    @PostMapping
    public ResponseEntity<?> returnProduct(@Valid @RequestBody ReturnDto returnProductDTO) {
        try {
            LOGGER.info("Return Product to stock: " + returnProductDTO.getProductCode());
            ReturnDto returnResult = returnService.returnProduct(returnProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(returnResult);
        } catch (ProductNotFound ex) {
            LOGGER.error("Product not found: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("An error occurred: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

}

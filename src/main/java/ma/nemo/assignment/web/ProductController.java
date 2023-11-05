package ma.nemo.assignment.web;

import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.exceptions.*;
import ma.nemo.assignment.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final ModelMapper modelMapper;


    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
        LOGGER.info("Creating product: {}", productDto);

        try {
            Product createdProduct = productService.createProduct(productDto);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (ProductAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Product with code: " + productDto.getProductCode() + " already exists");
        } catch (ProductValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + e.getMessage());
        }
    }



    @GetMapping("/list")
    public ResponseEntity<List<ProductDto>> listProducts() {
        LOGGER.info("Listing products");
        List<ProductDto> products = productService.getListProducts();

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) throws ProductNotFound{
        LOGGER.info("Getting product with id "+id);
        ProductDto productDto = productService.getProductById(id);
        return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                LOGGER.info("Product with id {} deleted", id);
                return new ResponseEntity<>("Product with id " + id + " deleted.", HttpStatus.NO_CONTENT);
            } else {
                LOGGER.error("Product with id {} not found for deletion", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id " + id + " not found for deletion.");
            }
        } catch (ProductNotFound e) {
            LOGGER.error("Product with id {} not found for deletion", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id " + id + " not found for deletion.");
        }
    }
}

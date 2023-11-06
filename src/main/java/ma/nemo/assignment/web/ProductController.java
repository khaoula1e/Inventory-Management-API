package ma.nemo.assignment.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.exceptions.*;
import ma.nemo.assignment.mapper.ProductMapper;
import ma.nemo.assignment.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;


    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("Creating product: {}", productDto);
        try {
            Product createdProduct = productService.createProduct(productDto);
            ProductDto createdProductDto = productMapper.toDTO(createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
        } catch (ProductAlreadyExists e) {
            log.error("Product already exists: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (ProductValidationException e) {
            log.error("Product validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/list")
    public ResponseEntity<?> listProducts() {
        log.info("Listing products");
        List<ProductDto> products = productService.getListProducts();

        if (products.isEmpty()) {
            log.info("Products list is empty");
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            log.info("Getting product with id " + id);
            ProductDto productDto = productService.getProductById(id);
            return ResponseEntity.ok(productDto);
        } catch (ProductNotFound e) {
            log.error("Product with id {} not found ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                log.info("Product with id {} deleted", id);
                return ResponseEntity.ok("Product with id " + id + " has been deleted successfully");
            } else {
                log.error("Product with id {} not found for deletion", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        } catch (ProductNotFound e) {
            log.error("Product with id {} not found for deletion", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
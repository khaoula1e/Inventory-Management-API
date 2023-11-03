package ma.nemo.assignment.web;

import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.exceptions.ProductAlreadyExists;
import ma.nemo.assignment.exceptions.ProductValidationException;
import ma.nemo.assignment.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/")
    public ResponseEntity<Long> createProduct(@Valid @RequestBody ProductDto productDto)
            throws ProductAlreadyExists, ProductValidationException {
        LOGGER.info("Creating product: {}", productDto);

        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productService.createProduct(product);

        return new ResponseEntity<>(savedProduct.getProductId(), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> listProducts() {
        LOGGER.info("Listing products");
        List<Product> products = productService.listProducts();

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);

        return optionalProduct
                .map(product -> {
                    LOGGER.info("Product with id {} found", id);
                    return new ResponseEntity<>(product, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);

        if (deleted) {
            LOGGER.info("Product with id {} deleted", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            LOGGER.error("Product with id {} not found for deletion", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

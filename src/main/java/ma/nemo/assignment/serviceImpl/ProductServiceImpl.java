package ma.nemo.assignment.serviceImpl;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.exceptions.ProductAlreadyExists;
import ma.nemo.assignment.exceptions.ProductValidationException;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) throws ProductAlreadyExists, ProductValidationException {
        if (productRepository.findByProductCode(product.getProductCode()) != null) {
            throw new ProductAlreadyExists("Product with code " + product.getProductCode() + " already exists");
        }
        Date currentDate = new Date();
        product.setCreationDate(currentDate);
        product.setModificationDate(currentDate);
        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }



}

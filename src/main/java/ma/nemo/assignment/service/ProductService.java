package ma.nemo.assignment.service;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.exceptions.ProductAlreadyExists;
import ma.nemo.assignment.exceptions.ProductValidationException;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product) throws ProductAlreadyExists, ProductValidationException;

    List<Product> listProducts();


    Optional<Product> getProductById(Long id);

    boolean deleteProduct(Long id);

}

package ma.nemo.assignment.service;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.exceptions.ProductAlreadyExists;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductValidationException;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductDto getProductById(Long id) throws ProductNotFound;
    ProductDto getProductByCode(String productCode) throws ProductNotFound;
    List<ProductDto> getListProducts() ;

    Product createProduct(ProductDto productDto) throws ProductAlreadyExists, ProductValidationException;

    boolean deleteProduct(Long id) throws ProductNotFound;

    List<ProductDto> getProductsBelowThreshold();

    ProductDto setProductThreshold(String productCode, int threshold) throws ProductNotFound;



}

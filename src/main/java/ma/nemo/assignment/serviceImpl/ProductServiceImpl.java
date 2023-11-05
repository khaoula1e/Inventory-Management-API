package ma.nemo.assignment.serviceImpl;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.exceptions.ProductAlreadyExists;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductValidationException;
import ma.nemo.assignment.mapper.ProductMapper;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public ProductDto getProductById(Long id) throws ProductNotFound {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            ProductDto productDto = productMapper.toDTO(product.get());
            return productDto;
        }
        throw new ProductNotFound("Product with id " + id + " wasn't found");
    }


    @Override
    public ProductDto getProductByCode(String productCode) throws ProductNotFound {
        Optional<Product> product = productRepository.findByProductCode(productCode);
        if(product.isPresent()){
            ProductDto productDto = productMapper.toDTO(product.get());
        }
        throw new ProductNotFound("Product with code " + productCode + " wasn't found");
    }

    @Override
    public List<ProductDto> getListProducts() {
        List<Product> listProducts = productRepository.findAll();
        List<ProductDto> listProductDtos = listProducts.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        return listProductDtos;
    }
    @Override
    public Product createProduct(ProductDto productDto) throws ProductAlreadyExists, ProductValidationException {
        // Vérifier si un produit avec le même code existe déjà
        Optional<Product> checkingProduct = productRepository.findByProductCode(productDto.getProductCode());
        if (checkingProduct.isPresent()) {
            throw new ProductAlreadyExists("Product with code: " + productDto.getProductCode() + " already exists");
        }

        Product newProduct = new Product();
        newProduct.setProductCode(productDto.getProductCode());
        newProduct.setProductName(productDto.getProductName());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setQuantityInStock(productDto.getQuantityInStock());
        newProduct.setUnitPrice(productDto.getUnitPrice());

        // Date courante
        LocalDateTime currentDate = LocalDateTime.now();

        // Initialiser les dates de création et de modification
        newProduct.setCreationDate(currentDate);
        newProduct.setModificationDate(currentDate);

        // Enregistrer le produit
        try {
            // Valider le produit, les exceptions de validation seront gérées globalement
            return productRepository.save(newProduct);
        } catch (DataIntegrityViolationException ex) {
            // Si une exception liée à la base de données se produit, vous pouvez la gérer ici
            throw new ProductValidationException("Failed to create the product due to a database error.");
        }
    }



    @Override
    public boolean deleteProduct(Long id) throws ProductNotFound{
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        else {
            throw new ProductNotFound("Product with id "+ id +" wasn't found");
        }
    }


}
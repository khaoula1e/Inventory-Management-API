package ma.nemo.assignment.serviceImpl;

import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.Return;
import ma.nemo.assignment.domain.Sale;
import ma.nemo.assignment.dto.ReturnDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductQuantityNotInStock;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.repository.ReturnRepository;
import ma.nemo.assignment.service.ReturnService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements ReturnService {
    private final ReturnRepository returnProductRepository;
    private final ProductRepository productRepository;

    @Override
    public ReturnDto returnProduct(ReturnDto returnProductDto) throws ProductNotFound {
        Optional<Product> productOptional = productRepository.findByProductCode(returnProductDto.getProductCode());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            if (returnProductDto.getQuantity() <= 0) {
                throw new IllegalArgumentException("La quantité de retour doit être supérieure à zéro.");
            }

            // Créer un objet Sale pour représenter le retour de produit
            Return returnedProduct = new Return();
            returnedProduct.setProduct(product);
            returnedProduct.setReturnQuantity(returnProductDto.getQuantity());
            returnedProduct.setReturnDate(LocalDateTime.now());

            returnProductRepository.save(returnedProduct);

            // Mettre à jour la quantité en stock du produit
            int newQuantityInStock = product.getQuantityInStock() + returnProductDto.getQuantity();
            product.setQuantityInStock(newQuantityInStock);

            // Mettre à jour les informations du produit
            product.setModificationDate(LocalDateTime.now());

            // Créer un objet ReturnDto pour représenter le résultat du retour
            ReturnDto returnResult = new ReturnDto();
            returnResult.setProductCode(product.getProductCode());
            returnResult.setQuantity(returnProductDto.getQuantity());
            returnResult.setReason("Le produit a été retourné avec succès.");

            return returnResult;
        } else {
            throw new ProductNotFound("Produit non trouvé");
        }
    }
}

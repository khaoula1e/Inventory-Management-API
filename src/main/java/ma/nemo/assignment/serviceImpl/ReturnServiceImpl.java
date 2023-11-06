package ma.nemo.assignment.serviceImpl;

import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.Return;
import ma.nemo.assignment.domain.TransactionHistory;
import ma.nemo.assignment.domain.util.EventType;
import ma.nemo.assignment.dto.ReturnDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.mapper.TransactionHistoryMapper;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.repository.ReturnRepository;
import ma.nemo.assignment.service.ReturnService;
import ma.nemo.assignment.service.TransactionHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements ReturnService {
    private final ReturnRepository returnProductRepository;
    private final ProductRepository productRepository;
    private final TransactionHistoryService transactionHistoryService;
    private final TransactionHistoryMapper transactionMapper;

    @Override
    @Transactional
    public ReturnDto returnProduct(ReturnDto returnProductDto) throws ProductNotFound {
        Optional<Product> productOptional = productRepository.findByProductCode(returnProductDto.getProductCode());

        if (!productOptional.isPresent()) {
            throw new ProductNotFound("Produit non trouvé");
        }

        Product product = productOptional.get();

        if (returnProductDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("La quantité de retour doit être supérieure à zéro.");
        }

        // Créer un objet Return pour représenter le retour de produit
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

        // Créer un objet TransactionHistory pour enregistrer la transaction
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setProduct(product);
        transactionHistory.setQuantity(returnProductDto.getQuantity());
        transactionHistory.setTransactionType(EventType.RETURN);
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistory.setUser(null);

        transactionHistoryService.addTransaction(transactionMapper.toDTO(transactionHistory));

        // Créer un objet ReturnDto pour représenter le résultat du retour
        ReturnDto returnResult = new ReturnDto();
        returnResult.setProductCode(product.getProductCode());
        returnResult.setQuantity(returnProductDto.getQuantity());
        returnResult.setReason("Le produit a été retourné avec succès.");

        return returnResult;
    }
}

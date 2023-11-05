package ma.nemo.assignment.serviceImpl;

import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.Sale;
import ma.nemo.assignment.domain.TransactionHistory;
import ma.nemo.assignment.domain.util.EventType;
import ma.nemo.assignment.dto.SaleDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductQuantityNotInStock;
import ma.nemo.assignment.mapper.TransactionHistoryMapper;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.repository.SaleRepository;
import ma.nemo.assignment.service.SaleService;
import ma.nemo.assignment.service.TransactionHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final TransactionHistoryService transactionHistoryService;
    private final TransactionHistoryMapper transactionMapper;

    @Override
    @Transactional
    public SaleDto addSale(SaleDto saleDto) throws ProductNotFound, ProductQuantityNotInStock {
        // Recherche du produit en fonction du code du produit dans le SaleDto
        Optional<Product> productOptional = productRepository.findByProductCode(saleDto.getProductCode());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Vérification de la quantité en stock
            if (product.getQuantityInStock() >= saleDto.getQuantity()) {
                // Création de l'entité Sale
                Sale sale = new Sale();
                sale.setProduct(product);
                sale.setSoldQuantity(saleDto.getQuantity());
                sale.setTotalPrice(product.getUnitPrice() * saleDto.getQuantity());
                sale.setSaleDate(LocalDateTime.now());

                // Enregistrement de la vente dans la base de données
                saleRepository.save(sale);

                // Mise à jour de la quantité en stock du produit
                int newQuantityInStock = product.getQuantityInStock() - saleDto.getQuantity();
                product.setQuantityInStock(newQuantityInStock);

                // Mise à jour des informations du produit
                product.setModificationDate(LocalDateTime.now());
                productRepository.save(product);

                TransactionHistory transactionHistory = new TransactionHistory();
                transactionHistory.setProduct(product);
                transactionHistory.setQuantity(saleDto.getQuantity());
                transactionHistory.setTransactionType(EventType.SALE);
                transactionHistory.setTransactionDate(LocalDateTime.now());
                transactionHistory.setUser(null);
                transactionHistoryService.addTransaction(transactionMapper.toDTO(transactionHistory));
            } else {
                throw new ProductQuantityNotInStock("Not enough quantity in stock for the product.");
            }
        } else {
            throw new ProductNotFound("Product with code " + saleDto.getProductCode() + " not found.");
        }

        return saleDto;
    }
}

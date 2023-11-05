package ma.nemo.assignment.serviceImpl;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.Supply;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.repository.SupplyRepository;
import ma.nemo.assignment.service.SupplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SupplyServiceImpl implements SupplyService {
    private final SupplyRepository supplyRepository;
    private final ProductRepository productRepository;

    public SupplyServiceImpl(SupplyRepository supplyRepository, ProductRepository productRepository) {
        this.supplyRepository = supplyRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional // Utilisez la transaction pour garantir l'intégrité des données
    public SupplyDto addProductToInventory(SupplyDto supplyDTO) throws ProductNotFound {
        Optional<Product> productOptional = productRepository.findByProductCode(supplyDTO.getProductCode());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            Supply supply = new Supply();
            supply.setProduct(product);
            supply.setQuantity(supplyDTO.getQuantity());
            supply.setExpirationDate(supplyDTO.getExpirationDate());
            supply.setSupplyDate(LocalDateTime.now());

            supplyRepository.save(supply);

            // Mettre à jour la quantité en stock du produit
            int newQuantityInStock = product.getQuantityInStock() + supplyDTO.getQuantity();
            product.setQuantityInStock(newQuantityInStock);
            product.setModificationDate(LocalDateTime.now());

            productRepository.save(product);

            return supplyDTO;
        } else {
            throw new ProductNotFound("Product with code " + supplyDTO.getProductCode() + " wasn't found");
        }
    }
}

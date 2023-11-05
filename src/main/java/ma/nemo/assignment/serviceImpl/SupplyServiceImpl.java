package ma.nemo.assignment.serviceImpl;

import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.domain.Supply;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.mapper.ProductMapper;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.repository.SupplyRepository;
import ma.nemo.assignment.service.SupplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyServiceImpl implements SupplyService {
    private final SupplyRepository supplyRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional // Using a transaction to ensure data integrity
    public SupplyDto addProductToInventory(SupplyDto supplyDto) throws ProductNotFound {
        Optional<Product> productOptional = productRepository.findByProductCode(supplyDto.getProductCode());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            Supply supply = new Supply();
            supply.setProduct(product);
            supply.setQuantity(supplyDto.getQuantity());
            supply.setExpirationDate(supplyDto.getExpirationDate());
            supply.setSupplyDate(LocalDateTime.now());

            supplyRepository.save(supply);

            // Updating the quantity in stock of the product
            int newQuantityInStock = product.getQuantityInStock() + supplyDto.getQuantity();
            product.setQuantityInStock(newQuantityInStock);
            product.setModificationDate(LocalDateTime.now());

            productRepository.save(product);

            return supplyDto;
        } else {
            throw new ProductNotFound("Product with code " + supplyDto.getProductCode() + " wasn't found");
        }
    }

    @Override
    public List<ProductDto> getProductsNearingExpiryDate(int thresholdDays) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime thresholdEndTime = currentDate.plusDays(thresholdDays)
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
        List<Supply> supplies = supplyRepository.findByExpirationDateBetween(currentDate, thresholdEndTime);

        return supplies.stream()
                .map(supply -> productMapper.toDTO(supply.getProduct()))
                .collect(Collectors.toList());
    }

}

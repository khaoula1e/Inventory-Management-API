package ma.nemo.assignment.service;

import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.exceptions.ProductNotFound;

import java.util.List;

public interface SupplyService {
    SupplyDto addProductToInventory(SupplyDto supplyDTO) throws ProductNotFound;

    List<ProductDto> getProductsNearingExpiryDate(int thresholdDays);

}

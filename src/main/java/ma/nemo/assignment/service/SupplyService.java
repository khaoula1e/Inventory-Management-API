package ma.nemo.assignment.service;

import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.exceptions.ProductNotFound;

public interface SupplyService {
    SupplyDto addProductToInventory(SupplyDto supplyDTO) throws ProductNotFound;

}

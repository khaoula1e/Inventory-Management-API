package ma.nemo.assignment.service;

import ma.nemo.assignment.dto.SaleDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.exceptions.ProductQuantityNotInStock;
import org.springframework.stereotype.Service;

@Service
public interface SaleService {
    public SaleDto addSale(SaleDto saleDto) throws ProductNotFound, ProductQuantityNotInStock;
}

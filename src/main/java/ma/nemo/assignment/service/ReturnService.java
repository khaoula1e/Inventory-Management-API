package ma.nemo.assignment.service;

import ma.nemo.assignment.dto.ReturnDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import org.springframework.stereotype.Service;

@Service
public interface ReturnService {
    public ReturnDto returnProduct(ReturnDto returnProductDto) throws ProductNotFound;
}

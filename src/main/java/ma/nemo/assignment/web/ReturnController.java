package ma.nemo.assignment.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.dto.ReturnDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.service.ReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/return")
@RequiredArgsConstructor
public class ReturnController {
    @Autowired
    private ReturnService returnService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReturnController.class);

    @PostMapping
    public ResponseEntity<?> returnProduct(@Valid @RequestBody ReturnDto returnProductDTO) throws ProductNotFound {
        LOGGER.info("Return Product to stock : " + returnProductDTO.getProductCode());
        return new ResponseEntity<>(returnService.returnProduct(returnProductDTO), HttpStatus.CREATED);
    }
}

package ma.nemo.assignment.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.exceptions.ProductNotFound;
import ma.nemo.assignment.service.SupplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supply")
@RequiredArgsConstructor
@Slf4j
public class SupplyController {
    private final SupplyService supplyService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplyController.class);
    @PostMapping
    public ResponseEntity<SupplyDto> addProductToInventory(@Valid @RequestBody SupplyDto supplyDTO) throws ProductNotFound {
        LOGGER.info("Create a new Supply Transaction : ", supplyDTO);
        return  new ResponseEntity<>(supplyService.addProductToInventory(supplyDTO), HttpStatus.CREATED);
    }
}
package ma.nemo.assignment.Controller;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.dto.SupplyDto;
import ma.nemo.assignment.mapper.ProductMapper;
import ma.nemo.assignment.mapper.SupplyMapper;
import ma.nemo.assignment.service.SupplyService;
import ma.nemo.assignment.web.ProductExpiryAlertsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductExpiryAlertsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SupplyService supplyService;
    @Autowired
    private SupplyMapper supplyMapper;
    @Autowired
    private ProductMapper productMapper;


    @InjectMocks
    private ProductExpiryAlertsController productExpiryAlertsController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productExpiryAlertsController).build();
    }

    @Test
    public void testListProductsNearExpiryDate() throws Exception {
        int thresholdDays = 10;
        List<ProductDto> products = new ArrayList<>();
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setProductCode("TEST1");
        supplyDto.setQuantity(5);
        supplyDto.setExpirationDate(LocalDateTime.parse("2023-11-30T14:30:00"));
        Product product_1= supplyMapper.toEntity(supplyDto).getProduct();
        SupplyDto supplyDto2 = new SupplyDto();
        supplyDto2.setProductCode("TEST2");
        supplyDto2.setQuantity(5);
        supplyDto2.setExpirationDate(LocalDateTime.parse("2023-11-20T14:30:00"));
        Product product_2= supplyMapper.toEntity(supplyDto2).getProduct();
        products.add(productMapper.toDTO(product_1));
        products.add(productMapper.toDTO(product_2));



        Mockito.when(supplyService.getProductsNearingExpiryDate(thresholdDays)).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/expiry-alerts")
                        .param("thresholdDays", String.valueOf(thresholdDays)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testListProductsNearExpiryDateEmptyList() throws Exception {
        int thresholdDays = 14;
        List<ProductDto> emptyList = new ArrayList<>();

        Mockito.when(supplyService.getProductsNearingExpiryDate(thresholdDays)).thenReturn(emptyList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/expiry-alerts")
                        .param("thresholdDays", String.valueOf(thresholdDays)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

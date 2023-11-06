package ma.nemo.assignment.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import ma.nemo.assignment.domain.Product;
import ma.nemo.assignment.dto.ProductDto;
import ma.nemo.assignment.mapper.ProductMapper;
import ma.nemo.assignment.repository.ProductRepository;
import ma.nemo.assignment.service.ProductService;
import ma.nemo.assignment.web.ProductController;
import ma.nemo.assignment.web.common.ExceptionHandlingController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new ExceptionHandlingController())
                .build();
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setProductCode("Product 1");
        productDto.setProductName("Product Test");
        productDto.setDescription("This is to test the creation");
        productDto.setUnitPrice(20.0);
        productDto.setQuantityInStock(100);
        productDto.setThresholdQuantity(50);

        // Mock the productService behavior
        Mockito.when(productService.createProduct(productDto))
                .thenReturn(new Product());

        // Mock the productMapper behavior
        Mockito.when(productMapper.toDTO(Mockito.any(Product.class)))
                .thenReturn(productDto);

        // Convert the productDto to a JSON string
        String json = "{\"productCode\":\"Product 1\",\"productName\":\"Product Test\",\"description\":\"This is to test the creation\",\"unitPrice\":20.0,\"quantityInStock\":100,\"thresholdQuantity\":50}";

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName", is("Product Test")));
    }


    @Test
    void testGetListProducts() throws Exception {
        // Créez une liste simulée de produits
        List<ProductDto> products = new ArrayList<>();
        ProductDto product1 = new ProductDto();
        product1.setProductName("Product 1");
        product1.setProductCode("TEST1");
        product1.setDescription("Test description");
        product1.setUnitPrice(16.0);
        product1.setQuantityInStock(100);
        product1.setThresholdQuantity(100);

        ProductDto product2 = new ProductDto();
        product2.setProductName("Product 2");
        product2.setProductCode("TEST2");
        product2.setDescription("Test description for product 2");
        product2.setUnitPrice(16.0);
        product2.setQuantityInStock(200);
        product2.setThresholdQuantity(150);

        products.add(product1);
        products.add(product2);


        // Mockez le comportement de productService.getListProducts() pour renvoyer la liste simulée de ProductDto
        Mockito.when(productService.getListProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productName", is("Product 1")))
                .andExpect(jsonPath("$[1].productName", is("Product 2")));
    }

    @Test
    void testGetProductById() throws Exception {
        Long productId = 1L;

        // Create a test productDto
        ProductDto product = new ProductDto();
        product.setProductName("Product 1");
        product.setProductCode("TEST1");
        product.setDescription("Test description");
        product.setUnitPrice(16.0);
        product.setQuantityInStock(100);
        product.setThresholdQuantity(10);

        // Mock the productService behavior
        Mockito.when(productService.getProductById(productId))
                .thenReturn(product);

        mockMvc.perform(get("/api/products/" + productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName", is("Product 1")));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Long productId = 1L;

        // Mock the productService behavior to return false (indicating that the product was not found and not deleted)
        Mockito.when(productService.deleteProduct(productId)).thenReturn(false);

        mockMvc.perform(delete("/api/products/" + productId))
                .andExpect(status().isNotFound());
    }
}

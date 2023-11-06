package ma.nemo.assignment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
  private Long productId;

  @NotBlank(message = "Product code is required")
  @Size(min = 3, max = 10, message = "Product code length must be between 3 and 10 characters")
  private String productCode;

  @NotBlank(message = "Product name is required")
  private String productName;

  private String description;

  @NotNull(message = "Unit price is required")
  @DecimalMin(value = "0.01", message = "Unit price must be greater than or equal to 0.01")
  private Double unitPrice;

  @NotNull(message = "Quantity in stock is required")
  @DecimalMin(value = "1", message = "Quantity in stock must be greater than or equal to 1")
  private Integer quantityInStock;

  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;

  @Min(value = 1, message = "Threshold quantity must be at least 1")
  private Integer thresholdQuantity;

}

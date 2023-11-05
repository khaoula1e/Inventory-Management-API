package ma.nemo.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.DecimalMax;
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

  @DecimalMin(value = "0.01", message = "Unit price must be greater than or equal to 0.01")
  private Double unitPrice;

  @DecimalMin(value = "1", message = "Quantity in stock must be greater than or equal to 1")
  private Integer quantityInStock;

  private LocalDateTime creationDate;
  private LocalDateTime modificationDate;
}

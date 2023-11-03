package ma.nemo.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto {
  private Long productId;
  private String productCode;
  private String productName;

  private String description;
  private Double unitPrice;
  private Integer quantityInStock;
  private Date creationDate;
  private Date modificationDate;


}

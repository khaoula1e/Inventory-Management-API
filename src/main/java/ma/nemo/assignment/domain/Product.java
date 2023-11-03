package ma.nemo.assignment.domain;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
@ToString
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @Column(unique = true, nullable = false)
  @NotBlank(message = "Product code is required")
  private String productCode;

  @Column(nullable = false)
  @NotBlank(message = "Product name is required")
  private String productName;

  private String description;

  private Double unitPrice;

  private Integer quantityInStock;

  @Temporal(TemporalType.TIMESTAMP)
  private Date creationDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date modificationDate;
}

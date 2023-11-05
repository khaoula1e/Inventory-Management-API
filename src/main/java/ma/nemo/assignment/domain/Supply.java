package ma.nemo.assignment.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Supplies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supply {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long supplyId;

  @ManyToOne
  @JoinColumn(name = "productId")
  private Product product;

  private Integer quantity;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime supplyDate;

  @Column(columnDefinition = "TIMESTAMP")
  @Basic(optional = true) // Cette annotation indique que le champ est optionnel
  private LocalDateTime expirationDate;


}

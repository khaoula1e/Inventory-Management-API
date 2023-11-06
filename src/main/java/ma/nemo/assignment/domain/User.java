package ma.nemo.assignment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "Users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  private String username;

  private String role;

  @Temporal(TemporalType.TIMESTAMP)
  private Date creationDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLoginDate;


}

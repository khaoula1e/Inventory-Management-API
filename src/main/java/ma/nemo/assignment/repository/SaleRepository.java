package ma.nemo.assignment.repository;

import ma.nemo.assignment.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}

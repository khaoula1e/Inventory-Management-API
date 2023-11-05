package ma.nemo.assignment.repository;

import ma.nemo.assignment.domain.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findByExpirationDateBetween(LocalDateTime start, LocalDateTime end);

}

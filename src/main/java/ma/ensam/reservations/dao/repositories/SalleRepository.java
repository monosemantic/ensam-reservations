package ma.ensam.reservations.dao.repositories;

import ma.ensam.reservations.dao.entities.Salle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle, Long> {
  boolean existsByNomIgnoreCase(String nom);

  boolean existsByDepartementId(Long departementId);

  Page<Salle> findByNomContainsIgnoreCase(String keyword, Pageable pageable);
}

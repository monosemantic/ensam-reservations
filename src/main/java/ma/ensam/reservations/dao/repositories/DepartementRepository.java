package ma.ensam.reservations.dao.repositories;

import ma.ensam.reservations.dao.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
  boolean existsByNomIgnoreCase(String nom);
}

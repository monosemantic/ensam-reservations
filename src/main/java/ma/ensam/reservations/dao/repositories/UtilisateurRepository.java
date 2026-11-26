package ma.ensam.reservations.dao.repositories;

import ma.ensam.reservations.dao.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
  boolean existsByEmailIgnoreCase(String email);

  List<Utilisateur> findByEmail(String email);
}

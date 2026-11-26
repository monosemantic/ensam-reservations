package ma.ensam.reservations.dao.repositories;

import ma.ensam.reservations.dao.entities.Reservation;
import ma.ensam.reservations.dao.entities.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  boolean existsBySalleId(Long salleId);

  List<Reservation> findByUtilisateurId(Long utilisateurId);

  @Query("""
      SELECT COUNT(r) > 0 FROM Reservation r
      WHERE r.salle.id = :salleId
        AND r.date = :date
        AND r.statut <> :statutRefusee
        AND r.heureDebut < :heureFin
        AND r.heureFin > :heureDebut
      """)
  boolean existsOverlappingReservation(
      @Param("salleId") Long salleId,
      @Param("date") LocalDate date,
      @Param("heureDebut") LocalTime heureDebut,
      @Param("heureFin") LocalTime heureFin,
      @Param("statutRefusee") StatutReservation statutRefusee);
}

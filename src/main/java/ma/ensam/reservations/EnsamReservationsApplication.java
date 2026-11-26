package ma.ensam.reservations;

import ma.ensam.reservations.dao.entities.*;
import ma.ensam.reservations.dao.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class EnsamReservationsApplication {

  public static void main(String[] args) {
    SpringApplication.run(EnsamReservationsApplication.class, args);
  }

  @Bean
  CommandLineRunner initDatabase(
      DepartementRepository departementRepository,
      SalleRepository salleRepository,
      UtilisateurRepository utilisateurRepository,
      ReservationRepository reservationRepository) {
    return args -> {
      if (departementRepository.count() == 0) {
        Departement info = new Departement(null, "Génie Informatique", null);
        info = departementRepository.save(info);

        Salle amphiA = new Salle(null, "Amphi A", 120, TypeSalle.AMPHI, info, null);
        Salle tp1 = new Salle(null, "Salle TP 1", 30, TypeSalle.SALLE_TP, info, null);
        amphiA = salleRepository.save(amphiA);
        salleRepository.save(tp1);

        Utilisateur admin = new Utilisateur(null, "Hssain", "Sara", "sara.admin@ensam.ma", Role.ADMIN, null);
        Utilisateur prof = new Utilisateur(null, "Alami", "Youssef", "y.alami@ensam.ma", Role.ENSEIGNANT, null);
        utilisateurRepository.save(admin);
        prof = utilisateurRepository.save(prof);

        Reservation res = new Reservation(
            null,
            LocalDate.now().plusDays(1),
            LocalTime.of(9, 0),
            LocalTime.of(11, 0),
            "TP Spring Data JPA",
            StatutReservation.EN_ATTENTE,
            amphiA,
            prof);
        reservationRepository.save(res);
      }

      System.out.println("=== DB Counts ===");
      System.out.println("Departements: " + departementRepository.count());
      System.out.println("Salles: " + salleRepository.count());
      System.out.println("Utilisateurs: " + utilisateurRepository.count());
      System.out.println("Reservations: " + reservationRepository.count());
    };
  }
}

package ma.ensam.reservations.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Column(name = "date_reservation", nullable = false)
  private LocalDate date;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  @Column(name = "heure_debut", nullable = false)
  private LocalTime heureDebut;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  @Column(name = "heure_fin", nullable = false)
  private LocalTime heureFin;

  @NotEmpty
  @Size(max = 255)
  @Column(name = "motif", length = 255, nullable = false)
  private String motif;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "statut", length = 20, nullable = false)
  private StatutReservation statut = StatutReservation.EN_ATTENTE;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "salle_id", nullable = false)
  private Salle salle;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "utilisateur_id", nullable = false)
  private Utilisateur utilisateur;
}

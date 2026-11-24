package ma.ensam.reservations.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Size(max = 50)
  @Column(name = "nom", length = 50, nullable = false)
  private String nom;

  @NotEmpty
  @Size(max = 50)
  @Column(name = "prenom", length = 50, nullable = false)
  private String prenom;

  @NotEmpty
  @Email
  @Size(max = 100)
  @Column(name = "email", length = 100, nullable = false, unique = true)
  private String email;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "role", length = 20, nullable = false)
  private Role role;

  @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
  @ToString.Exclude
  private List<Reservation> reservations = new ArrayList<>();
}

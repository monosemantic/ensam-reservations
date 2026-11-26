package ma.ensam.reservations.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salle {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  @Size(max = 50)
  @Column(name = "nom", length = 50, nullable = false, unique = true)
  private String nom;

  @NotNull
  @Min(1)
  @Column(name = "capacite", nullable = false)
  private Integer capacite;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type", length = 20, nullable = false)
  private TypeSalle type;

  @ManyToOne(optional = false)
  @JoinColumn(name = "departement_id", nullable = false)
  private Departement departement;

  @OneToMany(mappedBy = "salle", fetch = FetchType.LAZY)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Reservation> reservations = new ArrayList<>();
}

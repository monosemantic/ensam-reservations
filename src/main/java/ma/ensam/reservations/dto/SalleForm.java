package ma.ensam.reservations.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ma.ensam.reservations.dao.entities.TypeSalle;

@Data
public class SalleForm {
  private Long id;

  @NotEmpty(message = "Le nom de la salle est obligatoire")
  @Size(max = 50, message = "50 caractères maximum")
  private String nom;

  @NotNull(message = "La capacité est obligatoire")
  @Min(value = 1, message = "La capacité doit être strictement positive")
  private Integer capacite;

  @NotNull(message = "Le type est obligatoire")
  private TypeSalle type;

  @NotNull(message = "Le département est obligatoire")
  private Long departementId;
}

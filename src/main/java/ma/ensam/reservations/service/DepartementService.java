package ma.ensam.reservations.service;

import ma.ensam.reservations.dao.entities.Departement;
import java.util.List;

public interface DepartementService {
  List<Departement> getAllDepartements();

  Departement getDepartementById(Long id);

  boolean nomDejaUtilise(String nom, Long id);

  Departement saveDepartement(Departement departement);

  void deleteDepartement(Long id);
}

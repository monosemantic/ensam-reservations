package ma.ensam.reservations.service;

import java.util.List;
import ma.ensam.reservations.dao.entities.Salle;
import ma.ensam.reservations.dto.SalleForm;

public interface SalleService {
  List<Salle> getAllSalles();

  Salle getSalleById(Long id);

  SalleForm getSalleForm(Long id);

  boolean nomDejaUtilise(String nom, Long id);

  Salle saveSalle(SalleForm form);

  void deleteSalle(Long id);
}

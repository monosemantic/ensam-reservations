package ma.ensam.reservations.service;

import java.util.List;
import ma.ensam.reservations.dto.SalleForm;
import ma.ensam.reservations.dao.entities.Salle;
import ma.ensam.reservations.dao.repositories.SalleRepository;
import ma.ensam.reservations.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import ma.ensam.reservations.dao.repositories.ReservationRepository;
import ma.ensam.reservations.exception.OperationInterditeException;
import org.springframework.stereotype.Service;

@Service
public class SalleManager implements SalleService {

  @Autowired
  private SalleRepository salleRepository;
  @Autowired
  private ReservationRepository reservationRepository;
  @Autowired
  private DepartementService departementService;

  @Override
  public List<Salle> getAllSalles() {
    return salleRepository.findAll();
  }

  @Override
  public Salle getSalleById(Long id) {
    return salleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Salle introuvable (id=" + id + ")"));
  }

  @Override
  public SalleForm getSalleForm(Long id) {
    Salle s = getSalleById(id);
    SalleForm f = new SalleForm();
    f.setId(s.getId());
    f.setNom(s.getNom());
    f.setCapacite(s.getCapacite());
    f.setType(s.getType());
    f.setDepartementId(s.getDepartement().getId());
    return f;
  }

  @Override
  public boolean nomDejaUtilise(String nom, Long id) {
    return id == null ? salleRepository.existsByNomIgnoreCase(nom)
        : salleRepository.existsByNomIgnoreCaseAndIdNot(nom, id);
  }

  @Override
  public Salle saveSalle(SalleForm f) {
    Salle s = (f.getId() == null) ? new Salle() : getSalleById(f.getId());
    s.setNom(f.getNom());
    s.setCapacite(f.getCapacite());
    s.setType(f.getType());
    s.setDepartement(departementService.getDepartementById(f.getDepartementId()));
    return salleRepository.save(s);
  }

  @Override
  public void deleteSalle(Long id) {
    if (reservationRepository.existsBySalleId(id)) {
      throw new OperationInterditeException("Impossible de supprimer : cette salle a des réservations.");
    }
    salleRepository.deleteById(id);
  }
}

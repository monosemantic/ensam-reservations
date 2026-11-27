package ma.ensam.reservations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.ensam.reservations.dao.entities.Departement;

import ma.ensam.reservations.dao.repositories.DepartementRepository;
import ma.ensam.reservations.dao.repositories.SalleRepository;

import ma.ensam.reservations.exception.ResourceNotFoundException;
import ma.ensam.reservations.exception.OperationInterditeException;

import java.util.List;

@Service
public class DepartementManager implements DepartementService {

  @Autowired
  private DepartementRepository departementRepository;
  @Autowired
  private SalleRepository salleRepository;

  @Override
  public List<Departement> getAllDepartements() {
    return departementRepository.findAll();
  }

  @Override
  public Departement getDepartementById(Long id) {
    return departementRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Département introuvable (id=" + id + ")"));
  }

  @Override
  public boolean nomDejaUtilise(String nom, Long id) {
    return id == null ? departementRepository.existsByNomIgnoreCase(nom)
        : departementRepository.existsByNomIgnoreCaseAndIdNot(nom, id);
  }

  @Override
  public Departement saveDepartement(Departement departement) {
    return departementRepository.save(departement);
  }

  @Override
  public void deleteDepartement(Long id) {
    if (salleRepository.existsByDepartementId(id)) {
      throw new OperationInterditeException("Impossible de supprimer : ce département contient des salles.");
    }
    departementRepository.deleteById(id);
  }
}

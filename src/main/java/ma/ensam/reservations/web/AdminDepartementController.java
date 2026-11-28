package ma.ensam.reservations.web;

import ma.ensam.reservations.service.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ma.ensam.reservations.dao.entities.Departement;
import ma.ensam.reservations.exception.OperationInterditeException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/admin/departements")

public class AdminDepartementController {

  @Autowired
  private DepartementService departementService;

  @GetMapping
  public String liste(Model model) {
    model.addAttribute("listDepartements", departementService.getAllDepartements());
    return "admin/departements";
  }

  @GetMapping("/nouveau")
  public String nouveau(Model model) {
    model.addAttribute("departement", new Departement());
    return "admin/departement-form";
  }

  @GetMapping("/{id}/modifier")
  public String modifier(@PathVariable Long id, Model model) {
    model.addAttribute("departement", departementService.getDepartementById(id));
    return "admin/departement-form";
  }

  @PostMapping("/save")
  public String save(@Valid @ModelAttribute("departement") Departement departement,
      BindingResult result, RedirectAttributes ra) {
    if (!result.hasFieldErrors("nom")
        && departementService.nomDejaUtilise(departement.getNom(), departement.getId())) {
      result.rejectValue("nom", "doublon", "Un département avec ce nom existe déjà");
    }
    if (result.hasErrors()) {
      return "admin/departement-form"; // on réaffiche le formulaire avec les erreurs
    }
    departementService.saveDepartement(departement);
    ra.addFlashAttribute("succes", "Département enregistré.");
    return "redirect:/admin/departements";
  }

  @PostMapping("/{id}/supprimer")
  public String supprimer(@PathVariable Long id, RedirectAttributes ra) {
    try {
      departementService.deleteDepartement(id);
      ra.addFlashAttribute("succes", "Département supprimé.");
    } catch (OperationInterditeException e) {
      ra.addFlashAttribute("erreur", e.getMessage());
    }
    return "redirect:/admin/departements";
  }
}

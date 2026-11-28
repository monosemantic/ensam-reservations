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
import ma.ensam.reservations.exception.OperationInterditeException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ma.ensam.reservations.service.SalleService;
import ma.ensam.reservations.dao.entities.TypeSalle;
import ma.ensam.reservations.dto.SalleForm;

@Controller
@RequestMapping("/admin/salles")

public class AdminSalleController {

  @Autowired
  private SalleService salleService;
  @Autowired
  private DepartementService departementService;

  private void remplirListes(Model model) {
    model.addAttribute("departements", departementService.getAllDepartements());
    model.addAttribute("types", TypeSalle.values());
  }

  @GetMapping
  public String liste(Model model) {
    model.addAttribute("listSalles", salleService.getAllSalles());
    return "admin/salles";
  }

  @GetMapping("/nouvelle")
  public String nouvelle(Model model) {
    model.addAttribute("salleForm", new SalleForm());
    remplirListes(model);
    return "admin/salle-form";
  }

  @GetMapping("/{id}/modifier")
  public String modifier(@PathVariable Long id, Model model) {
    model.addAttribute("salleForm", salleService.getSalleForm(id));
    remplirListes(model);
    return "admin/salle-form";
  }

  @PostMapping("/save")
  public String save(@Valid @ModelAttribute("salleForm") SalleForm form,
      BindingResult result, Model model, RedirectAttributes ra) {
    if (!result.hasFieldErrors("nom") && salleService.nomDejaUtilise(form.getNom(), form.getId())) {
      result.rejectValue("nom", "doublon", "Une salle avec ce nom existe déjà");
    }
    if (result.hasErrors()) {
      remplirListes(model);
      return "admin/salle-form";
    }
    salleService.saveSalle(form);
    ra.addFlashAttribute("succes", "Salle enregistrée.");
    return "redirect:/admin/salles";
  }

  @PostMapping("/{id}/supprimer")
  public String supprimer(@PathVariable Long id, RedirectAttributes ra) {
    try {
      salleService.deleteSalle(id);
      ra.addFlashAttribute("succes", "Salle supprimée.");
    } catch (OperationInterditeException e) {
      ra.addFlashAttribute("erreur", e.getMessage());
    }
    return "redirect:/admin/salles";
  }
}

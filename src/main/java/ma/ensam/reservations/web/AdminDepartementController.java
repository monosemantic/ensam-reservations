package ma.ensam.reservations.web;

import ma.ensam.reservations.service.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

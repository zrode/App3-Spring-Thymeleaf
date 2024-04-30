package com.app3.Patient.web;

import com.app3.Patient.dao.PatientRepo;
import com.app3.Patient.entities.Patient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PatientController {
    @Autowired
    private PatientRepo patientRepo;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        // on récupère la liste des patients mais sous forme de pages
        Page<Patient> pagePatients = patientRepo.findByNomContains(kw, PageRequest.of(page,size));
        // on transmet cette liste au Model que l'on nommera listePatients
        model.addAttribute("listePatients", pagePatients);
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "patients";
    }

    @GetMapping("/admin/deletePatient")
    public String delete(@RequestParam(name = "id") Long idPatient, String keyword, int page) {
        patientRepo.deleteById(idPatient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/editPatient")
    public String editPatient(Model model, @RequestParam(name = "id") Long id) {
        Patient patient = patientRepo.findById(id).get(); // on ajoute .get() pour récupérer le patient
        model.addAttribute("patient", patient);
        return "editPatient";
    }

    @GetMapping("/admin/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @PostMapping("/admin/savePatient")
    public String savePatient(@Valid Patient patient, BindingResult br) {
        if(br.hasErrors()) {
            return "formPatient";
        }
        patientRepo.save(patient);
        return "redirect:/index";
    }

}

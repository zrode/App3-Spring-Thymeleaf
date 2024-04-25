package com.app3.Patient.web;

import com.app3.Patient.dao.PatientRepo;
import com.app3.Patient.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private PatientRepo patientRepo;

    @GetMapping("/index")
    public String index(Model model) {
        // on récupère la liste des patients
        List<Patient> patients = patientRepo.findAll();
        // on transmet cette liste au Model que l'on nommera listePatients
        model.addAttribute("listePatients", patients);
        return "patients";
    }

    @GetMapping("/deletePatient")
    public String delete(@RequestParam(name = "id") Long idPatient) {
        patientRepo.deleteById(idPatient);
        return "redirect:/index";
    }
}

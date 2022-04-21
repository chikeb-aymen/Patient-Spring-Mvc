package com.achcode.patientmvc.controller;

import com.achcode.patientmvc.model.Patient;
import com.achcode.patientmvc.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {


    private PatientRepository patientRepository;


    @GetMapping(path = "/index")
    public String patients(Model model, @RequestParam(name = "page",defaultValue = "0") int page,@RequestParam(name = "size",defaultValue = "5") int size,@RequestParam(name = "keyword",defaultValue = "") String keyword){

        //List<Patient> patients = patientRepository.findAll();
        //Page<Patient> patients = patientRepository.findAll(PageRequest.of(page,size));
        Page<Patient> patients = patientRepository.findByUsernameContaining(keyword,PageRequest.of(page,size));


        model.addAttribute("patients",patients.getContent());

        model.addAttribute("pages",new int[patients.getTotalPages()]);

        model.addAttribute("currentPage",page);

        model.addAttribute("nextPage",page+1);

        model.addAttribute("keyword",keyword);

        return "patients";
    }


    @GetMapping(path = "/delete")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }


    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listPatients(){
        return patientRepository.findAll();
    }

    
    @GetMapping("/formPatient")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }


    @PostMapping("/save")
    public String save(@Valid Patient patient, BindingResult bindingResult){

        if(bindingResult.hasErrors()) return "formPatients";

        patientRepository.save(patient);

        Page<Patient> patients = patientRepository.findByUsernameContaining("",PageRequest.of(0,5));

        return "redirect:/index?page="+(patients.getTotalPages()-1);
    }


    @GetMapping("/edit")
    public String formEditPatients(Model model,Long id){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient not found");
        model.addAttribute("patient",patient);
        return "editPatient";
    }


    @PostMapping("/editPatient")
    public String edit(@Valid Patient p, BindingResult bindingResult){

        if(bindingResult.hasErrors()) return "editPatient";

        Patient patient = patientRepository.findById(p.getId()).orElse(null);

        if(patient==null) throw new RuntimeException("Patient not found");

        patient.setDateOfBirth(p.getDateOfBirth());
        patient.setUsername(p.getUsername());
        patient.setScore(p.getScore());
        patient.setSick(p.isSick());

        patientRepository.save(patient);

        return "redirect:/index";
    }

}

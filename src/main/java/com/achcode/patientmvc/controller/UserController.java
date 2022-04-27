package com.achcode.patientmvc.controller;

import com.achcode.patientmvc.model.Users;
import com.achcode.patientmvc.repository.UsersRepository;
import com.achcode.patientmvc.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class UserController {


    private UsersRepository usersRepository;

    private SecurityService securityService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new Users());
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@Valid @ModelAttribute("user") Users user, BindingResult bindingResult, Model model){

        System.out.println(user);

        if(usersRepository.findByUsername(user.getUsername())!=null){
            System.out.println("Username already exists");
            model.addAttribute("userFound","Username already exists");
            return "register";
        }


        if(!user.getRepeatPassword().equals(user.getPassword())){
            model.addAttribute("comparePasswordError","The password confirmation does not match");
        }

        if(bindingResult.hasErrors()) return "register";


        securityService.saveNewUser(user.getUsername(),user.getPassword(),user.getPassword());
        if(!user.getRole().equals("BOTH"))
            securityService.addRoleToUser(user.getUsername(),user.getRole());
        else{
            securityService.addRoleToUser(user.getUsername(),"ADMIN");
            securityService.addRoleToUser(user.getUsername(),"USER");
        }

        return "redirect:/login";
    }
}

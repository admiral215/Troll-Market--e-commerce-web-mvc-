package com.indocyber.trollmarket.controllers;

import com.indocyber.trollmarket.dtos.admin.AdminRegisterDto;
import com.indocyber.trollmarket.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping("")
    public ModelAndView admin (){
        var mv = new ModelAndView("pages/admin");
        mv.addObject("dto", AdminRegisterDto.builder().build());
        return mv;
    }

    @PostMapping("")
    public String register(@Valid @ModelAttribute("dto")AdminRegisterDto dto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("dto",dto);
            return "pages/admin";
        }
        service.registerAdmin(dto);
        return "redirect:/admin";
    }

}

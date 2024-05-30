package com.indocyber.trollmarket.controllers;


import com.indocyber.trollmarket.configuration.SecurityRole;
import com.indocyber.trollmarket.dtos.AuthRegisterDto;

import com.indocyber.trollmarket.dtos.RoleRequestDto;
import com.indocyber.trollmarket.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AuthenticationController {

    private final AuthService service;

    public AuthenticationController(AuthService service) {
        this.service = service;
    }


    @GetMapping("login")
    public ModelAndView login (Authentication authentication, @RequestParam(required = false) Boolean error){
        var mv = new ModelAndView("auth/login");
        mv.addObject("error", error);
        return mv;
    }

    @GetMapping("register-buyer")
    public ModelAndView registerBuyer(){
        var mv = new ModelAndView("auth/register-buyer");
        mv.addObject("dto",AuthRegisterDto.builder().build());
        return mv;
    }

    @PostMapping("register-buyer")
    public String registeringBuyer(@Valid @ModelAttribute("dto") AuthRegisterDto dto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("dto",dto);
            return "auth/register-buyer";
        }
        service.registerBuyer(dto);
        return "redirect:/login";
    }

    @GetMapping("register-seller")
    public ModelAndView registerSeller(){
        var mv = new ModelAndView("auth/register-seller");
        mv.addObject("dto",AuthRegisterDto.builder().build());
        return mv;
    }

    @PostMapping("register-seller")
    public String registeringSeller(@Valid @ModelAttribute("dto") AuthRegisterDto dto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("dto",dto);
            return "auth/register-seller";
        }
        service.registerSeller(dto);
        return "redirect:/login";
    }

    @GetMapping("home")
    public ModelAndView home(){
        var mv = new ModelAndView("home");
        return mv;
    }

    @GetMapping("role")
    public ModelAndView chooseRole(Authentication authentication){
        var mv = new ModelAndView("auth/role");
        mv.addObject("roles", authentication.getAuthorities());
        mv.addObject("dto", RoleRequestDto.builder().build());
        return mv;
    }

    @PostMapping("role")
    public String choosingRole(@Valid @ModelAttribute("dto") RoleRequestDto dto, BindingResult bindingResult,Authentication authentication,
                               Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute(dto);
            System.out.println(dto.getRole());
            model.addAttribute("roles", authentication.getAuthorities());
            return "auth/role";
        }
        SecurityRole.updateAuthorities(authentication.getName(), dto.getRole());
        System.out.println(dto.getRole());
        System.out.println("sudah update");
        return "redirect:/home";
    }
}

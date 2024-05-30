package com.indocyber.trollmarket.controllers;

import com.indocyber.trollmarket.dtos.user.UserDepositDto;
import com.indocyber.trollmarket.services.ProfileService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("")
    public ModelAndView profile(Authentication authentication, @RequestParam(defaultValue = "1") Integer pageNumber){
        var mv =new ModelAndView("pages/profile");
        mv.addObject("dto",UserDepositDto.builder().build());
        mv.addObject("role", authentication.getAuthorities().iterator().next().toString());
        mv.addObject("user", profileService.getUser(authentication.getName()));
        mv.addObject("orders", profileService.getOrders(authentication.getName(), pageNumber, authentication.getAuthorities().iterator().next().toString()));
        return mv;
    }

    @PostMapping("deposit")
    public String deposit(@Valid @ModelAttribute("dto") UserDepositDto dto, BindingResult bindingResult, Authentication authentication, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("showModal", true);
            System.out.println(dto.getBalance());
            model.addAttribute("dto", dto);
            model.addAttribute("role", authentication.getAuthorities().iterator().next().toString());
            model.addAttribute("user", profileService.getUser(authentication.getName()));
            model.addAttribute("orders", profileService.getOrders(authentication.getName(), 1,authentication.getAuthorities().iterator().next().toString()));
            return "pages/profile";
        }
        profileService.depositBalance(authentication.getName(), dto);
        return "redirect:/profile";
    }
}

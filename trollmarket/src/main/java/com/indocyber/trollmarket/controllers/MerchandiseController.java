package com.indocyber.trollmarket.controllers;

import com.indocyber.trollmarket.dtos.product.ProductUpsertDto;
import com.indocyber.trollmarket.services.MerchandiseService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/merchandise")
public class MerchandiseController {
    private final MerchandiseService service;

    public MerchandiseController(MerchandiseService service) {
        this.service = service;
    }

    @GetMapping("")
    public ModelAndView merchandise(Authentication authentication, @RequestParam(defaultValue = "1") Integer pageNumber){
        var mv = new ModelAndView("pages/merchandise");
        mv.addObject("dto", ProductUpsertDto.builder().build());
        mv.addObject("products", service.getProductsByAccountId(authentication.getName(), pageNumber));
        return mv;
    }

    @PostMapping("insert")
    public String insert (@Valid @ModelAttribute("dto") ProductUpsertDto dto, BindingResult bindingResult,
                          Model model, Authentication authentication){
        if (bindingResult.hasErrors()){
            model.addAttribute("showModal", true);
            model.addAttribute("dto",dto);
            model.addAttribute("products", service.getProductsByAccountId(authentication.getName(), 1));
            return "pages/merchandise";
        }
        service.insertProduct(dto,authentication.getName());
        return "redirect:/merchandise";
    }

    @PostMapping("update/{id}")
    public String update (@Valid @ModelAttribute("dto") ProductUpsertDto dto, BindingResult bindingResult, Model model,
                          @PathVariable Integer id, Authentication authentication){
        if (bindingResult.hasErrors()){
            model.addAttribute("showModal", true);
            model.addAttribute("dto",dto);
            model.addAttribute("products", service.getProductsByAccountId(authentication.getName(), 1));
            return "pages/merchandise";
        }
        service.updateProduct(dto,id);
        return "redirect:/merchandise";
    }

    @PostMapping("delete/{id}")
    public String delete (@PathVariable Integer id, Model model, Authentication authentication){
        if (service.isOrdering(id)){
            model.addAttribute("deleteError", true);
            model.addAttribute("dto",ProductUpsertDto.builder().build());
            model.addAttribute("products", service.getProductsByAccountId(authentication.getName(), 1));
            return "pages/merchandise";
        }

        service.deleteProduct(id);
        return "redirect:/merchandise";
    }

    @PostMapping("discontinue/{id}")
    public String delete (@PathVariable Integer id){
        service.discontinueProduct(id);
        return "redirect:/merchandise";
    }
}

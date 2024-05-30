package com.indocyber.trollmarket.controllers;

import com.indocyber.trollmarket.dtos.cart.CartRequestDto;
import com.indocyber.trollmarket.dtos.product.ProductSearchDto;
import com.indocyber.trollmarket.models.Product;
import com.indocyber.trollmarket.services.ShopService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopService service;

    public ShopController(ShopService service) {
        this.service = service;
    }

    @GetMapping("")
    public ModelAndView shop(ProductSearchDto dto,
                             @RequestParam(defaultValue = "1") Integer pageNumber,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        var mv = new ModelAndView("pages/shop");
        mv.addObject("dto", dto);
        mv.addObject("reqDto",CartRequestDto.builder().build());
        mv.addObject("products", service.getAllProductBySearch(dto, pageNumber, pageSize));
        mv.addObject("shippers",service.getShippersDropdown());
        return mv;
    }

    @PostMapping("cart/{id}")
    public String addToCart(@Valid @ModelAttribute("reqDto") CartRequestDto dto, BindingResult bindingResult,
                            @PathVariable Integer id, Authentication authentication, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("showModal", true);
            model.addAttribute("dto",ProductSearchDto.builder().build());
            model.addAttribute("reqDto", dto);
            model.addAttribute("products", service.getAllProductBySearch(ProductSearchDto.builder().build(), 1, 10));
            model.addAttribute("shippers",service.getShippersDropdown());
            return "pages/shop";
        }
        service.addProductToCart(id, authentication.getName(), dto);
        return "redirect:/shop";
    }
}

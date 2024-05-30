package com.indocyber.trollmarket.controllers;

import com.indocyber.trollmarket.dtos.cart.CartPurchaseDto;
import com.indocyber.trollmarket.services.MyCartService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

@Controller
@RequestMapping("cart")
public class MyCartController {
    private final MyCartService service;

    public MyCartController(MyCartService service) {
        this.service = service;
    }

    @GetMapping("")
    public ModelAndView cart (Authentication authentication, @RequestParam(defaultValue = "1") Integer pageNumber){
        var mv = new ModelAndView("pages/cart");
        mv.addObject("carts", service.getAllCart(authentication.getName(), pageNumber));
        mv.addObject("purchase", service.getSizeCarts(authentication.getName()));
        mv.addObject("purchaseInfo", service.getPurchaseInfo(authentication.getName()));
        return mv;
    }

    @PostMapping("")
    public ModelAndView purchase (Authentication authentication){

        if (service.checkBalanceAndCarts(authentication.getName())){
            var mv = new ModelAndView("pages/cart");
            System.out.println(service.getPurchaseInfo(authentication.getName()));
            mv.addObject("carts", service.getAllCart(authentication.getName(), 1));
            mv.addObject("purchase", service.getSizeCarts(authentication.getName()));
            mv.addObject("purchaseFailed", "true");
            mv.addObject("purchaseInfo",service.getPurchaseInfo(authentication.getName()));

            return mv;
        }
        service.purchaseAllCart(authentication.getName());
        return new ModelAndView("redirect:/cart");
    }

    @PostMapping("delete/{id}")
    public String delete (@PathVariable BigInteger id){
        service.deleteCartById(id);
        return "redirect:/cart";
    }
}

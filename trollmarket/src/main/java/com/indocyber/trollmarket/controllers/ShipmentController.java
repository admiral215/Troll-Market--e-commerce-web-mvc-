package com.indocyber.trollmarket.controllers;


import com.indocyber.trollmarket.dtos.shipper.ShipperUpsertDto;
import com.indocyber.trollmarket.services.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shipment")
public class ShipmentController {
    private final ShipmentService service;

    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @GetMapping("")
    public ModelAndView shipment(@RequestParam(defaultValue = "1") Integer pageNumber){
        var mv = new ModelAndView("pages/shipment");
        mv.addObject("dto",ShipperUpsertDto.builder().build());
        mv.addObject("shippers",service.getAllShipment(pageNumber));
        return mv;
    }

    @PostMapping("upsert")
    public String insert(@Valid @ModelAttribute("dto") ShipperUpsertDto dto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("invalidUpsert",true);
            model.addAttribute("shippers",service.getAllShipment(1));
            return "pages/shipment";
        }
        service.upsertShipper(dto);
        return "redirect:/shipment";
    }

    @PostMapping("service/{id}")
    public String service (@PathVariable Integer id){
        service.offServiceShipper(id);
        return "redirect:/shipment";
    }

    @PostMapping("delete/{id}")
    public String delete (@PathVariable Integer id, Model model){
        if (service.isHasOrder(id)){
            model.addAttribute("dto",ShipperUpsertDto.builder().build());
            model.addAttribute("shippers",service.getAllShipment(1));
            model.addAttribute("deleteError", true);
            return "pages/shipment";
        }
        service.deleteShipperById(id);
        return "redirect:/shipment";
    }
}

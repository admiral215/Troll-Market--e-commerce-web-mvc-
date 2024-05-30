package com.indocyber.trollmarket.controllers;

import com.indocyber.trollmarket.dtos.history.HistorySearchDto;
import com.indocyber.trollmarket.models.RoleEnum;
import com.indocyber.trollmarket.services.HistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService service;

    public HistoryController(HistoryService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView history (HistorySearchDto dto, @RequestParam(defaultValue = "1") Integer pageNumber){
        var mv = new ModelAndView("pages/history");
        dto.setSellerId(Objects.equals(dto.getSellerId(), "") ? null : dto.getSellerId());
        dto.setBuyerId(Objects.equals(dto.getBuyerId(), "") ? null : dto.getBuyerId());
        mv.addObject("dto", dto);
        mv.addObject("buyers",service.getDropdownUserByRole(RoleEnum.BUYER));
        mv.addObject("sellers",service.getDropdownUserByRole(RoleEnum.SELLER));
        mv.addObject("orders",service.getAllBySearch(dto, pageNumber));
        System.out.println(service.getAllBySearch(dto, pageNumber));
        return mv;
    }
}

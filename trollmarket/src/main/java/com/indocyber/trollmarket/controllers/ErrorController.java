package com.indocyber.trollmarket.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
//public class ErrorController {
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ModelAndView handleForbiddenAccess (Exception e, Model model){
//
//        var mv = new ModelAndView("/error-403");
//        return mv;
//    }
//}

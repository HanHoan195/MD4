package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {
    @GetMapping("/home")
    public String getMessage(Model model) {
        model.addAttribute("hello","Spring boot");
        return "index";
    }
}

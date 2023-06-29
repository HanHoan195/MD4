package com.example.springgreeting.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class HomeCotroller {
    @RequestMapping("/home")
    public String index( Model model) {
        model.add
        return "index";
    }
}

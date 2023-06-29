package com.cg.controller;

import com.cg.model.Product;
import com.cg.service.IProductService;
import com.cg.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {
    private final IProductService iProductService = new ProductService();


    @GetMapping
    public String showListProduct(Model model) {
        List<Product> products = iProductService.findAll();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        Product product = new Product();


        model.addAttribute("product", product);
        return "create";
    }


}

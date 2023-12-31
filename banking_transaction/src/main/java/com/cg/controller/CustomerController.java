package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.customer.CustomerService;
import com.cg.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")

public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/list");

        List<Customer> customers = customerService.findAll();

        modelAndView.addObject("customers", customers);

        return modelAndView;
    }

    @GetMapping("/create")
    private String showCreate(Model model) {

        Customer customer = new Customer();

        model.addAttribute("customer", customer);


        return "customer/create";
    }

    @PostMapping("/create")
    private String doCreate(@ModelAttribute Customer customer, Model model) {
        customerService.save(customer);

        model.addAttribute("customer", new Customer());
//        redirectAttributes.addFlashAttribute("success", true);
//        redirectAttributes.addFlashAttribute("message", "Thêm thành công");

        return "redirect:/customers/create";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keySearch, Model model) {
        keySearch = "%" + keySearch + "%";

        List<Customer> customers = customerService.findAllByFullNameLikeOrEmailLikeOrPhoneLike(keySearch, keySearch, keySearch);

        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/info")
    public String showInfoByParamPage(@RequestParam Long id, Model model) {
        Optional<Customer> customerOptional = customerService.findById(id);

        if (customerOptional.isEmpty()) {
            return "redirect:/error/404";
        }
        Customer customer = customerOptional.get();

        model.addAttribute("customer", customer);
        return "customer/info";
    }

    @GetMapping("/{id}")
    public String showEditPage(@PathVariable String id, Model model) {
        try {
            Long cusstomerId = Long.parseLong(id);
            Optional<Customer> customerOptional = customerService.findById(cusstomerId);

            if (customerOptional.isEmpty()) {
                return "redirect:/error/404";
            }

            Customer customer = customerOptional.get();

            model.addAttribute("customer", customer);
            return "customer/edit";
        } catch (Exception e) {
            return "error/404";
        }
    }

    @PostMapping("/{id}")
    public String doUpdate(@PathVariable Long id, @ModelAttribute Customer customer, Model model) {
        customer.setId(id);
        customerService.save(customer);

        List<Customer> customers = customerService.findAll();

        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            Long customerId = Long.parseLong(id);
            customerService.deleteById(customerId);

            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công");

            return "redirect:/customers";


        } catch (Exception e) {
            return "error/404";
        }
    }


}

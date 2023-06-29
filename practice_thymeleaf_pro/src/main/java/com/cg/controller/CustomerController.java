package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.CustomerType;
import com.cg.service.CustomerService;
import com.cg.service.CustomerTypeService;
import com.cg.service.ICustomerService;
import com.cg.service.ICustomerTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("customer")
public class CustomerController {
    public CustomerController(){
        System.out.println("ChAY CONTROLLER");
    }
    private final ICustomerService customerService = new CustomerService();
    private final ICustomerTypeService customerTypeService = new CustomerTypeService();

//    @GetMapping("")
//    public String index(Model model) {
//
//        List<Customer> customerList = customerService.findAll();
//        model.addAttribute("customers", customerList);
//        return "/index";
//    }

    @GetMapping("")
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView("/index");
        List<Customer> customerList = customerService.findAll();
        modelAndView.addObject("customers", customerList);
        return modelAndView;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        List<CustomerType>customerTypes = customerTypeService.findAll();
        model.addAttribute("customerTypes", customerTypes);
        return "/create";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        List<CustomerType>customerTypes = customerTypeService.findAll();
        model.addAttribute("customerTypes", customerTypes);
        return "/edit";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        List<CustomerType>customerTypes = customerTypeService.findAll();
        model.addAttribute("customerTypes", customerTypes);
        return "/delete";
    }
    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        List<CustomerType>customerTypes = customerTypeService.findAll();
        model.addAttribute("customerTypes", customerTypes);
        return "/view";
    }

    @PostMapping("/save")
    public String save(Customer customer) {
        customer.setId((int) (Math.random() * 10000));


        CustomerType customerType = customerTypeService.findById(customer.getCustomerType().getId());
        customer.setCustomerType(customerType);

        customerService.save(customer);
        return "redirect:/customer";
    }

    @PostMapping("/update")
    public String update(Customer customer, RedirectAttributes redirectAttributes) {
        CustomerType customerType = customerTypeService.findById(customer.getCustomerType().getId());
        customer.setCustomerType(customerType);
        customerService.update(customer.getId(), customer);


        redirectAttributes.addFlashAttribute("message", "Sửa thành công");
        return "redirect:/customer";
    }

    @PostMapping("/delete")
    public String delete(Customer customer, RedirectAttributes redirect) {
        customerService.remove(customer.getId());
        redirect.addFlashAttribute("success", "Removed customer successfully!");
        return "redirect:/customer";
    }

}
package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.CustomerService;
import com.cg.service.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("customers") // đường dẫn mặc định lúc ban đầu

public class CustomerController {
    ICustomerService customerService = new CustomerService();

    @GetMapping
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView();// tạo modelandview
        modelAndView.setViewName("customer/list"); // chuyển đến trang customer/list.html
        ICustomerService customerService = new CustomerService();
        List<Customer> customers = customerService.getAll();

        if (customers != null) {
            for (Customer customer : customers) {
                System.out.println(customer.toString());
            }
        }
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/info")
    public String showInfoByParamPage(@RequestParam Long id, Model model) {
        // RequestParam tự động ánh xạ query parameter truyền vào tương ứng vs tên và kiểu dữ liệu của tham số


        Customer customer = customerService.getById(id);

        model.addAttribute("customer", customer);//bắn dữ liệu

        return "customer/info";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {

        Customer customer = new Customer();

        model.addAttribute("customer", customer);

        return "customer/create";
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute Customer customer, Model model) {
        //ModelAttribute là một cách bổ sung no giú bind tham số hoặc kết quả trả về
        // của một phương thức thành một modelattribute dưới tên đc chỉ định

        customerService.add(customer);

        model.addAttribute("customer", new Customer());

        return "customer/create";
    }

    @GetMapping("/{id}")
    public String showEditpage(@PathVariable String id, Model model) {
        //@PathVariable dùng để xác thực kháo chính của entity muốn truy vấn
        try {
            Long customerId = Long.parseLong(id);
            Customer customer = customerService.getById(customerId);

            model.addAttribute("customer", customer);

            return "customer/edit";

        } catch (Exception e) {
            return "error/404";
        }
    }

    @PostMapping("/{id}")
    public String doUpdate(@PathVariable Long id,@ModelAttribute Customer customer, Model model) {

        customer.setId(id);
        customerService.update(customer);

        List<Customer> customers = customerService.getAll();

        model.addAttribute("customers", customers);

        return "customer/list";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        List<Customer> customers = customerService.getAll();
        Customer customer1 = customerService.getById(id);

        customers.remove(customer1);

        model.addAttribute("customers", customers);

        return "redirect:/customers";
    }


}

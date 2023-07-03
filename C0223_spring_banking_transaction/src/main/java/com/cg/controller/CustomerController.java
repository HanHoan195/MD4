package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposits;
import com.cg.model.Withdraw;
import com.cg.service.customer.CustomerService;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService = new CustomerService();
    @Autowired
    private IDepositService depositService;
    @Autowired
    private IWithdrawService withdrawService;

    @GetMapping
    public String showList(Model model) {
        List<Customer> customers = customerService.findAll();

        model.addAttribute("customers", customers);
        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        Customer customer = new Customer();

        model.addAttribute("customer", customer);

        return "customer/create";
    }

    @GetMapping("/{id}")
    public String showUpdate(@PathVariable String id, Model model) {
        try {
            Long customerId = Long.parseLong(id);
            Optional<Customer> customerOptional = customerService.findById(customerId);
            if (customerOptional.isEmpty()) {
                return "redirect:/error/404";
            }
            Customer customer = customerOptional.get();

            model.addAttribute("customer", customer);

            return "customer/update";
        } catch (Exception e) {
            return "error/404";
        }
    }

    @GetMapping("/deposit/{customerId}")
    public String showDeposits(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng khôg tồn tại!");

        } else {
            Customer customer = customerOptional.get();
            Deposits deposits = new Deposits();
            deposits.setCustomer(customer);
            model.addAttribute("deposits", deposits);
        }
        return "customer/deposit";
    }

    @GetMapping("/withdraw/{customerId}")
    public String showWithdraw(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng khôg tồn tại!");
        } else {
            Customer customer = customerOptional.get();
            Withdraw withDraw = new Withdraw();
            withDraw.setCustomer(customer);
            model.addAttribute("withDraw", withDraw);
        }


        return "customer/withdraw";
    }






    @PostMapping("/{id}")
    public String doUpdate(@PathVariable Long id, @ModelAttribute Customer customer, Model model) {
        customer.setId(id);
        customerService.save(customer);

        List<Customer> customers = customerService.findAll();

        model.addAttribute("customers", customers);
        return "customer/list";
    }


    @PostMapping("create")
    public String doCreate(@ModelAttribute Customer customer, Model model) {
        customerService.save(customer);

        model.addAttribute("customer", new Customer());

        return "redirect:/customers/create";
    }

    @PostMapping("/deposit/{customerId}")
    public String doDeposit(@ModelAttribute Deposits deposits, @PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng khôg tồn tại!");

        } else {
            Customer customer = customerOptional.get();

            deposits.setId(null);
            depositService.save(deposits);

            BigDecimal currentBalance = customer.getBalance();
            BigDecimal newBalance = currentBalance.add(deposits.getTransaction_Amount());
            customer.setBalance(newBalance);
            customerService.save(customer);

            deposits.setCustomer(customer);

            model.addAttribute("deposits", deposits);
        }

        return "customer/deposit";
    }

    @PostMapping("/withdraw/{customerId}")
    public String doWithdraw(@ModelAttribute Withdraw withdraw, @PathVariable Long customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("message", "ID khách hàng khôg tồn tại!");

        } else {
            Customer customer = customerOptional.get();

            withdraw.setId(null);
            withdrawService.save(withdraw);

            BigDecimal currentBalance = customer.getBalance();
            BigDecimal newBalance = currentBalance.subtract(withdraw.getTransaction_Amount());
            customer.setBalance(newBalance);
            customerService.save(customer);

            withdraw.setCustomer(customer);

            model.addAttribute("withdraw", withdraw);
        }
        return "customer/withdraw";
    }

}
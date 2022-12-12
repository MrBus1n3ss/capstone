package com.wgu.scheduling.view;

import com.wgu.scheduling.controller.CustomerController;
import com.wgu.scheduling.controller.UserController;
import com.wgu.scheduling.model.*;
import com.wgu.scheduling.repository.StateRepository;
import com.wgu.scheduling.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Controller
public class CustomerView {

    @Autowired
    CustomerController customerController;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    UserController userController;

    @RequestMapping(value = "/customer")
    public String customerDetails(Model model) {
        if (Auth.isIsUserAuthorized()) {
            List<Customer> customer = customerController.getAll().getBody();
            System.out.println(customer);
            model.addAttribute("customers", customerController.getAll().getBody());
            model.addAttribute("isAdmin", isAdmin());
            return "customer_list";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/customer/create")
    public String show(Model model) {
        String title = "Create Customer";
        if (Auth.isIsUserAuthorized()) {
            List<UsState> states = stateRepository.findAll();
            Collections.sort(states);
            Customer customer = new Customer();
            Address address = new Address();
            Phone phone = new Phone();
            model.addAttribute("title", title);
            model.addAttribute("customer", customer);
            model.addAttribute("address", address);
            model.addAttribute("phone", phone);
            model.addAttribute("states", states);
            model.addAttribute("isAdmin", isAdmin());
            return "customer_create";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/customer/create")
    public String submit(@ModelAttribute("customer") Customer customer) {

        Timestamp ts = Timestamp.from(Instant.now());

        if (Auth.isIsUserAuthorized()) {

            customer.getHomePhone().setPhoneName("HOME");
            customer.getMobilePhone().setPhoneName("MOBILE");
            customer.getWorkPhone().setPhoneName("WORK");
            customer.setModifiedAt(ts);
            if (customer.getId() == null) {
                customer.setCreatedBy(CurrentUser.userName);
                customer.setCreatedAt(ts);

                customerController.save(customer);
            } else {

                customerController.update(customer, customer.getId());
            }

            return "redirect:/customer";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/customer/edit/{id}")
    public String fillEdit(@PathVariable(value = "id") long id, Model model) {
        String title = "Update Customer";
        if (Auth.isIsUserAuthorized()) {
            List<UsState> states = stateRepository.findAll();
            Customer customer = customerController.getOne(id).getBody();
            model.addAttribute("title", title);
            model.addAttribute("customer", customer);
            model.addAttribute("states", states);
            return "customer_create";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/customer/delete/{id}")
    public String delete(@PathVariable(value = "id") long id, Model model) {

        if (Auth.isIsUserAuthorized()) {
            customerController.delete(id);
            return "redirect:/customer";
        } else {
            return "redirect:/login";
        }
    }

    private boolean isAdmin() {
        boolean isAdmin = false;
        Role role = userController.getRoleById(CurrentUser.id).getBody();
        if (role != null && role.getRoleName().equals("ADMIN")) {
            isAdmin = true;
        }
        return isAdmin;
    }
}

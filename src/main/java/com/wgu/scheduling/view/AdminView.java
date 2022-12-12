package com.wgu.scheduling.view;

import com.wgu.scheduling.controller.UserController;
import com.wgu.scheduling.model.*;
import com.wgu.scheduling.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Controller
public class AdminView {

    @Autowired
    UserController userController;

    @GetMapping(value = "/admin")
    public String adminDashboard(Model model) {
        if (Auth.isIsUserAuthorized()) {
            List<User> users = userController.getAll().getBody();
            model.addAttribute("users", users);
            model.addAttribute("isAdmin", isAdmin());
            return "admin_list";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/admin/create")
    public String show(Model model) {

        if (Auth.isIsUserAuthorized()) {
            User user = new User();
            List<Role> roles = userController.getRoles().getBody();
            model.addAttribute("user", user);
            model.addAttribute("isAdmin", isAdmin());
            model.addAttribute("roles", roles);
            return "admin_create";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/admin/create")
    public String submit(@ModelAttribute("user") User user) {

        Timestamp ts = Timestamp.from(Instant.now());

        if (Auth.isIsUserAuthorized()) {
            userController.save(user);
            return "redirect:";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/admin/activate/{id}")
    public String activate(@PathVariable(value = "id") long id) {
        User user = userController.getOne(id).getBody();
        Timestamp ts = Timestamp.from(Instant.now());

        if (Auth.isIsUserAuthorized()) {
            Objects.requireNonNull(user).setActive(true);

            userController.update(user, user.getId());
            return "redirect:/admin";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/admin/deactivate/{id}")
    public String deactivate(@PathVariable(value = "id") long id) {
        User user = userController.getOne(id).getBody();
        Timestamp ts = Timestamp.from(Instant.now());

        if (Auth.isIsUserAuthorized()) {
            Objects.requireNonNull(user).setActive(false);
            userController.update(user, user.getId());
            return "redirect:/admin";
        } else {
            return "redirect:/login";
        }
    }

    @PutMapping(value = "/admin/updateRole")
    public String updateRole(@ModelAttribute("user") User user) {
        Timestamp ts = Timestamp.from(Instant.now());
        if (Auth.isIsUserAuthorized()) {
            return "redirect:";
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

package com.wgu.scheduling.view;

import com.wgu.scheduling.controller.UserController;
import com.wgu.scheduling.model.*;
import com.wgu.scheduling.util.Auth;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.time.Instant;
import java.util.Objects;


@Controller
public class LoginView {

    @Autowired
    UserController userController;

    @GetMapping(value = "/login")
    public String show(Model model) {
        Auth.logout();
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping(value = "/login")
    public String submit(@ModelAttribute("user") User user, Model model) {
        try{
            User loginUser = userController.findByUserName(user.getUserName()).getBody();
            String encodeUserPassword = DigestUtils.sha256Hex(user.getPassword());
            if(Objects.requireNonNull(loginUser).getPassword().equals(encodeUserPassword)) {
                Auth.setIsUserAuthorized(true);
                CurrentUser.id = loginUser.getId();
                CurrentUser.userName = loginUser.getUserName();
                CurrentUser.roleId = loginUser.getRoleId();
                CurrentUser.businessHoursId = loginUser.getBusinessId();

                return "redirect:";
            }
            else {
                Auth.setIsUserAuthorized(false);
                return "redirect:login";
            }
        } catch (NullPointerException e) {
            return "redirect:login";
        }
    }

}

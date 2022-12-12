package com.wgu.scheduling.view;

import com.wgu.scheduling.controller.AppointmentController;
import com.wgu.scheduling.controller.UserController;
import com.wgu.scheduling.model.*;
import com.wgu.scheduling.util.Auth;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Controller
public class DashboardView {

    @Autowired
    UserController userController;

    @Autowired
    AppointmentController appointmentController;

    @RequestMapping(value = "/")
    public String dashboard(
            Model model) {
        if(Auth.isIsUserAuthorized()) {
            boolean isAdmin = false;
            List<Appointment> appointments = new ArrayList<>();
            User user = new User();
            try {
                user = userController.getOne(CurrentUser.id).getBody();
            } catch (Exception e) {
                log.info(e);
            }
            try {
                Role role = userController.getRoleById(CurrentUser.id).getBody();
                if (role != null && role.getRoleName().equals("ADMIN")) {
                    isAdmin = true;
                }
            } catch (Exception e) {
                log.info(e);
            }

            try {
                appointments = appointmentController.getByDay().getBody();
            } catch (Exception e) {
                log.info(e);
            }


            model.addAttribute("username", CurrentUser.userName);
            model.addAttribute("isAdmin", isAdmin);

            if(Objects.requireNonNull(appointments).size() > 0){
                model.addAttribute("hasAppointments", true);
            } else {
                model.addAttribute("hasAppointments", false);
            }

            model.addAttribute("appointments", appointments);

            model.addAttribute("mondayIsDayOff", user.isMondayDayOff());
            model.addAttribute("mondayStartTime", user.getMondayStartTime());
            model.addAttribute("mondayEndTime", user.getMondayEndTime());

            model.addAttribute("tuesdayIsDayOff", user.isTuesdayDayOff());
            model.addAttribute("tuesdayStartTime", user.getTuesdayStartTime());
            model.addAttribute("tuesdayEndTime", user.getTuesdayEndTime());

            model.addAttribute("wednesdayIsDayOff", user.isWednesdayDayOff());
            model.addAttribute("wednesdayStartTime", user.getWednesdayStartTime());
            model.addAttribute("wednesdayEndTime", user.getWednesdayEndTime());

            model.addAttribute("thursdayIsDayOff", user.isThursdayDayOff());
            model.addAttribute("thursdayStartTime", user.getThursdayStartTime());
            model.addAttribute("thursdayEndTime", user.getThursdayEndTime());

            model.addAttribute("fridayIsDayOff", user.isFridayDayOff());
            model.addAttribute("fridayStartTime", user.getFridayStartTime());
            model.addAttribute("fridayEndTime", user.getFridayEndTime());

            model.addAttribute("saturdayIsDayOff", user.isSaturdayDayOff());
            model.addAttribute("saturdayStartTime", user.getSaturdayStartTime());
            model.addAttribute("saturdayEndTime", user.getSaturdayEndTime());

            model.addAttribute("sundayIsDayOff", user.isSundayDayOff());
            model.addAttribute("sundayStartTime", user.getSundayStartTime());
            model.addAttribute("sundayEndTime", user.getSundayEndTime());


            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }

}

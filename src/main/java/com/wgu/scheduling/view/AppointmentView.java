package com.wgu.scheduling.view;

import com.wgu.scheduling.controller.AppointmentController;
import com.wgu.scheduling.controller.CustomerController;
import com.wgu.scheduling.controller.UserController;
import com.wgu.scheduling.model.Appointment;
import com.wgu.scheduling.model.CurrentUser;
import com.wgu.scheduling.model.Customer;
import com.wgu.scheduling.model.Role;
import com.wgu.scheduling.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;


@Controller
public class AppointmentView {

    @Autowired
    AppointmentController appointmentController;

    @Autowired
    CustomerController customerController;

    @Autowired
    UserController userController;

    @GetMapping(value = "/appointment/weekly")
    public String appointmentWeeklyDetails(Model model) {
        if (Auth.isIsUserAuthorized()) {
            List<List<Appointment>> weekAndMonthAppointments = appointmentController.getAllByWeekAndMonth().getBody();
            List<Appointment> weekly = Objects.requireNonNull(weekAndMonthAppointments).get(0);
            model.addAttribute("weekly", weekly);
            model.addAttribute("isWeekly", true);
            model.addAttribute("isAdmin", isAdmin());
            return "appointment_list_weekly";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/appointment/monthly")
    public String appointmentMonthlyDetails(Model model) {
        if (Auth.isIsUserAuthorized()) {
            List<List<Appointment>> weekAndMonthAppointments = appointmentController.getAllByWeekAndMonth().getBody();
            List<Appointment> monthly = Objects.requireNonNull(weekAndMonthAppointments).get(1);
            model.addAttribute("monthly", monthly);
            model.addAttribute("isWeekly", true);
            model.addAttribute("isAdmin", isAdmin());
            return "appointment_list_monthly";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/appointment/create")
    public String show(Model model) {
        if (Auth.isIsUserAuthorized()) {
            Appointment appointment = new Appointment();
            List<Customer> customers = customerController.getAll().getBody();
            if (customers != null && customers.isEmpty()) {
                return "redirect:/appointment";
            }
            model.addAttribute("isAdmin", isAdmin());
            model.addAttribute("customers", customers);
            model.addAttribute("appointment", appointment);
            return "appointment_create";
        } else {
            return "redirect:/login";
        }
    }

    // TODO: add update

    @PostMapping(value = "/appointment/create")
    public String submit(@ModelAttribute("appointment") Appointment appointment) {

        if (Auth.isIsUserAuthorized()) {
            Timestamp current = Timestamp.from(Instant.now());

            appointment.setModifiedBy(CurrentUser.userName);
            appointment.setModifiedAt(current);

            if (appointment.getAppointmentId() == null) {

                appointment.setCreatedBy(CurrentUser.userName);
                appointment.setCreatedAt(current);
                appointment.setCreatedBy(CurrentUser.userName);
                appointment.setCreatedAt(current);

                appointmentController.save(appointment);
            } else {
                appointmentController.update(appointment, appointment.getAppointmentId());
            }
            return "redirect:/appointment/weekly";
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping(value = "/appointment/edit/{id}")
    public String fillEdit(@PathVariable(value = "id") long id, Model model) {
        if (Auth.isIsUserAuthorized()) {
            Appointment appointment = appointmentController.getOne(id).getBody();
            List<Customer> customers = customerController.getAll().getBody();
            model.addAttribute("appointment", appointment);
            model.addAttribute("customers", customers);

            return "appointment_create";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/appointment/delete/{id}")
    public String delete(@PathVariable(value = "id") long id, Model model) {

        if (Auth.isIsUserAuthorized()) {
            appointmentController.delete(id);
            return "redirect:/appointment/weekly";
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

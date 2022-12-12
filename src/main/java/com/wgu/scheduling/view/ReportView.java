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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class ReportView {

    @Autowired
    CustomerController customerController;

    @Autowired
    AppointmentController appointmentController;

    @Autowired
    UserController userController;

    @RequestMapping(value = "/reports")
    public String reports(Model model) {
        if (Auth.isIsUserAuthorized()) {
            model.addAttribute("isAdmin", isAdmin());
            return "reports";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/reports/monthly")
    public String monthlyReport(Model model) {

        if (Auth.isIsUserAuthorized()) {
            List<Appointment> appointments = appointmentController.getAllForReport().getBody();
            model.addAttribute("appointments", appointments);
            model.addAttribute("isMonthlyReports", true);
            model.addAttribute("appointmentCount", Objects.requireNonNull(appointments).size());
            model.addAttribute("isAdmin", isAdmin());
            return "reports";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/reports/newCustomers")
    public String newCustomer(Model model) {

        if (Auth.isIsUserAuthorized()) {

            List<Customer> customers = customerController.getAllByMonth().getBody();
            int customerCount = 0;
            for(Customer customer: Objects.requireNonNull(customers)) {
                customerCount++;
            }
            model.addAttribute("customers", customers);
            model.addAttribute("customerCount", customerCount);
            model.addAttribute("isNewCustomers", true);
            model.addAttribute("isAdmin", isAdmin());
            return "reports";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/reports/appointmentTypes")
    public String appointmentTypes(Model model) {

        if (Auth.isIsUserAuthorized()) {

            List<List<Appointment>> weekAndMonthAppointments = appointmentController.getAllByWeekAndMonth().getBody();
            List<Appointment> weekly = Objects.requireNonNull(weekAndMonthAppointments).get(0);
            List<Appointment> monthly = weekAndMonthAppointments.get(1);

            int weeklyInPersonCount = 0;
            int weeklyVirtualCount = 0;
            int weeklyErrorCount = 0;
            for(Appointment appointment: weekly) {
                if(appointment.getType().equals("inPerson")) {
                    weeklyInPersonCount++;
                }
                if(appointment.getType().equals("virtual")) {
                    weeklyVirtualCount++;
                } else {
                    weeklyErrorCount++;
                }
            }

            int monthlyInPersonCount = 0;
            int monthlyVirtualCount = 0;
            int monthlyErrorCount = 0;
            for(Appointment appointment: monthly) {
                if(appointment.getType().equals("inPerson")) {
                    monthlyInPersonCount++;
                }
                if(appointment.getType().equals("virtual")) {
                    monthlyVirtualCount++;
                } else {
                    monthlyErrorCount++;
                }
            }

            model.addAttribute("isAdmin", isAdmin());
            model.addAttribute("isAppointmentTypes", true);
            model.addAttribute("weeklyInPersonCount", weeklyInPersonCount);
            model.addAttribute("weeklyVirtualCount", weeklyVirtualCount);
            model.addAttribute("weeklyErrorCount", weeklyErrorCount);
            model.addAttribute("monthlyInPersonCount", monthlyInPersonCount);
            model.addAttribute("monthlyVirtualCount", monthlyVirtualCount);
            model.addAttribute("monthlyErrorCount", monthlyErrorCount);

            return "reports";
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

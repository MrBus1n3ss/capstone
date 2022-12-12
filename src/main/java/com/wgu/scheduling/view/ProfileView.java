package com.wgu.scheduling.view;

import com.wgu.scheduling.controller.UserController;
import com.wgu.scheduling.model.*;
import com.wgu.scheduling.repository.UserRepository;
import com.wgu.scheduling.util.Auth;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;

@Controller
public class ProfileView {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/profile")
    public String profile(Model model) {
        if (Auth.isIsUserAuthorized()) {
            model.addAttribute("isAdmin", isAdmin());
            return "profile";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/user_password")
    public String password(Model model) {
        if (Auth.isIsUserAuthorized()) {
            User user = userController.getOne(CurrentUser.id).getBody();

            model.addAttribute("isAdmin", isAdmin());
            model.addAttribute("user", user);

            return "password_reset";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/password")
    public String resetPassword(@ModelAttribute("user") User user) {
        if(Auth.isIsUserAuthorized()) {
            user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
            userRepository.updatePassword(user);
            return "profile";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/user_hours")
    public String hours(Model model) {
        if (Auth.isIsUserAuthorized()) {
            User user = userController.getOne(CurrentUser.id).getBody();

            BusinessHoursWrapper businessHoursWrapper = new BusinessHoursWrapper();

            businessHoursWrapper.setMondayDayOff(user.isMondayDayOff());
            businessHoursWrapper.setMondayStartTime(user.getMondayStartTime().toString());
            businessHoursWrapper.setMondayEndTime(user.getMondayEndTime().toString());

            businessHoursWrapper.setTuesdayDayOff(user.isTuesdayDayOff());
            businessHoursWrapper.setTuesdayStartTime(user.getTuesdayStartTime().toString());
            businessHoursWrapper.setTuesdayEndTime(user.getTuesdayEndTime().toString());

            businessHoursWrapper.setWednesdayDayOff(user.isWednesdayDayOff());
            businessHoursWrapper.setWednesdayStartTime(user.getWednesdayStartTime().toString());
            businessHoursWrapper.setWednesdayEndTime(user.getWednesdayEndTime().toString());

            businessHoursWrapper.setThursdayDayOff(user.isThursdayDayOff());
            businessHoursWrapper.setThursdayStartTime(user.getThursdayStartTime().toString());
            businessHoursWrapper.setThursdayEndTime(user.getThursdayEndTime().toString());

            businessHoursWrapper.setFridayDayOff(user.isFridayDayOff());
            businessHoursWrapper.setFridayStartTime(user.getFridayStartTime().toString());
            businessHoursWrapper.setFridayEndTime(user.getFridayEndTime().toString());

            businessHoursWrapper.setSaturdayDayOff(user.isSaturdayDayOff());
            businessHoursWrapper.setSaturdayStartTime(user.getSaturdayStartTime().toString());
            businessHoursWrapper.setSaturdayEndTime(user.getSaturdayEndTime().toString());

            businessHoursWrapper.setSundayDayOff(user.isSundayDayOff());
            businessHoursWrapper.setSundayStartTime(user.getSundayStartTime().toString());
            businessHoursWrapper.setSundayEndTime(user.getSundayEndTime().toString());

            model.addAttribute("isAdmin", isAdmin());
            model.addAttribute("businessHoursWrapper", businessHoursWrapper);

            return "user_business_hours";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/businessHours")
    public String show(@ModelAttribute("businessHoursWrapper") BusinessHoursWrapper businessHoursWrapper) {

        if (Auth.isIsUserAuthorized()) {
            Timestamp current = Timestamp.from(Instant.now());
            User user = userRepository.getById(CurrentUser.id);

            BusinessHoursSegment businessHoursSegment = checkStartAndEndTime(businessHoursWrapper.getMondayStartTime(), businessHoursWrapper.getMondayEndTime(), businessHoursWrapper.isMondayDayOff());
            if(businessHoursSegment == null) {
                return "error";
            }
            user.setMondayDayOff(businessHoursSegment.isDayOff());
            user.setMondayStartTime(businessHoursSegment.getStartTime());
            user.setMondayEndTime(businessHoursSegment.getEndTime());
            businessHoursSegment = null;

            businessHoursSegment = checkStartAndEndTime(businessHoursWrapper.getTuesdayStartTime(), businessHoursWrapper.getTuesdayEndTime(), businessHoursWrapper.isTuesdayDayOff());
            if(businessHoursSegment == null) {
                return "error";
            }
            user.setTuesdayDayOff(businessHoursSegment.isDayOff());
            user.setTuesdayStartTime(businessHoursSegment.getStartTime());
            user.setTuesdayEndTime(businessHoursSegment.getEndTime());
            businessHoursSegment = null;

            businessHoursSegment = checkStartAndEndTime(businessHoursWrapper.getWednesdayStartTime(), businessHoursWrapper.getWednesdayEndTime(), businessHoursWrapper.isWednesdayDayOff());
            if(businessHoursSegment == null) {
                return "error";
            }
            user.setWednesdayDayOff(businessHoursSegment.isDayOff());
            user.setWednesdayStartTime(businessHoursSegment.getStartTime());
            user.setWednesdayEndTime(businessHoursSegment.getEndTime());
            businessHoursSegment = null;

            businessHoursSegment = checkStartAndEndTime(businessHoursWrapper.getThursdayStartTime(), businessHoursWrapper.getThursdayEndTime(), businessHoursWrapper.isThursdayDayOff());
            if(businessHoursSegment == null) {
                return "error";
            }
            user.setThursdayDayOff(businessHoursSegment.isDayOff());
            user.setThursdayStartTime(businessHoursSegment.getStartTime());
            user.setThursdayEndTime(businessHoursSegment.getEndTime());
            businessHoursSegment = null;

            businessHoursSegment = checkStartAndEndTime(businessHoursWrapper.getFridayStartTime(), businessHoursWrapper.getFridayEndTime(), businessHoursWrapper.isFridayDayOff());
            if(businessHoursSegment == null) {
                return "error";
            }
            user.setFridayDayOff(businessHoursSegment.isDayOff());
            user.setFridayStartTime(businessHoursSegment.getStartTime());
            user.setFridayEndTime(businessHoursSegment.getEndTime());
            businessHoursSegment = null;

            businessHoursSegment = checkStartAndEndTime(businessHoursWrapper.getSaturdayStartTime(), businessHoursWrapper.getSaturdayEndTime(), businessHoursWrapper.isSaturdayDayOff());
            if(businessHoursSegment == null) {
                return "error";
            }
            user.setSaturdayDayOff(businessHoursSegment.isDayOff());
            user.setSaturdayStartTime(businessHoursSegment.getStartTime());
            user.setSaturdayEndTime(businessHoursSegment.getEndTime());
            businessHoursSegment = null;

            businessHoursSegment = checkStartAndEndTime(businessHoursWrapper.getSundayStartTime(), businessHoursWrapper.getSundayEndTime(), businessHoursWrapper.isSundayDayOff());
            if(businessHoursSegment == null) {
                return "error";
            }
            user.setSundayDayOff(businessHoursSegment.isDayOff());
            user.setSundayStartTime(businessHoursSegment.getStartTime());
            user.setSundayEndTime(businessHoursSegment.getEndTime());
            businessHoursSegment = null;

            user.setModifiedBy(CurrentUser.userName);
            user.setModifiedAt(current);

            userRepository.update(user);

            return "profile";
        } else {
            return "redirect:/login";
        }
    }

    private BusinessHoursSegment checkStartAndEndTime(String startTime, String endTime, boolean isDayOff){

        BusinessHoursSegment businessHoursSegment = new BusinessHoursSegment();

        if(isDayOff) {
            businessHoursSegment.setDayOff(true);
            businessHoursSegment.setStartTime(LocalTime.of(12, 0));
            businessHoursSegment.setEndTime(LocalTime.of(12, 1));
        } else {
            if(LocalTime.parse(endTime)
                    .isAfter(
                            LocalTime.parse(startTime))){
                businessHoursSegment.setDayOff(false);
                businessHoursSegment.setStartTime(LocalTime.parse(startTime));
                businessHoursSegment.setEndTime(LocalTime.parse(endTime));
            } else {
                return null;
            }
        }

        return businessHoursSegment;
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

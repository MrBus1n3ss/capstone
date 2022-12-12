package com.wgu.scheduling;

import com.wgu.scheduling.controller.UserController;
import com.wgu.scheduling.model.CurrentUser;
import com.wgu.scheduling.model.Role;
import com.wgu.scheduling.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SchedulingApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTests {

    @Autowired
    UserController userController;

//    Mock user to create test
    private User createUser(){
        User user = new User();
        user.setUserName("deleteMe");
        user.setPassword("12345");
        user.setEmail("abc@abc.edu");
        user.setRoleId(1);
        user.setBusinessId(1);
        user.setActive(true);
        user.setMondayStartTime(LocalTime.of(9, 30));
        user.setMondayEndTime(LocalTime.of(12, 0));
        user.setMondayDayOff(false);
        user.setTuesdayStartTime(LocalTime.of(9, 30));
        user.setTuesdayEndTime(LocalTime.of(12, 0));
        user.setTuesdayDayOff(false);
        user.setWednesdayStartTime(LocalTime.of(9, 30));
        user.setWednesdayEndTime(LocalTime.of(12, 0));
        user.setWednesdayDayOff(false);
        user.setThursdayStartTime(LocalTime.of(9, 30));
        user.setThursdayEndTime(LocalTime.of(12, 0));
        user.setThursdayDayOff(false);
        user.setFridayStartTime(LocalTime.of(9, 30));
        user.setFridayEndTime(LocalTime.of(12, 0));
        user.setFridayDayOff(false);
        user.setSaturdayStartTime(LocalTime.of(9, 30));
        user.setSaturdayEndTime(LocalTime.of(12, 0));
        user.setSaturdayDayOff(false);
        user.setSundayStartTime(LocalTime.of(9, 30));
        user.setSundayEndTime(LocalTime.of(12, 0));
        user.setSundayDayOff(false);
        user.setCreatedBy("system");
        user.setCreatedAt(Timestamp.from(Instant.now()));
        user.setModifiedAt(Timestamp.from(Instant.now()));
        user.setModifiedBy("system");

        userController.save(user);

        return user;
    }

//    @Test
//    public void authorizedUser_pass() {
//        // checks the save function of user
//        // checks if the password is the same as the password in the database
//        // checks the delete function
//
//        User testUser = createUser();
//        User testUserLoginUser = userController.findByUserName(testUser.getUserName()).getBody();
//        assert testUserLoginUser != null;
//        assertEquals(testUser.getPassword(), testUserLoginUser.getPassword());
//        userController.delete(testUserLoginUser.getId());
//
//    }
//
//    @Test
//    public void authorizedUser_fail() {
//        // checks the save function of user
//        // checks if the password is the not the same as the password in the database
//        // checks the delete function
//
//        User testUser = createUser();
//        testUser.setPassword("12345");
//        User testUserLoginUser = userController.findByUserName(testUser.getUserName()).getBody();
//        assert testUserLoginUser != null;
//        assertNotEquals(DigestUtils.sha256Hex(testUser.getPassword()), testUserLoginUser.getPassword());
//        userController.delete(testUserLoginUser.getId());
//    }

}

package com.wgu.scheduling.controller;

import com.wgu.scheduling.model.Role;
import com.wgu.scheduling.model.User;
import com.wgu.scheduling.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getOne(@PathVariable Long id) {
        log.info("Get user:" + id);
        try {
            return new ResponseEntity<>(userService.getOne(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to get user:" + id);
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getRoles() {
        log.info("Get roles");
        try {
            return new ResponseEntity<>(userService.getRoles(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to get roles");
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        log.info("Get role:" + id);
        try {
            return new ResponseEntity<>(userService.getRoleById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to get role:" + id);
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        log.info("Get users");
        try {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Failed to get all users");
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<User> findByUserName(@PathVariable String userName) {
        log.info("Get user: " + userName);
        try {
            return new ResponseEntity<>(userService.findByUserName(userName), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to get user:" + userName);
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/{id}")
    public HttpStatus update(@RequestBody User user, @PathVariable Long id) {
        log.info("Update user:" + id);
        try {
            userService.update(user, id);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Failed to update user:" + id);
            log.error(e);
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/users")
    public HttpStatus save(@RequestBody User user) {
        log.info("Create new user");
        try {
            userService.save(user);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Failed to create new user");
            log.error(e);
            return HttpStatus.BAD_REQUEST;
        }

    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete user:" + id);
        try {
            userService.delete(id);
            log.info("Delete user:" + id + ":successful");
        } catch (Exception e) {
            log.info("Delete user:" + id + ":failed");
            log.info(e);
        }
    }
}

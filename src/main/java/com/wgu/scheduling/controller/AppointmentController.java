package com.wgu.scheduling.controller;

import com.wgu.scheduling.model.Appointment;
import com.wgu.scheduling.model.CurrentUser;
import com.wgu.scheduling.service.AppointmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getOne(@PathVariable Long id) {
        log.info("Get appointment:" + id);
        try {
            return new ResponseEntity<>(appointmentService.getOne(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to get appointment:" + id);
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAll() {
        log.info("Get appointments");
        try {
            return new ResponseEntity<>(appointmentService.getAll(CurrentUser.id), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Failed to get all appointments");
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/appointments/all")
    public ResponseEntity<List<Appointment>> getAllForReport() {
        log.info("Get appointments");
        try {
            return new ResponseEntity<>(appointmentService.all(), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Failed to get all appointments");
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/appointments/day")
    public ResponseEntity<List<Appointment>> getByDay() {
        log.info("Get appointments");
        try {
            return new ResponseEntity<>(appointmentService.getByDay(CurrentUser.id), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Failed to get all appointments");
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/appointments/week")
    public ResponseEntity<List<List<Appointment>>> getAllByWeekAndMonth() {
        log.info("Get appointments");
        try {
            return new ResponseEntity<>(appointmentService.getAllByWeekAndMonth(CurrentUser.id), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Failed to get all appointments");
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/appointments/{id}")
    public HttpStatus update(@RequestBody Appointment appointment, @PathVariable Long id) {
        log.info("Update appointment:" + id);
        try {
            appointmentService.update(appointment, id);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Failed to update customer:" + id);
            log.error(e);
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/appointments")
    public HttpStatus save(@RequestBody Appointment appointment) {
        log.info("Create new appointment");
        try {
            appointmentService.save(appointment);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Failed to create new appointment");
            log.error(e);
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping("/appointments/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete appointment:" + id);
        try {
            appointmentService.delete(id);
            log.info("Delete appointment:" + id + ":successful");
        } catch (Exception e) {
            log.info("Delete appointment:" + id + ":failed");
            log.info(e);
        }
    }

}

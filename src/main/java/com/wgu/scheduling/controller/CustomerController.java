package com.wgu.scheduling.controller;

import com.wgu.scheduling.model.Customer;
import com.wgu.scheduling.service.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getOne(@PathVariable Long id) {
        log.info("Get customer:" + id);
        try {
            return new ResponseEntity<>(customerService.getOne(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Failed to get customer:" + id);
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAll() {
        log.info("Get customers");
        try {
            return new ResponseEntity<>(customerService.getAll(), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Failed to get all customers");
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customers/month")
    public ResponseEntity<List<Customer>> getAllByMonth() {
        log.info("Get customers");
        try {
            return new ResponseEntity<>(customerService.getAllByMonth(), HttpStatus.OK);
        }catch (Exception e) {
            log.error("Failed to get all customers");
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/customers/{id}")
    public HttpStatus update(@RequestBody Customer customer, @PathVariable Long id) {
        log.info("Update customer:" + id);
        try {
            customerService.update(customer, id);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Failed to update customer:" + id);
            log.error(e);
            return HttpStatus.BAD_REQUEST;
        }

    }

    @PostMapping("/customers")
    public HttpStatus save(@RequestBody Customer customer) {
        log.info("Create new customer");
        try {
            customerService.save(customer);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Failed to create new customer");
            log.error(e);
            return HttpStatus.BAD_REQUEST;
        }

    }

    @DeleteMapping("/customers/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete customer:" + id);
        try {
            customerService.delete(id);
            log.info("Delete customer:" + id + ":successful");
        } catch (Exception e) {
            log.info("Delete customer:" + id + ":failed");
            log.info(e);
        }
    }

}

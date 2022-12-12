package com.wgu.scheduling.service;

import com.wgu.scheduling.model.Customer;
import com.wgu.scheduling.model.Phone;
import com.wgu.scheduling.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getOne(Long id) {
        Customer customer = customerRepository.getById(id);
        customer.setAddress(customerRepository.getAddress(customer.getId()));
        List<Phone> phones = customerRepository.getPhone(customer.getId());
        customer.setMobilePhone(null);
        customer.setHomePhone(null);
        customer.setWorkPhone(null);
        for(Phone phone: phones) {
            if(phone.getPhoneName().equals("HOME")) {
                customer.setHomePhone(phone);
            }
            if(phone.getPhoneName().equals("WORK")) {
                customer.setWorkPhone(phone);
            }
            if(phone.getPhoneName().equals("MOBILE")) {
                customer.setMobilePhone(phone);
            }
        }
        return customer;
    }

    public List<Customer> getAll() {
        List<Customer> customers = customerRepository.findAll();
        Phone emptyPhone = new Phone();
        emptyPhone.setPhoneName("");
        emptyPhone.setPhoneNumber("");
        for(Customer customer: customers) {
            customer.setAddress(customerRepository.getAddress(customer.getId()));
            List<Phone> phones = customerRepository.getPhone(customer.getId());
            customer.setMobilePhone(emptyPhone);
            customer.setHomePhone(emptyPhone);
            customer.setWorkPhone(emptyPhone);
            for(Phone phone: phones) {
                if(phone.getPhoneName().equals("HOME")) {
                    customer.setHomePhone(phone);
                }
                if(phone.getPhoneName().equals("WORK")) {
                    customer.setWorkPhone(phone);
                }
                if(phone.getPhoneName().equals("MOBILE")) {
                    customer.setMobilePhone(phone);
                }
            }
        }
        return customers;
    }

    public List<Customer> getAllByMonth() {
        List<Customer> customers = customerRepository.findAll();
        List<Customer> customersByMonth = new ArrayList<>();
        for(Customer customer: customers) {
            Timestamp ts = customer.getCreatedAt();
            if (LocalDateTime.now().getMonth() == ts.toLocalDateTime().getMonth()) {
                customersByMonth.add(customer);

            }
        }
        return customersByMonth;
    }

    public void update(Customer updatedCustomer, Long id) {
        Customer customer = customerRepository.getById(id);

        customer.setId(id);
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setAddress(updatedCustomer.getAddress());
        customer.setMobilePhone(updatedCustomer.getMobilePhone());
        customer.setHomePhone(updatedCustomer.getHomePhone());
        customer.setWorkPhone(updatedCustomer.getWorkPhone());
        customer.setModifiedAt(updatedCustomer.getModifiedAt());
        customer.setModifiedBy(updatedCustomer.getModifiedBy());

        customerRepository.update(customer);
    }

    public void save(Customer customer) { customerRepository.save(customer); }

    public void delete(Long id) { customerRepository.deleteById(id); }

}

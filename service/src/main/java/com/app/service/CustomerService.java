package com.app.service;

import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Customer;
import com.app.repo.generic.CountryRepository;
import com.app.repo.generic.CustomerRepository;
import com.app.service.valid.CustomerValidator;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final CustomerValidator customerValidator;





    public Customer addCustomerToDB(Customer customer) {

        if (customer == null) {
            throw new AppException("object is null");
        }
        return customerRepository.addOrUpdate(customer)
                .orElseThrow(() -> new AppException("cannot insert customer"));
    }

    public Customer createCustomer() {

        Integer age = DataManager.getInt("PRESS AGE");
        String name = DataManager.getLine("PRESS NAME");
        String sureName = DataManager.getLine("PRESS SURNAME");

        Country country = countryRepository.findByName(DataManager.getLine("GIVE COUNTRY NAME")).orElseThrow(() -> new AppException("cannot insert country"));

        Customer customer = Customer.builder().age(age).name(name).surname(sureName).country(country).build();

        customerValidator.validate(customer);
        if (customerValidator.hasErrors()) {
            throw new AppException("ERROR IN CUSTOMER VALIDATION");
        }

        Optional<Customer> customerBySurname = customerRepository.findByName(sureName);
        if(customerBySurname.isPresent() && customerBySurname.get().getName().equals(customer.getName())){
            throw new AppException("THERE IS RECORD WITH SIMILAR NAME AND SURNAME IN DB");
        }
        return addCustomerToDB(customer);
    }

}


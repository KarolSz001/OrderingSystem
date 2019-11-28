package com.app.service;

import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Customer;
import com.app.repo.generic.CustomerRepository;
import com.app.repo.impl.CustomerRepositoryImpl;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.CustomerValidator;


import java.util.Optional;


public class CustomerService {

    private final CustomerRepository customerRepository = new CustomerRepositoryImpl("HBN");
    private final CountryService countryService = new CountryService();
    private final CustomerValidator customerValidator = new CustomerValidator();


    public Customer addCustomerToDB(Customer customer) {

        if (customer == null) {
            throw new AppException("object is null");
        }
        System.out.println("New customer RECORD --->>>" + customer);
        return customerRepository.addOrUpdate(customer)
                .orElseThrow(() -> new AppException("cannot insert customer"));
    }

    public Customer createCustomer() {
        String name = DataManager.getLine("PRESS NAME");
        String sureName = DataManager.getLine("PRESS SURNAME");
        Integer age = DataManager.getInt("PRESS AGE");
        Country country = countryService.findCountryInDB();

        Customer customer = Customer.builder().age(age).name(name).surname(sureName).country(country).build();
        customerValidator.validate(customer);
        if (customerValidator.hasErrors()) {
            throw new AppException("ERROR IN CUSTOMER VALIDATION");
        }

        Optional<Customer> customerBySurname = customerRepository.findByName(sureName);
        if (customerBySurname.isPresent() && customerBySurname.get().getName().equals(customer.getName())) {
            throw new AppException("THERE IS RECORD WITH SIMILAR NAME AND SURNAME IN DB");
        }

        return addCustomerToDB(customer);
    }

    public void showAllCustomersInDB() {
        customerRepository.findAll().forEach(System.out::print);
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findOne(id).orElseThrow(() -> new AppException("THERE IS RECORD IN DB"));

    }


}


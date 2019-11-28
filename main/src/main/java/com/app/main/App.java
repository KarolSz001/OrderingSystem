package com.app.main;

import com.app.model.*;
import com.app.model.enums.EPayment;
import com.app.repo.impl.CountryRepositoryImpl;
import com.app.repo.impl.TradeRepositoryImpl;
import com.app.repo.impl.*;
import com.app.service.CategoryService;
import com.app.service.ControlService;

import java.lang.Error;
import java.math.BigDecimal;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) {


       /* var countryRepo = new CountryRepositoryImpl("HBN");
        var tradeRepo = new TradeRepositoryImpl("HBN");
        var customerRepo = new CustomerRepositoryImpl("HBN");
        var productRepo = new ProductRepositoryImpl("HBN");
        var customerOrderRepo =  new CustomerOrderRepositoryImpl("HBN");
        var errorRepo = new ErrorRepositoryImpl("HBN");
        var paymentRepository = new PaymentRepositoryImpl("HBN");
        var categoryRepo = new CategoryRepositoryImpl("HBN");
        var producerRepo = new ProductRepositoryImpl("HBN");
        var shopRepo = new ShopRepositoryImpl("HBN");

        tradeRepo.addOrUpdate(Trade.builder().name("AAAA").build());
        customerRepo.addOrUpdate(Customer.builder().name("KAROL").age(12).surname("SZOT").build());
        System.out.println(customerRepo.findAll());
        countryRepo.findAll();

        productRepo.addOrUpdate(Product.builder().name("MILK").price(new BigDecimal(100)).build());
        productRepo.findAll().forEach(System.out::println);

        customerOrderRepo.addOrUpdate(CustomerOrder.builder().date(LocalDate.now()).discount(new BigDecimal(2)).quantity(20).build());
        customerOrderRepo.findAll().forEach(System.out::print);

       *//* errorRepo.addOrUpdate(Error.builder().date(LocalDate.now()).message("HELLOW").build());
        customerOrderRepo.findAll().forEach(System.out::print);*//*

        paymentRepository.addOrUpdate(Payment.builder().payment(EPayment.CARD).build());
        paymentRepository.findAll().forEach(System.out::print);

        categoryRepo.addOrUpdate(Category.builder().name("BOOK").build());
        categoryRepo.findAll();
//        producerRepo.addOrUpdate(Producer.builder().name("ALMA").build());
        producerRepo.findAll().forEach(System.out::print);
        shopRepo.addOrUpdate(Shop.builder().name("BIEDRA").build());
        producerRepo.findAll().forEach(System.out::print);



        final String appName = "\n TransferGoodApplication v1.0 Karol Szot 06.10.2019 ";
        System.out.println(appName);
        ControlService controlService = new ControlService();
        controlService.controlRun();*/

     /*   var categoryRepo = new CategoryRepositoryImpl("HBN");
        categoryRepo.deleteAll();
        CategoryService categoryService = new CategoryService();
        categoryService.printAllRecordsInCategories();*/


        // order service , decrease numer in stock !!
        ControlService controlService =  new ControlService();
        controlService.controlRun();
    }
}

package com.app.service;


public class ControlService {

    CategoryService categoryService;
    TradeService tradeService;
    CountryService countryService;
    CustomerService customerService;
    ProducerService producerService;
    ProductService productService;







    public void controlRun(){
        initDataInDB();


    }

    public void initDataInDB(){

        categoryService = new CategoryService();
        tradeService = new TradeService();
        countryService =  new CountryService();
        customerService.createCustomer();
        producerService.createProducer();
        productService.createProduct();




    }
}

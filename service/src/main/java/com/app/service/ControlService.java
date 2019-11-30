package com.app.service;


public class ControlService {

    CategoryService categoryService = new CategoryService();
    TradeService  tradeService = new TradeService();
    CountryService countryService = new CountryService();
    ShopService shopService = new ShopService();
    CustomerService customerService;
    ProducerService producerService = new ProducerService();
    ProductService productService;

    

    public void controlRun(){
        initDataInDB();


    }

    public void initDataInDB(){

        countryService.countryInit();
        categoryService.categoryInit();
        tradeService.tradeInit();
        shopService.shopInit();
        producerService.producerInit();
        productService.productInit();



       /* producerService.createProducer();
        productService.createProduct();


        /////////////////////////////////////////
        customerService.createCustomer();*/




    }
}

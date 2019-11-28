package com.app.service;


public class ControlService {

    CategoryService categoryService = new CategoryService();
    TradeService  tradeService = new TradeService();
    CountryService countryService = new CountryService();
    CustomerService customerService;
    ProducerService producerService;
    ProductService productService;







    public void controlRun(){
        initDataInDB();


    }

    public void initDataInDB(){

        countryService.countryDataInit();
        categoryService.categoryDataInit();
        tradeService.tradeDataInit();


       /* producerService.createProducer();
        productService.createProduct();


        /////////////////////////////////////////
        customerService.createCustomer();*/




    }
}

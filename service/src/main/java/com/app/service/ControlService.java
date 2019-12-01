package com.app.service;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ControlService {
    CategoryService categoryService = new CategoryService();
    TradeService  tradeService = new TradeService();
    CountryService countryService = new CountryService();

    ShopService shopService = new ShopService();
    ProducerService producerService = new ProducerService();
    ProductService productService = new ProductService();
    StockService stockService = new StockService();

    CustomerService customerService = new CustomerService();
    OrderService orderService = new OrderService();

    

    public void controlRun(){
        initDataInDB();
    }

    public void initDataInDB(){
        categoryService.categoryInit();
        tradeService.tradeInit();
        countryService.countryInit();

        shopService.shopInit();
        producerService.producerInit();
        productService.productInit();
        stockService.stockInit();

        customerService.customerInit();
        orderService.orderInit();





    }
}

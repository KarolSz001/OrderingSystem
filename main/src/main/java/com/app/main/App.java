package com.app.main;

import com.app.model.Trade;
import com.app.repo.impl.*;
import com.app.service.*;
import com.app.service.valid.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class App {

    private final static String persistenceUnit = "HBN";


    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        sb.append(" ----------------------------------------------------------------------------- \n");
        sb.append(" OrderingSys v1.0 07.12.2019 \n ");
        sb.append(" Karol Szot \n");
        sb.append(" ----------------------------------------------------------------------------- \n");
        System.out.println(sb.toString());


        var categoryRepository = new CategoryRepositoryImpl(persistenceUnit);
        var categoryValidator = new CategoryValidator();
        var categoryService = new CategoryService(categoryRepository, categoryValidator);

        var tradeRepository = new TradeRepositoryImpl(persistenceUnit);
        var tradeValidator = new TradeValidator();
        var tradeService = new TradeService(tradeRepository, tradeValidator);

        var countryRepository = new CountryRepositoryImpl(persistenceUnit);
        var countryValidator = new CountryValidator();
        var countryService = new CountryService(countryRepository, countryValidator);

        var shopRepository = new ShopRepositoryImpl(persistenceUnit);
        var shopValidator = new ShopValidator();
        var shopService = new ShopService(shopRepository, countryService, shopValidator);

        var producerRepository = new ProducerRepositoryImpl(persistenceUnit);
        var producerValidator = new ProducerValidator();
        var producerService = new ProducerService(producerRepository, tradeService, producerValidator, countryService);

        var productRepository = new ProductRepositoryImpl(persistenceUnit);
        var productValidator = new ProductValidator();
        var productService = new ProductService(productValidator, productRepository, categoryService, producerService);

        var stockRepository = new StockRepositoryImpl(persistenceUnit);
        var stockService = new StockService(productService, shopService, stockRepository);

        var customerRepository = new CustomerRepositoryImpl(persistenceUnit);
        var customerValidator = new CustomerValidator();
        var customerService = new CustomerService(countryService, customerRepository, customerValidator);

        var customerOrderRepository = new CustomerOrderRepositoryImpl(persistenceUnit);
        var orderValidator = new OrderValidator();
        var orderService = new OrderService(productService, stockService, customerOrderRepository, orderValidator, customerService);

        var solutionService = new SolutionService(categoryService, tradeService, shopService, producerService, productService, stockService, customerService, orderService);

        var controlService = new ControlService(categoryService, tradeService, countryService, shopService, producerService, productService, stockService, customerService, orderService, solutionService);

//        controlService.controlLoop();
//        productService.solution1();
//        orderService.orderInitAuto();
//        orderService.solution2("GERMAN",0,100);
//        productService.solution3(GuaranteeComponents.MONEY_BACK);
//            stockService.solution4();
//            stockService.solution5("FOOD",10);
//          orderService.solution6(LocalDate.of(2019,12,7),LocalDate.now(),new BigDecimal(250));

        orderService.solution7("KAROL","CICHON","GERMAN");
//        orderService.solution8();
        orderService.solution8a();

    }
}

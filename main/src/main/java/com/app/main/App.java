package com.app.main;

import com.app.repo.impl.*;
import com.app.service.*;
import com.app.service.valid.*;

public class App {

    public static void main(String[] args) {

        String persistenceUnit = "HBN";

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
        var customerService = new CustomerService( countryService, customerRepository, customerValidator);

        var customerOrderRepository = new CustomerOrderRepositoryImpl(persistenceUnit);
        var orderValidator = new OrderValidator();
        var orderService = new OrderService(productService, stockService, customerOrderRepository, orderValidator, customerService);

        var solutionService = new SolutionService();

        var controlService = new ControlService(categoryService, tradeService, countryService, shopService, producerService, productService, stockService, customerService, orderService, solutionService);

        controlService.controlRun();


    }
}

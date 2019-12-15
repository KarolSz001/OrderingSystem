package com.app.main;

import com.app.repo.impl.*;
import com.app.service.*;
import com.app.service.valid.*;

public class App {

    private final static String persistenceUnit = "HBN";


    public static void main(String[] args) {


        StringBuilder sb = new StringBuilder();
        sb.append(" ----------------------------------------------------------------------------- \n");
        sb.append(" OrderingSys v1.0 07.12.2019 \n ");
        sb.append(" Karol Szot \n");
        sb.append(" ----------------------------------------------------------------------------- \n");
        System.out.println(sb.toString());
        /////////////////////////////////////////////////////////////////////////////////////////////////

        var categoryRepository = new CategoryRepositoryImpl(persistenceUnit);
        var categoryService = new CategoryService(categoryRepository);

        var tradeRepository = new TradeRepositoryImpl(persistenceUnit);
        var tradeService = new TradeService(tradeRepository);

        var countryRepository = new CountryRepositoryImpl(persistenceUnit);
        var countryService = new CountryService(countryRepository);

        var shopRepository = new ShopRepositoryImpl(persistenceUnit);
        var shopService = new ShopService(shopRepository, countryService);

        var producerRepository = new ProducerRepositoryImpl(persistenceUnit);
        var producerService = new ProducerService(producerRepository, tradeService, countryService);

        var productRepository = new ProductRepositoryImpl(persistenceUnit);
        var productService = new ProductService(productRepository, categoryService, producerService);

        var stockRepository = new StockRepositoryImpl(persistenceUnit);
        var stockService = new StockService(productService, shopService, stockRepository, tradeService);

        var customerRepository = new CustomerRepositoryImpl(persistenceUnit);
        var customerService = new CustomerService(countryService, customerRepository);

        var customerOrderRepository = new CustomerOrderRepositoryImpl(persistenceUnit);
        var orderService = new OrderService(productService, stockService, customerOrderRepository, customerService);

        var controlService = new ControlService(categoryService, tradeService, countryService, shopService, producerService, productService, stockService, customerService, orderService);
//        controlService.controlLoop();

        stockService.clearDataFromStock();
        stockService.stockDataInitAutoFill();
        stockService.stockDataInitAutoFill();







    }
}

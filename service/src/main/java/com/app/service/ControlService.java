package com.app.service;


import com.app.exception.AppException;
import com.app.model.enums.GuaranteeComponents;
import com.app.service.dataUtility.DataManager;

import java.math.BigDecimal;
import java.time.LocalDate;


public class ControlService {

    private final static StringBuilder sb = new StringBuilder();

    CategoryService categoryService;
    TradeService tradeService;
    CountryService countryService;
    ShopService shopService;
    ProducerService producerService;
    ProductService productService;
    StockService stockService;
    CustomerService customerService;
    OrderService orderService;


    public ControlService(CategoryService categoryService, TradeService tradeService, CountryService countryService, ShopService shopService, ProducerService producerService, ProductService productService, StockService stockService, CustomerService customerService, OrderService orderService) {
        this.categoryService = categoryService;
        this.tradeService = tradeService;
        this.countryService = countryService;
        this.shopService = shopService;
        this.producerService = producerService;
        this.productService = productService;
        this.stockService = stockService;
        this.customerService = customerService;
        this.orderService = orderService;

    }


    private void initSolution() {

        printSolutionDescription();

        int choice = DataManager.getInt("PRESS NUMBER OF RESULT YOU WANT TO GET");
        switch (choice) {
            case 1: {
                productService.solution1();
                ;
                break;
            }
            case 2: {
                orderService.solution2("GERMAN", 0, 100);
                ;
                break;
            }
            case 3: {
                productService.solution3(GuaranteeComponents.MONEY_BACK);
                break;
            }
            case 4: {
                stockService.solution4();
                break;
            }
            case 5: {
                stockService.solution5("FOOD", 10);
                break;
            }
            case 6: {
                orderService.solution6(LocalDate.of(2019, 12, 7), LocalDate.now(), new BigDecimal(250));
                break;
            }
            case 7: {
                orderService.solution7("KAROL", "CICHON", "GERMAN");
                break;
            }
            case 8: {
                orderService.solution8a();
                break;
            }
        }
    }

    private void printSolutionDescription() {

        sb.setLength(0);
        sb.append("-----------------------------------------------------------------------------\n");
        sb.append("1. Download from the database full information " +
                "about the products with the highest price in each category");

        sb.append("2. Downloading from the database a list of all " +
                "products that were ordered by customers from a country");

        sb.append("3. Retrieve from the database a list of products that is covered by the warranty");

        sb.append("4. Retrieving from the database a list of stores that have products in the warehouse" +
                " whose country of origin is different from the countries in which the store's branches are present.");

        sb.append("5. Download from the database of manufacturers with the name of the industry given " +
                "by the user, who produced products with a total number of pieces greater than the number provided by the user. ");

        sb.append("6. Downloading from the database of orders that was placed within the date range from the user " +
                "about the amount of the order (including the discount)");

        sb.append("7. Retrieving from the database a list of products ordered by the customer ");

        sb.append("8. Retrieve from the database a list of those customers who have ordered at least one product from the same country as the customer ");
        sb.append("-----------------------------------------------------------------------------\n");

        System.out.println(sb.toString());
    }

    private void initDataAuto() {

        categoryService.categoryDataInitAutoFill();
        tradeService.tradeDataInitAutoFill();
        countryService.countryDataInitAutoFill();

        shopService.shopDataInitAutoFill();
        producerService.producerInitAuto();
        productService.productInitAuto();
        stockService.stockDataInitAutoFill();

        customerService.customerDataInitAutoFill();
        orderService.orderInitAuto();

    }

    private void initDataInDB() {

        try {
            System.out.println("\n --------------------->>>>>>WELCOME IN ORDERING SYSTEM MENU APPLICATION<<<<------------------------------- ");

            categoryService.categoryInit();
            tradeService.tradeInit();
            countryService.countryInit();

            shopService.shopInit();
            producerService.producerInit();
            productService.productInit();
            stockService.stockInit();

            customerService.customerInit();
            orderService.orderInit();

        } catch (AppException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    public void controlLoop() {

        boolean loopOn = true;
        while (loopOn) {

            startMenu();
            Integer read = DataManager.getInt(" PRESS NUMBER TO MAKE A CHOICE ");

            switch (read) {
                case 0: {
                    printExit();
                    return;
                }
                case 1: {
                    initDataInDB();
                    break;
                }
                case 2: {
                    initDataAuto();
                    break;
                }
                case 3: {
                    initSolution();
                    break;
                }
            }
        }
    }


    private void printExit() {
        System.out.println(" EXIT APPLICATION - GOODBYE \n");
    }

    private void startMenu() {
        sb.setLength(0);
        sb.append("-----------------------------------------------------------------------------\n");
        sb.append(" MAIN LOOP MENU \n");
        sb.append(" 0 - EXIT PROGRAM \n");
        sb.append(" 1 - INITIALIZATION DATA IN DATABASE BY USER \n");
        sb.append(" 2 - INITIALIZATION DATA IN DATABASE AUTOMATE GENERATE DATA \n");
        sb.append(" 3 - SEND QUERY \n");
        sb.append("-----------------------------------------------------------------------------\n");
        System.out.println(sb.toString());
    }
}

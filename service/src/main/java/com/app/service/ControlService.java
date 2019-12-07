package com.app.service;


import com.app.exception.AppException;
import com.app.service.dataUtility.DataManager;


public class ControlService {

        CategoryService categoryService = new CategoryService();
    TradeService tradeService = new TradeService();
    CountryService countryService = new CountryService();
////    ///////////////////////////////////////////////////////////
    ShopService shopService = new ShopService();
    ProducerService producerService = new ProducerService();
    ProductService productService = new ProductService();
    StockService stockService = new StockService();
//    //////////////////////////////////////////////////////////////
    CustomerService customerService = new CustomerService();
    OrderService orderService = new OrderService();
    //////////////////////////////////
    SolutionService solutionService = new SolutionService();


    public void controlRun() {
        initDataInDB();
//        initSolution();
    }

    public void initSolution() {

        System.out.println("1. Download from the database full information about the products with the highest price in each category. " +
                "The information includes the product name, product price, product category, manufacturer's name, " +
                "country where the product was manufactured and the number of items in which this product was ordered.");

        System.out.println("2. Downloading from the database a list of all products that were ordered by customers from a country " +
                "with the name given by the user and age within the range specified by the user. Products should be ordered in descending order of price. " +
                "The information includes the product name, product price, product category, manufacturer's name and country in which the product was produced. ");

        System.out.println("3. Retrieve from the database a list of products that is covered by the warranty and which under the warranty have services provided by the user. " +
                "Group these products by category. ");

        System.out.println("4. Retrieving from the database a list of stores that have products in the warehouse whose country of origin is different from the countries in " +
                "which the store's branches are present.");

        System.out.println("5. Download from the database of manufacturers with the name of the industry given by the user, who produced products with a total number of pieces" +
                " greater than the number provided by the user. ");

        System.out.println("6. Downloading from the database of orders that was placed within the date range from the user about the amount of the order (including the discount) \"+\n" +
                "                 \"greater than the value provided by the user.");

        System.out.println("7. Retrieving from the database a list of products ordered by the customer with the name, surname and name of the country of origin taken from the user. \"+\n" +
                "                 \"Products should be grouped according to the producer who produced the product.");

        System.out.println("8. Retrieve from the database a list of those customers who have ordered at least one product from the same country as the customer. Customer information " +
                                "should include name, surname, age, name of the customer's country of origin, and quantity of products ordered from a country other than " +
                                    "the country of the customer.");

        int choice = DataManager.getInt("PRESS NUMBER OF RESULT YOU WANT TO GET");

        switch (choice){
            case 1:{
             solutionService.solution1();
                break;
            }
            case 2:{
                solutionService.solution2();
                break;
            }
            case 3:{
                solutionService.solution3();
                break;
            }
            case 4:{
                solutionService.solution4();
                break;
            }
            case 5:{
                solutionService.solution5();
                break;
            }
            case 6:{
                solutionService.solution6();
                break;
            }
            case 7:{
                solutionService.solution7();
                break;
            }
            case 8:{
                solutionService.solution8();
                break;
            }
        }




    }

    public void initDataInDB() {
        try {
////////////////////////////////////////
//            categoryService.categoryInit();
//            tradeService.tradeInit();
//        countryService.countryInit();
///////////////////////////////////////////////
//        shopService.shopInit();
//        producerService.producerInit();
//        productService.productInit();
//        stockService.stockInit();
//////////////////////////////////////////////
//        customerService.customerInit();
            orderService.orderInit();
/////////////////////////////////////////////
        } catch (AppException e) {
            e.printStackTrace();
            e.getMessage();
        }

    }
}

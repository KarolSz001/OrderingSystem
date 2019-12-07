package com.app.service;

import com.app.exception.AppException;
import com.app.model.*;
import com.app.model.enums.EPayment;
import com.app.repo.generic.CustomerOrderRepository;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.OrderValidator;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public class OrderService {

    private final ProductService productService;
    private final StockService stockService;
    private final CustomerOrderRepository customerOrderRepository;
    private final OrderValidator orderValidator;
    private final CustomerService customerService;


    public OrderService(ProductService productService, StockService stockService, CustomerOrderRepository customerOrderRepository, OrderValidator orderValidator, CustomerService customerService) {
        this.productService = productService;
        this.stockService = stockService;
        this.customerOrderRepository = customerOrderRepository;
        this.orderValidator = orderValidator;
        this.customerService = customerService;
    }

    public CustomerOrder addOrderToDB(CustomerOrder order) {
        if (order == null) {
            throw new AppException("object is null");
        }
        return customerOrderRepository.addOrUpdate(order).orElseThrow(() -> new AppException("NO RECORD IN DB"));
    }


    public void orderInit() {

        String answer = DataManager.getLine("WELCOME TO PRODUCT DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA AUTOMATE OR N IF YOU WANNA FILL THEM IN MANUAL");
        if (answer.toUpperCase().equals("Y")) {
            orderInitAuto();
        } else {
            orderInitManual();
        }
    }

    void orderInitAuto() {
        generateOrderAutoMode();
        printOrderRecordsFromDB();
    }

    private void printOrderRecordsFromDB() {
        customerOrderRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    private void orderInitManual() {
        System.out.println("LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF PRODUCERS YOU WANNA ADD TO DB");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleOrderRecordCreator();
        }
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS OF PRODUCTS FROM DB");
        printOrderRecordsFromDB();
    }

    private void generateOrderAutoMode() {
        for (int i = 1; i <= 1; i++) {

            Customer customer = customerService.findRandomCustomerFromDb();
            BigDecimal discount = generateDiscount();
            Product product = productService.findRandomProductFromDb();
            EPayment ePayment = EPayment.findRandomPayment();
            Payment payment = Payment.builder().payment(ePayment).build();
            CustomerOrder customerOrder = CustomerOrder.builder().customer(customer).date(LocalDate.now()).discount(discount).quantity(getNumberOfQuantity()).payment(payment).product(product).build();
            System.out.println("RESULT " + customerOrder);
            addOrderToDB(customerOrder);
        }
    }

    private Integer getNumberOfQuantity() {
        return new Random().nextInt(4);
    }

    private BigDecimal generateDiscount() {
        return BigDecimal.valueOf(new Random().nextDouble()).setScale(1, BigDecimal.ROUND_DOWN);
    }

    public CustomerOrder singleOrderRecordCreator() {
        System.out.println("PRINT ALL CUSTOMERS");
        customerService.showAllCustomersInDB();
        Long idCustomer = DataManager.getLong("PRESS ID CUSTOMER");
        Customer customer = customerService.findCustomerById(idCustomer);
        BigDecimal discount = BigDecimal.valueOf(DataManager.getDouble("PRESS DISCOUNT RANGE 0.0-1.0"));
        Integer quantity = DataManager.getInt("PRESS QUANTITY");
        EPayment ePayment = EPayment.values()[DataManager.getInt("CHOOSE METHOD TO PAY FROM 0 CASH, 1 CARD, 2 MONEY_TRANSFER - PRESS NUMBER")];
        Payment payment = Payment.builder().payment(ePayment).build();
        productService.showAllProductsInDB();
        Product product = productService.findProductByName(DataManager.getLine("PRESS NAME OF PRODUCT"));
        decreaseQuantityOfProductInStock(product.getName(), quantity);

        CustomerOrder order = CustomerOrder.builder().customer(customer).date(LocalDate.now()).discount(discount).quantity(quantity).payment(payment).product(product).build();

        orderValidator.validate(order);
        if (orderValidator.hasErrors()) {
            throw new AppException("ERROR IN PRODUCT VALIDATION");
        }
        return addOrderToDB(order);
    }

    private void decreaseQuantityOfProductInStock(String productName, Integer quantity) {
        Long idProductInStock = productService.getIdProductInStock(productName);
        Stock stock = stockService.findStockInDbById(idProductInStock);
        Integer quantityProductInStock = productService.getQuantityOfProductInStock(productName) - quantity;
        if(quantityProductInStock < 0){
            throw new AppException("THERE IS NO ENOUGH PRODUCT IN STOCK");
        }
        stock.setQuantity(quantityProductInStock);
        stockService.addStockDb(stock);// is it ok, means update quantity in previous record

    }
    /*private boolean isQuantityOrderProductCorrect(CustomerOrder order) {
        return order.getQuantity() <= (getQuantityOfProductInStock(order.getProduct().getName()));
    }*/


}

package com.app.service;

import com.app.exception.AppException;
import com.app.model.*;
import com.app.model.dto.Mapper;
import com.app.model.dto.ProductDTO;
import com.app.model.enums.EPayment;
import com.app.repo.generic.CustomerOrderRepository;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.OrderValidator;


import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    public void orderInitAuto() {
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
        for (int i = 1; i <= 2; i++) {

            Customer customer = customerService.findRandomCustomerFromDb();
            BigDecimal discount = generateDiscount();
            Product product = productService.findRandomProductFromDb();
            EPayment ePayment = EPayment.findRandomPayment();
            Payment payment = Payment.builder().payment(ePayment).build();
            CustomerOrder customerOrder = CustomerOrder.builder().customer(customer).date(LocalDate.now()).discount(discount).quantity(getNumberOfQuantity()).payment(payment).product(product).build();
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
        if (quantityProductInStock < 0) {
            throw new AppException("THERE IS NO ENOUGH PRODUCT IN STOCK");
        }
        stock.setQuantity(quantityProductInStock);
        stockService.addStockDb(stock);// is it ok, means update quantity in previous record

    }
    /*private boolean isQuantityOrderProductCorrect(CustomerOrder order) {
        return order.getQuantity() <= (getQuantityOfProductInStock(order.getProduct().getName()));
    }*/

    public void clearDataFromOrder() {
        customerOrderRepository.deleteAll();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void findProductsByParameters() {

        String name = DataManager.getLine("GIVE CUSTOMER NAME");
        Integer minAge = DataManager.getInt("GIVE MIN AGE");
        Integer maxAge = DataManager.getInt("GIVE MIN AGE");

        customerOrderRepository.findAll()
                .stream()
                .filter(f -> f.getCustomer().getCountry().getName().equals(name) && f.getCustomer().getAge() > minAge && f.getCustomer().getAge() < maxAge)
                .map(CustomerOrder::getProduct)
                .map(Mapper::fromProductToProductDTO)
                .sorted(Comparator.comparing(ProductDTO::getPrice, Comparator.naturalOrder()))
                .forEach(System.out::println);
    }

    public void findOrders() {

        System.out.println("PRESS NUMBER MIN");
        LocalDate min = getDate();
        System.out.println("PRESS NUMBER MAX");
        LocalDate max = getDate();
        BigDecimal price = BigDecimal.valueOf(DataManager.getInt("PRESS PRICE"));

        customerOrderRepository.query6(min, max).stream()
                .filter(f -> f.getProduct().getPrice().subtract(f.getProduct().getPrice().multiply(f.getDiscount())).compareTo(price) > 0)
                .collect(Collectors.toCollection(ArrayList::new))
                .forEach(System.out::println);
    }

    private LocalDate getDate() {
        Integer yy = DataManager.getInt("PRESS YEAR");
        Integer mm = DataManager.getInt("PRESS MONTH");
        Integer dd = DataManager.getInt("PRESS DAY");
        return LocalDate.of(yy, mm, dd);
    }

    public void findProducts() {
        Customer customer = customerService.findRandomCustomerFromDb();
        var surName = customer.getSurname();
        var name = customer.getName();
        var countryName = customer.getName();
        System.out.println("WE LOOKING FOR CUSTOMER " + surName + ":::" + name + ":::" + countryName);
        customerOrderRepository.query7(surName, name, countryName)
                .stream()
                .peek(System.out::println)
                .collect(Collectors.groupingBy(Product::getProducer))
                .forEach((k, v) -> System.out.println("PRODUCER--->>" + k.getName() + ":::" + v));
    }

    public void findCustomers() {
        customerOrderRepository.query8a().stream()
                .collect(Collectors.toMap(
                        CustomerOrder::getCustomer,
                        CustomerOrder::getQuantity,
                        Integer::sum,
                        HashMap::new
                ))

                .forEach((k, v) -> System.out.println(k.getName() + "::" + k.getSurname() + "::::" + (howManyProducts(k) - v)));
    }

    private Integer howManyProducts(Customer customer) {
        return customerOrderRepository.findAll().stream()
                .filter(f -> f.getCustomer().equals(customer))
                .collect(Collectors.toMap(
                        CustomerOrder::getCustomer,
                        CustomerOrder::getQuantity,
                        Integer::sum,
                        HashMap::new
                ))
                .entrySet()
                .stream()
                .findFirst().orElseThrow(() -> new AppException("NO RESULT"))
                .getValue();
    }
}

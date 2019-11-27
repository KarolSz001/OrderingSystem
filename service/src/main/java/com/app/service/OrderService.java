package com.app.service;

import com.app.exception.AppException;
import com.app.model.*;
import com.app.model.enums.EPayment;
import com.app.model.enums.GuaranteeComponents;
import com.app.repo.generic.CustomerOrderRepository;
import com.app.repo.impl.CustomerOrderRepositoryImpl;
import com.app.service.valid.OrderValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderService {

            private final  ProductService productService = new ProductService();
            private final CustomerOrderRepository customerOrderRepository = new CustomerOrderRepositoryImpl("HBN");
            private final OrderValidator orderValidator = new OrderValidator();
            private final CustomerService customerService = new CustomerService();

    public CustomerOrder addOrderToDB(CustomerOrder order) {

        if (order == null) {
            throw new AppException("object is null");
        }
        return customerOrderRepository.addOrUpdate(order).orElseThrow(() -> new AppException("NO RECORD IN DB"));
    }

    public CustomerOrder createOrder() {

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

        productService.decreaseQuantityOfProductInStock(product.getName(), quantity);
        CustomerOrder order = CustomerOrder.builder().customer(customer).date(LocalDate.now()).discount(discount).quantity(quantity).payment(payment).product(product).build();
        orderValidator.validate(order);

        if (orderValidator.hasErrors()) {
            throw new AppException("ERROR IN PRODUCT VALIDATION");
        }
        return addOrderToDB(order);
    }










}

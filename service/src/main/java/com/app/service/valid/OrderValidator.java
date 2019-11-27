package com.app.service.valid;

import com.app.model.CustomerOrder;
import com.app.model.Product;
import com.app.service.ProductService;
import com.app.service.StockService;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderValidator extends AbstractValidator<CustomerOrder> {


    private final ProductService productService = new ProductService();

    @Override
    public Map<String, String> validate(CustomerOrder customerOrder) {

        Map<String, String> err = new HashMap<>();

        if (!isDateCorrect(customerOrder.getDate())) {
            err.put("Error nr 1", "Order Data is valid");
        }
        if (!isDiscountNumberCorrect(customerOrder.getDiscount())) {
            err.put("Error nr 2", "Name is too short");
        }
        if (!isQuantityOrderProductCorrect(customerOrder)) {
            err.put("Error nr 3", "Quantity number is Valid");
        }
        return err;
    }

    private boolean isDateCorrect(LocalDate localDate) {
        return localDate.isEqual(LocalDate.now()) || localDate.isAfter(LocalDate.now());
    }

    private boolean isDiscountNumberCorrect(BigDecimal discount) {
        return discount.compareTo(new BigDecimal(1.0)) < 0 || discount.compareTo(new BigDecimal(0.0)) > 0;

    }

    private boolean isQuantityOrderProductCorrect(CustomerOrder order) {
        return order.getQuantity() <= (productService.getQuantityOfProductInStock(order.getProduct().getName()));

    }


}

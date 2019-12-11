package com.app.service;


import com.app.exception.AppException;
import com.app.model.Producer;
import com.app.model.Product;
import com.app.model.Shop;
import com.app.model.Stock;
import com.app.repo.generic.StockRepository;
import com.app.service.dataUtility.DataManager;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public class StockService {

    private final ProductService productService;
    private final ShopService shopService;
    private final StockRepository stockRepository;

    public StockService(ProductService productService, ShopService shopService, StockRepository stockRepository) {
        this.productService = productService;
        this.shopService = shopService;
        this.stockRepository = stockRepository;
    }

    private Stock addRecordToStock(Stock stock) {

        if (stock == null) {
            throw new AppException("object is null");
        }
        return stockRepository.addOrUpdate(stock).orElseThrow(() -> new AppException("NO FOUND RECORD"));
    }

    public Stock singleStockRecordCreator() {

        System.out.println("List of product in our DataBase");
        productService.showAllProductsInDB();
        Long idProduct = DataManager.getLong("PRESS ID PRODUCT OF YOU CHOICE");
        Product product = productService.findProductById(idProduct);

        System.out.println("List of shops in our DataBase");
        shopService.printAllShopRecordsInDB();
        Long idShop = DataManager.getLong("PRESS ID SHOP OF YOU CHOICE");
        Shop shop = shopService.findShopById(idShop);
        Integer quantityProductInStock = DataManager.getInt("PRESS NUMBER OF PRODUCT IN STOCK");
        Stock stock = Stock.builder().product(product).quantity(quantityProductInStock).shop(shop).build();
        return addRecordToStock(stock);
    }

    public Stock findStockInDbById(Long idStock) {
        return stockRepository.findOne(idStock).orElseThrow(() -> new AppException("NO FOUND RECORD IN DB"));
    }

    public Stock addStockDb(Stock stock) {
        return stockRepository.addOrUpdate(stock).orElseThrow(() -> new AppException("NO FOUND RECORD IN DB"));
    }


    public void stockInit() {

        String answer = DataManager.getLine("WELCOME TO STOCK DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA MANUALLY OR N IF YOU WANNA FILL THEM IN AUTOMATE");
        if (answer.toUpperCase().equals("Y")) {
            stockDataInitAutoFill();
        } else {
            stockDataShopInitManualFill();
        }
    }

    public void printAllStockRecordsInDB() {
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        stockRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    public void stockDataInitAutoFill() {
        generateStockInDBAutoMode();
        printAllStockRecordsInDB();
    }

    private void generateStockInDBAutoMode() {
        for (int i = 1; i < 4; i++) {
            Product product = productService.findRandomProductFromDb();
            Shop shop = shopService.findRandomShopFromDb();
            Stock stock = Stock.builder().product(product).quantity(1).shop(shop).build();
            addStockDb(stock);
        }
    }

    private void stockDataShopInitManualFill() {
        System.out.println("LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF RECORD YOU WANNA ADD TO DB");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleStockRecordCreator();
        }
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        printAllStockRecordsInDB();
    }

    public void clearDataFromStock() {
        stockRepository.deleteAll();
    }

    public void solution1a() {
        stockRepository.query4();
    }

    public void solution4() {
        stockRepository.query4().stream()
//                .peek(s-> System.out.println(Arrays.toString(s)))
                .collect(Collectors.groupingBy(e -> e[0]))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> (Product) e.getKey(),
                        e -> e.getValue().stream().map(m -> (Shop) (m[1])).collect(Collectors.toList())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().filter(f -> !f.getCountry().getName().equals(e.getKey().getProducer().getCountry().getName())).collect(Collectors.toList())
                ))

                .forEach((k, v) -> System.out.println(k.getName() + "::" + k.getProducer().getCountry().getName() + "::::::" + v));
    }

    public void solution5(String tradeName, int minQuantity) {
        stockRepository.query5(tradeName)
                .stream()
                .collect(Collectors.groupingBy(e -> e[0]))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> (Producer) e.getKey(),
                        e -> e.getValue().stream().map(m -> (Integer) (m[1])).reduce(0,Integer::sum)
                ))
                .entrySet()
                .stream()
                .filter(f->f.getValue() > minQuantity)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ))
                .forEach((k,v)-> System.out.println(k + "::::" + v));

    }


}

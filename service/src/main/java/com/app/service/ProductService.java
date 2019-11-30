package com.app.service;


import com.app.exception.AppException;
import com.app.model.Category;
import com.app.model.Producer;
import com.app.model.Product;
import com.app.model.Stock;
import com.app.model.enums.GuaranteeComponents;
import com.app.repo.generic.ProductRepository;
import com.app.repo.impl.ProductRepositoryImpl;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.ProductValidator;

import java.beans.BeanInfo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ProductService {

    private final ProductValidator productValidator = new ProductValidator();
    private final ProductRepository productRepository = new ProductRepositoryImpl("HBN");
    private final CategoryService categoryService = new CategoryService();
    private final StockService stockService = new StockService();
    private final ProducerService producerService = new ProducerService();


    public Product addProductToDB(Product product) {

        if (product == null) {
            throw new AppException("object is null");
        }
        return productRepository.addOrUpdate(product).orElseThrow(() -> new AppException("NO RECORD IN DB"));
    }

    private boolean isProductAlreadyInDB(Product product) {

        String nameInDB = product.getName();
        Product productInDB = productRepository.findByName(nameInDB).orElseThrow(() -> new AppException("NO RECORD IN DB"));
        String categoryName = product.getCategory().getName();

        return product.getName().equals(productInDB) && product.getCategory().equals(categoryName);
    }

    public void productInit() {

        String answer = DataManager.getLine("WELCOME TO PRODUCT DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA AUTOMATE OR N IF YOU WANNA FILL THEM IN MANUAL");
        if (answer.toUpperCase().equals("Y")) {
            productInitAuto();
        } else {
            productInitManual();
        }
    }

    private void productInitAuto() {
        generateProductAutoMode();
        printProductRecordsFromDB();
    }
    private void printProductRecordsFromDB(){
        productRepository.findAll().forEach((s)-> System.out.println(s + "\n"));
    }

    private void productInitManual() {
        System.out.println("LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF PRODUCERS YOU WANNA ADD TO DB");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleProducerRecordCreator();
        }
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS OF PRODUCTS FROM DB");
        printProductRecordsFromDB();
    }


    private Product generateProductAutoMode() {
        Producer producer = producerService.findRandomProducerFromDb();
        Set<GuaranteeComponents> components = Set.of(GuaranteeComponents.getRandomComponent());
        Category category = categoryService.findRandomCategoryFromDB();
        String productName = generateProductName();
        BigDecimal price = generateProductPrice();

        return Product.builder().name(productName).price(price).producer(producer).components(components).category(category).build();
    }

    private String generateProductName() {
        List<String> nameList = List.of("BANAN", "ORANGE", "TV", "KAVASAKI", "HONDA");
        return nameList.get(new Random().nextInt(nameList.size() - 1));
    }
    private BigDecimal generateProductPrice(){
        return new BigDecimal(new Random().nextInt(400-100) + 100);
    }


    public Product singleProducerRecordCreator() {
        String productName = DataManager.getLine("PRESS PRODUCT NAME");
        BigDecimal price = BigDecimal.valueOf(DataManager.getInt("PRESS PRICE"));
        Category category = categoryService.findRandomCategoryFromDB();
        Set<GuaranteeComponents> guaranteeComponents = Set.of(GuaranteeComponents.getRandomComponent());

        Product product = Product.builder().name(productName).category(category).price(price).category(category).components(guaranteeComponents).build();

        if (isProductAlreadyInDB(product)) {
            throw new AppException("THIS RECORD IS ALREADY IN DB");
        }
        productValidator.validate(product);
        if (productValidator.hasErrors()) {
            throw new AppException("ERROR IN PRODUCT VALIDATION");
        }
        return addProductToDB(product);
    }


    public Integer getQuantityOfProductInStock(String productName) {
        return productRepository.getQuantityOfProductInStock(productName).orElseThrow(() -> new AppException("NO RECORD IN DB"));
    }

    public void showAllProductsInDB() {
        productRepository.findAll().forEach(System.out::print);
    }

    public Product findProductByName(String productName) {
        return productRepository.findByName(productName).orElseThrow(() -> new AppException("NO RECORD IN DB"));
    }

    public void decreaseQuantityOfProductInStock(String productName, Integer quantity) {
        Long idProductInStock = productRepository.getIdProductInStock(productName).orElseThrow(() -> new AppException("NO RECORD IN DB"));
        Stock stock = stockRepository.findOne(idProductInStock).orElseThrow(() -> new AppException("NO RECORD IN DB"));
        Integer quantityProductInStock = getQuantityOfProductInStock(productName) - quantity;
        stock.setQuantity(quantityProductInStock);
        stockRepository.addOrUpdate(stock);// is it ok, means update quantity in previous record

    }

}

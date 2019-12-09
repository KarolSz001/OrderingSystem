package com.app.service;


import com.app.exception.AppException;
import com.app.model.*;
import com.app.model.dto.Mapper;
import com.app.model.dto.ProductDTO;
import com.app.model.enums.GuaranteeComponents;
import com.app.repo.generic.ProductRepository;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.ProductValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductValidator productValidator;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProducerService producerService;

    public ProductService(ProductValidator productValidator, ProductRepository productRepository, CategoryService categoryService, ProducerService producerService) {
        this.productValidator = productValidator;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.producerService = producerService;
    }

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

        System.out.println("check !!!!");
        String answer = DataManager.getLine("WELCOME TO PRODUCT DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA AUTOMATE OR N IF YOU WANNA FILL THEM IN MANUAL");
        if (answer.toUpperCase().equals("Y")) {
            productInitAuto();
        } else {
            productInitManual();
        }
    }

    void productInitAuto() {
        generateProductAutoMode();
        printProductRecordsFromDB();
    }

    private void printProductRecordsFromDB() {
        productRepository.findAll().forEach((s) -> System.out.println("\n" + s));
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

    private void generateProductAutoMode() {
        for (int i = 1; i <= 4; i++) {
            Set<GuaranteeComponents> components = null;
            Producer producer = producerService.findRandomProducerFromDb();
            if(isGuarantee()) {
               components = GuaranteeComponents.getRandomComponent();
            }
            Category category = categoryService.findRandomCategoryFromDB();
            String productName = generateProductName();
            BigDecimal price = generateProductPrice();
            Product product = Product.builder().name(productName).price(price).producer(producer).components(components).category(category).build();
            addProductToDB(product);
        }
    }

    private boolean isGuarantee(){
        return new Random().nextBoolean();
    }

    private String generateProductName() {
        List<String> nameList = List.of("BANAN", "ORANGE", "TV", "KAVASAKI", "HONDA");
        return nameList.get(new Random().nextInt(nameList.size() - 1));
    }

    private BigDecimal generateProductPrice() {
        return new BigDecimal(new Random().nextInt(400 - 100) + 100);
    }

    public Product findRandomProductFromDb() {
        List<Product> products = productRepository.findAll();
        return products.get(new Random().nextInt(products.size() - 1));
    }


    public Product singleProducerRecordCreator() {

        String productName = DataManager.getLine("PRESS PRODUCT NAME");
        BigDecimal price = BigDecimal.valueOf(DataManager.getInt("PRESS PRICE"));
        Category category = categoryService.findRandomCategoryFromDB();
        Set<GuaranteeComponents> guaranteeComponents = GuaranteeComponents.getRandomComponent();

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


    public Product findProductById(Long id) {
        return productRepository.findOne(id).orElseThrow(() -> new AppException("NO RECORD IN DB"));
    }

    public Long getIdProductInStock(String name) {
        return productRepository.getIdProductInStock(name).orElseThrow(() -> new AppException("NO FOUND RECORD IN DB"));
    }


    ///////////////////////////////////////////////////////////////////

    public void solution1() {

        productRepository.findAll()
                .stream().map(Mapper::fromProductToProductDTO)
                .peek(System.out::println)
                .collect(Collectors.groupingBy(ProductDTO::getCategoryName))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().max(Comparator.comparing(ProductDTO::getPrice, Comparator.reverseOrder())).orElseThrow(() -> new AppException("NO RECORD FOUND"))
                ))
                .forEach((k,v)->System.out.println(k + "::::::" + v));
    }

    public void solution3(GuaranteeComponents... guaranteeComponents) {
        Set componentsList = new HashSet(Arrays.asList(guaranteeComponents));
        productRepository.findAll()
                .stream().map(Mapper::fromProductToProductWithGuarDTO)
//                .peek(System.out::println)
                .filter(f->!(f.getGuaranteeComponents().isEmpty()) && f.getGuaranteeComponents().containsAll(componentsList))
                .forEach(System.out::println);
    }







}

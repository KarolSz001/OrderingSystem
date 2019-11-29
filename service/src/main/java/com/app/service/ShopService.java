package com.app.service;

import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Shop;
import com.app.repo.generic.ShopRepository;
import com.app.repo.impl.ShopRepositoryImpl;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.CountryValidator;
import com.app.service.valid.ShopValidator;

import java.util.List;
import java.util.Optional;


public class ShopService {

    private final ShopRepository shopRepository = new ShopRepositoryImpl("HBN");
    private final CountryService countryService = new CountryService();
    private final CountryValidator countryValidator = new CountryValidator();
    private final ShopValidator shopValidator = new ShopValidator();

    public ShopService() {
    }

    public Shop addShopToDB(Shop shop) {

        if (shop == null) {
            throw new AppException("object is null");
        }
        shopRepository.addOrUpdate(shop);
        return shop;
    }

    private void generateShopsInDB() {

        List<String> shopNames = List.of("BIEDRA", "ZABA", "TESCO");

        for (String shopName : shopNames) {
            Country country = countryService.findRandomCountryFromDB();
            Shop shop = Shop.builder().name(shopName).country(country).build();
            addShopToDB(shop);
        }
    }

    public void shopInit() {

        String answer = DataManager.getLine("WELCOME TO SHOP DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA MANUALLY OR N IF YOU WANNA FILL THEM IN AUTOMATE");
        if (answer.toUpperCase().equals("Y")) {
            shopDataInitAutoFill();
        } else {
            categoryDataShopInitManualFill();
        }
    }

    public void printAllShopRecordsInDB() {
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        shopRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    private void shopDataInitAutoFill() {
        generateShopsInDB();
        printAllShopRecordsInDB();
    }

    private void categoryDataShopInitManualFill() {

        System.out.println("LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF RECORD YOU WANNA ADD TO DB");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleShopRecordCreator();
        }

        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        printAllShopRecordsInDB();
    }

    private Shop singleShopRecordCreator() {
        String name = DataManager.getLine("PRESS SHOP NAME");
        countryService.printAllRecordsInCountries();
        Country country = countryService.findCountryByName(name);
        Shop shop = Shop.builder().name(name).country(country).build();

        shopValidator.validate(shop);
        if (shopValidator.hasErrors()) {
            throw new AppException("ERROR IN SHOP VALIDATION");
        }

        Optional<Shop> shopByName = shopRepository.findByName(name);
        if (shopByName.isEmpty()) {
            addShopToDB(shop);
        }
        return addShopToDB(shop);
    }


}

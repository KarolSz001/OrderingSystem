package com.app.service;

import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Shop;
import com.app.repo.generic.CountryRepository;
import com.app.repo.generic.ShopRepository;
import com.app.service.valid.CountryValidator;
import com.app.service.valid.ShopValidator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopValidator shopValidator;
    private final CountryRepository countryRepository;
    private final CountryValidator countryValidator;

    public void addShopToDB(Shop shop) {

        if (shop == null) {
            throw new AppException("object is null");
        }
        shopRepository.addOrUpdate(shop);
    }

    private Shop createShop() {

        String name = DataManager.getLine("PRESS NAME");
        System.out.println("BELOW LIST OF COUNTRIES IN DB");
        countryRepository.findAll().forEach(System.out::print);
        Long idCountry = DataManager.getLong("PRESS NUMBER OF ID COUNTRY");
        Country country = countryRepository.findOne(idCountry).orElseThrow(() -> new AppException("cannot find record"));
        countryValidator.validate(country);

        if(countryValidator.hasErrors()){
            throw new AppException("VALID RECORD OF COUNTRY");
        }

        Shop shop = Shop.builder().name(name).country(country).build();

        shopValidator.validate(shop);
        if (shopValidator.hasErrors()) {
            throw new AppException("ERROR IN SHOP VALIDATION");
        }

        Optional<Shop> shopByName = shopRepository.findByName(name);
        if(shopByName.isPresent() && shopByName.get().getName().equals(shop.getName())){
            throw new AppException("THERE IS RECORD WITH SIMILAR NAME IN DB");
        }

        var insertShop= shopRepository
                .addOrUpdate(shop)
                .orElseThrow(() -> new AppException("cannot insert SHOP"));

        return insertShop;
    }


}

package com.app.service;

import com.app.exception.AppException;

import com.app.model.Country;
import com.app.repo.generic.CountryRepository;
import com.app.repo.impl.CountryRepositoryImpl;
import com.app.service.valid.CountryValidator;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CountryService {


    CountryRepository countryRepository = new CountryRepositoryImpl("HBN");
    CountryValidator countryValidator = new CountryValidator();

    public CountryService() {
        createCountriesInDb();
    }

    private Country addCategoryToDB(Country country) {

        if (country == null) {
            throw new AppException("OBJECT IS NULL");
        }
        return countryRepository.addOrUpdate(country)
                .orElseThrow(() -> new AppException("CANNOT INSERT COUNTRY"));
    }

    public void createCountriesInDb() {

        List<Country> countries   = List.of(
                Country.builder().name("POLAND").build(),
                Country.builder().name("GERMAN").build(),
                Country.builder().name("UK").build(),
                Country.builder().name("USA").build()
        );

        for (Country country : countries) {

          countryValidator.validate(country);
            if (countryValidator.hasErrors()) {
                throw new AppException("ERROR IN COUNTRY VALIDATION");
            }

            Optional<Country> categoryByName = countryRepository.findByName(country.getName());
            if (!(categoryByName.isPresent() && categoryByName.get().getName().equals(country.getName()))) {
//                throw new AppException("THERE IS RECORD WITH SIMILAR CATEGORY NAME IN DB");
                addCategoryToDB(country);
            }

        }

    }
    public Country findCountryInDB(){
        List<Country> countries = countryRepository.findAll();
        int index = new Random().nextInt(countries.size() - 1);
        return countries.get(index);
    }

    public void printAllRecordsInCountries(){
        countryRepository.findAll().forEach(System.out::print);
    }

}

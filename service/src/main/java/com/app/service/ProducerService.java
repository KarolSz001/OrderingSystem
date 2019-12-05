package com.app.service;


import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Producer;
import com.app.model.Product;
import com.app.model.Trade;
import com.app.repo.generic.ProducerRepository;
import com.app.repo.impl.ProducerRepositoryImpl;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.ProducerValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor

public class ProducerService {

    private final ProducerRepository producerRepository = new ProducerRepositoryImpl("HBN");
    private final TradeService tradeService = new TradeService();
    private final ProducerValidator producerValidator = new ProducerValidator();
    private final CountryService countryService = new CountryService();


    public Producer addProducerToDB(Producer producer) {
        if (producer == null) {
            throw new AppException("object is null");
        }
        return producerRepository.addOrUpdate(producer).orElseThrow(() -> new AppException("NO RECORD IN DB"));
    }

    private boolean isProducerAlreadyInDB(String producerName) {
       return !producerRepository.findByName(producerName).isEmpty();

        /*String tradeInDb = producerInDB.getTrade().getName();
        String countryInDb = producerInDB.getCountry().getName();
        return producer.getTrade().getName().equals(tradeInDb) && producer.getCountry().getName().equals(countryInDb);*/
    }


    public void generateProducersInDB() {
        List<String> producerNames = List.of("DANONE", "IBM", "MOTOROLA", "TREC");

        for (String name : producerNames) {
            Country country = countryService.findRandomCountry();
            Trade trade = tradeService.findRandomTradeInDB();
            Producer producer = Producer.builder().name(name).country(country).trade(trade).build();
            if(isProducerAlreadyInDB(name))
            addProducerToDB(producer);
        }
    }

    public void producerInit() {
        String answer = DataManager.getLine("WELCOME TO PRODUCER DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA AUTOMATE OR N IF YOU WANNA FILL THEM IN MANUAL");
        if (answer.toUpperCase().equals("Y")) {
            producerInitAuto();
        } else {
            producerInitManual();
        }
    }

    public void printProducerRecordsFromDB() {
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        producerRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    private void producerInitAuto() {
        generateProducersInDB();
        printProducerRecordsFromDB();
    }

    private void producerInitManual() {
        System.out.println("LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF PRODUCERS YOU WANNA ADD TO DB");
        for (int i = 1; i <= numberOfRecords; i++) {
            singleProducerRecordCreator();
        }
        printProducerRecordsFromDB();
    }

    private Producer singleProducerRecordCreator() {
        String producerName = DataManager.getLine("PRESS PRODUCER NAME");
        Country country = countryService.findRandomCountryFromDB();
        Trade trade = tradeService.findTradeByName(DataManager.getLine("PRESS TRADE NAME"));
        Producer producer = Producer.builder().name(producerName).country(country).trade(trade).build();

        producerValidator.validate(producer);
        if (producerValidator.hasErrors()) {
            throw new AppException("ERROR IN TRADE VALIDATION");
        }

        Optional<Producer> producerByName = producerRepository.findByName(producerName);
        if (producerByName.isEmpty()) {
            throw new AppException("NO RECORDS TRADE IN DB");
        }
        return addProducerToDB(producer);
    }

    public void printAllRecordsInProducers() {
        producerRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    public Producer findRandomProducerFromDb() {
        List<Producer> producers = producerRepository.findAll();
        return producers.get(new Random().nextInt(producers.size() - 1));
    }



}

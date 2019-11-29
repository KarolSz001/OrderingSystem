package com.app.service;


import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Producer;
import com.app.model.Trade;
import com.app.repo.generic.ProducerRepository;
import com.app.repo.impl.ProducerRepositoryImpl;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.ProducerValidator;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

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

    private boolean isProducerAlreadyInDB(Producer producer) {

        String nameInDB = producer.getName();
        Producer producerInDB = producerRepository.findByName(nameInDB).orElseThrow(() -> new AppException("NO RECORD IN DB"));
        String tradeInDb = producerInDB.getTrade().getName();
        String countryInDb = producerInDB.getCountry().getName();
        return producer.getTrade().getName().equals(tradeInDb) && producer.getCountry().getName().equals(countryInDb);
    }


    public Producer generateProducerAutoMode() {

        String producerName = DataManager.getLine("PRESS PRODUCER NAME");
        Country country = countryService.getRandomCountry();
        Trade trade = tradeService.findRandomTradeInDB();
        Producer producer = Producer.builder().name(producerName).country(country).trade(trade).build();

       if(isProducerAlreadyInDB(producer)){
           throw new AppException("THIS RECORD ISA ALREADY IN DB");
       }

        producerValidator.validate(producer);
        if (producerValidator.hasErrors()) {
            throw new AppException("ERROR IN PRODUCER VALIDATION");
        }
        return addProducerToDB(producer);
    }


    public void producerInit() {

        String answer = DataManager.getLine("WELCOME TO PRODUCER DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA AUTOMATE OR N IF YOU WANNA FILL THEM IN MANUAL");
        if (answer.toUpperCase().equals("Y")) {
            producerDataInitAuto();
        } else {
            producerDataInitManual();
        }
    }

    public void printProducerRecordsFromDB() {
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        producerRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    private void producerDataInitAuto() {
        generateProducerAutoMode();
        printProducerRecordsFromDB();
    }

    private void producerDataInitManual() {

        System.out.println("LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF PRODUCERS YOU WANNA ADD TO DB");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleProducerRecordCreator();
        }
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS OF PRODUCERS");
        printProducerRecordsFromDB();
    }

    private Producer singleProducerRecordCreator() {
        String producerName = DataManager.getLine("PRESS PRODUCER NAME");
        countryService.printAllRecordsInCountries();
        Country country = countryService.findCountryByName(DataManager.getLine("PRESS COUNTRY NAME"));
        tradeService.printAllRecordsInTrades();
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

    public void printAllRecordsInProducers(){
        producerRepository.findAll().forEach((s)-> System.out.println(s + "\n"));
    }






}

package com.app.service;


import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Producer;
import com.app.model.Product;
import com.app.model.Trade;
import com.app.repo.generic.CountryRepository;
import com.app.repo.generic.ProducerRepository;
import com.app.service.valid.ProducerValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class ProducerService {

    private final ProducerRepository producerRepository;
    private final TradeService tradeService;
    private final ProducerValidator producerValidator;
    private final CountryService countryService;



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


    public Producer createProducer() {

        String producerName = DataManager.getLine("PRESS PRODUCER NAME");
        Country country = countryService.findCountryInDB();
        Trade trade = tradeService.findTradeInDB();

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






}

package com.app.service;


import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Producer;
import com.app.model.Trade;
import com.app.repo.generic.CountryRepository;
import com.app.repo.generic.ProducerRepository;
import com.app.repo.generic.TradeRepository;
import com.app.service.valid.ProducerValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class ProducerService {

    private final ProducerRepository producerRepository;
    private final CountryRepository countryRepository;
    private final TradeRepository tradeRepository;
    private final ProducerValidator producerValidator;


    public void addProducerToDB(Producer producer) {

        if (producer == null) {
            throw new AppException("object is null");
        }
        producerRepository.addOrUpdate(producer);
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
        String tradeName = DataManager.getLine("PRESS TRADE NAME");
        String countName = DataManager.getLine("PRESS COUNTRY NAME");

        Country country = countryRepository.findByName(countName).orElseThrow(() -> new AppException("no country records for this name"));
        Trade trade = tradeRepository.findByName(tradeName).orElseThrow(() -> new AppException("no trade records for this name"));

        Producer producer = Producer.builder().name(producerName).country(country).trade(trade).build();

       if(isProducerAlreadyInDB(producer)){
           throw new AppException("THIS RECORD ISA ALREADY IN DB");
       }

        producerValidator.validate(producer);
        if (producerValidator.hasErrors()) {
            throw new AppException("ERROR IN PRODUCER VALIDATION");
        }


        var insertProducer = producerRepository
                .addOrUpdate(producer)
                .orElseThrow(() -> new AppException("cannot insert PRODUCER"));

        return insertProducer;


    }


}

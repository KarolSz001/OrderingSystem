package com.app.service;

import com.app.exception.AppException;
import com.app.model.Trade;
import com.app.repo.generic.TradeRepository;
import com.app.repo.impl.TradeRepositoryImpl;
import com.app.service.valid.TradeValidator;

import java.util.List;
import java.util.Optional;
import java.util.Random;



public class TradeService {

    TradeRepository tradeRepository = new TradeRepositoryImpl("HBN");
    TradeValidator tradeValidator = new TradeValidator();

    public TradeService() {

    }

    private Trade addCategoryToDB(Trade trade) {

        if (trade == null) {
            throw new AppException("OBJECT IS NULL");
        }
        return tradeRepository.addOrUpdate(trade)
                .orElseThrow(() -> new AppException("CANNOT INSERT COUNTRY"));
    }

    public void createTradesInDb() {

        List<Trade> trades = List.of(
                Trade.builder().name("FOOD").build(),
                Trade.builder().name("TECH").build(),
                Trade.builder().name("BOOK").build(),
                Trade.builder().name("AUDIO").build()
        );

        for (Trade trade : trades) {

            tradeValidator.validate(trade);
            if (tradeValidator.hasErrors()) {
                throw new AppException("ERROR IN COUNTRY VALIDATION");
            }

            Optional<Trade> tradeByName = tradeRepository.findByName(trade.getName());
            if (!(tradeByName.isPresent() && tradeByName.get().getName().equals(trade.getName()))) {
                addCategoryToDB(trade);
            }

        }

    }

    public Trade findTradeInDB() {
        List<Trade> trades = tradeRepository.findAll();
        int index = new Random().nextInt(trades.size() - 1);
        return trades.get(index);
    }

    public void printAllRecordsInTrades() {
        tradeRepository.findAll().forEach(System.out::print);
    }

}

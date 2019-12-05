package com.app.service;

import com.app.exception.AppException;
import com.app.model.Trade;
import com.app.repo.generic.TradeRepository;
import com.app.repo.impl.TradeRepositoryImpl;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.TradeValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
public class TradeService {

    TradeRepository tradeRepository = new TradeRepositoryImpl("HBN");
    TradeValidator tradeValidator = new TradeValidator();



    private Trade addCategoryToDB(Trade trade) {

        if (trade == null) {
            throw new AppException("OBJECT IS NULL");
        }
        return tradeRepository.addOrUpdate(trade)
                .orElseThrow(() -> new AppException("CANNOT INSERT COUNTRY"));
    }

    public void createTradesInDb() {
        System.out.println("LOADING AUTOFILL PROGRAM TO UPDATE DATA_BASE");
        List<Trade> trades = List.of(
                Trade.builder().name("FOOD").build(),
                Trade.builder().name("TECH").build(),
                Trade.builder().name("BOOK").build(),
                Trade.builder().name("AUDIO").build()
        );

        for (Trade trade : trades) {
            Optional<Trade> tradeByName = tradeRepository.findByName(trade.getName());
            if (tradeByName.isEmpty()) {
                addCategoryToDB(trade);
            }
        }
    }

    public Trade findRandomTradeInDB() {
        List<Trade> trades = tradeRepository.findAll();
        int index = new Random().nextInt(trades.size() - 1);
        return trades.get(index);
    }

    public void printAllRecordsInTrades() {
        System.out.println("\n LOADING DATA COMPLETED ----> BELOW ALL RECORDS ");
        tradeRepository.findAll().forEach((s)-> System.out.println(s + "\n"));
    }

    public void tradeInit() {

        String answer = DataManager.getLine("\n WELCOME TO TRADE DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA MANUALLY OR N IF YOU WANNA FILL THEM IN AUTOMATE ");
        if (answer.toUpperCase().equals("Y")) {
            tradeDataInitAutoFill();
        } else {
            tradeDataInitManualFill();
        }
    }

    private void tradeDataInitAutoFill() {
        createTradesInDb();
        printAllRecordsInTrades();
    }

    private void tradeDataInitManualFill() {

        System.out.println("\n LOADING MANUAL PROGRAM TO UPDATE DATA_BASE ");
        int numberOfRecords = DataManager.getInt("\n PRESS NUMBER OF RECORD YOU WANNA ADD TO DB ");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleTradeRecordCategory();
        }
        printAllRecordsInTrades();
    }

    private void singleTradeRecordCategory() {
        String name = DataManager.getLine(" PRESS TRADE NAME ");
        Trade trade = Trade.builder().name(name).build();
        tradeValidator.validate(trade);
        if (tradeValidator.hasErrors()) {
            throw new AppException(" VALID DATA IN TRADE CREATOR ");
        }
       addCategoryToDB(trade);
    }

    public Trade findTradeByName(String name){
        return tradeRepository.findByName(name).orElseThrow(()->new AppException("\n NO RECORDS FOUND IN DB "));
    }
}

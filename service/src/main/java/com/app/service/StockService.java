package com.app.service;


import com.app.exception.AppException;
import com.app.model.Stock;
import com.app.repo.generic.CountryRepository;
import com.app.repo.generic.ProductRepository;
import com.app.repo.generic.ShopRepository;
import com.app.repo.generic.StockRepository;

public class StockService {

    ProductRepository productRepository;
    ShopRepository shopRepository;
    CountryRepository countryRepository;
    StockRepository stockRepository;

    public void addRecordToStock(Stock stock) {

        if (stock == null) {
            throw new AppException("object is null");
        }
        stockRepository.addOrUpdate(stock);
    }

    private Stock createRecordInStock() {


        Stock stock = null;
        var insertStock= stockRepository
                .addOrUpdate(stock)
                .orElseThrow(() -> new AppException("cannot insert SHOP"));

        return insertStock;
    }

}

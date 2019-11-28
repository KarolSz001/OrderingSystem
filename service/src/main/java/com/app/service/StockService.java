package com.app.service;


import com.app.exception.AppException;
import com.app.model.Product;
import com.app.model.Shop;
import com.app.model.Stock;
import com.app.repo.generic.ProductRepository;
import com.app.repo.generic.ShopRepository;
import com.app.repo.generic.StockRepository;
import com.app.service.dataUtility.DataManager;

public class StockService {

    ProductRepository productRepository;
    ShopRepository shopRepository;
    StockRepository stockRepository;

    private Stock addRecordToStock(Stock stock) {

        if (stock == null) {
            throw new AppException("object is null");
        }
        return stockRepository.addOrUpdate(stock).orElseThrow(() -> new AppException("cannot insert SHOP"));
    }

    public Stock createNewRecordInStock() {

        System.out.println("List of product in our DataBase");
        productRepository.findAll().forEach(System.out::print);  // print with category of
        Long idProduct = DataManager.getLong("PRESS ID PRODUCT OF YOU CHOICE");
        Product product = productRepository.findOne(idProduct).orElseThrow(() -> new AppException("NO RECORD FOUND"));

        System.out.println("List of shops in our DataBase");
        shopRepository.findAll().forEach(System.out::print);
        Long idShop = DataManager.getLong("PRESS ID SHOP OF YOU CHOICE");
        Shop shop = shopRepository.findOne(idShop).orElseThrow(() -> new AppException("NO RECORD FOUND"));
        Integer quantityProductInStock = DataManager.getInt("PRESS NUMBER OF PRODUCT IN STOCK");

        Stock stock = Stock.builder().product(product).quantity(quantityProductInStock).shop(shop).build();

        return addRecordToStock(stock);


    }

}

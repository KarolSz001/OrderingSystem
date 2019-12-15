package com.app.service;

import com.app.exception.AppException;
import com.app.model.Category;
import com.app.repo.generic.CategoryRepository;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.CategoryValidator;


import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private Category addCategoryToDB(Category category) {
        if (category == null) {
            throw new AppException("OBJECT IS NULL");
        }
        return categoryRepository.addOrUpdate(category)
                .orElseThrow(() -> new AppException("CANNOT INSERT CATEGORY"));
    }

    public void createCategoriesInDB() {
        System.out.println("LOADING AUTOFILL PROGRAM TO UPDATE DATA_BASE");
        List<Category> categories = List.of(
                Category.builder().name("FOOD").build(),
                Category.builder().name("TECH").build(),
                Category.builder().name("CAR").build(),
                Category.builder().name("BOOK").build()
        );

        for (Category category : categories) {
            Optional<Category> categoryByName = categoryRepository.findByName(category.getName());
            if (categoryByName.isEmpty())
                addCategoryToDB(category);
        }
    }

    public Category findRandomCategoryFromDB() {
        List<Category> categories = categoryRepository.findAll();
        int index = new Random().nextInt(categories.size() - 1);
        System.out.println("List of counties in DB , randomly Added --->>>" + categories.get(index));
        return categories.get(index);
    }

    public void printAllRecordsInCategories() {
        System.out.println("\n LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        categoryRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    public void categoryInit() {
        String answer = DataManager.getLine("\nWELCOME TO CATEGORY DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA MANUALLY OR N IF YOU WANNA FILL THEM IN AUTOMATE");
        if (answer.toUpperCase().equals("Y")) {
            categoryDataInitManualFill();
        } else {
            categoryDataInitAutoFill();
        }
    }

    public void categoryDataInitAutoFill() {
        createCategoriesInDB();
        printAllRecordsInCategories();
    }

    private void categoryDataInitManualFill() {
        System.out.println("\nLOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF RECORD YOU WANNA ADD TO DB");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleCategoryRecordCreator();
        }
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        printAllRecordsInCategories();
    }

    private void singleCategoryRecordCreator() throws AppException {
        CategoryValidator categoryValidator = new CategoryValidator();
        String name = DataManager.getLine("PRESS CATEGORY NAME");
        Category category = Category.builder().name(name).build();
        categoryValidator.validate(category);
        if (categoryValidator.hasErrors()) {
            throw new AppException("VALID DATA IN COUNTRY CREATOR");

        } else if (findCategoryByNameInDb(category.getName()).isEmpty()) {
            addCategoryToDB(category);
        }
    }

    private Optional<Category> findCategoryByNameInDb(String name) {
        return categoryRepository.findByName(name);
    }

    public void clearDataFromCategory() {
        categoryRepository.deleteAll();
    }
}

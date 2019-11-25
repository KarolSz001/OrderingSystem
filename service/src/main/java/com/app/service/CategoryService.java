package com.app.service;

import com.app.exception.AppException;
import com.app.model.Category;
import com.app.repo.generic.CategoryRepository;
import com.app.repo.impl.CategoryRepositoryImpl;
import com.app.service.valid.CategoryValidator;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CategoryService {


    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl("HBN");
    private final CategoryValidator categoryValidator = new CategoryValidator();


    public CategoryService() {
        createCategoriesInDB();
    }

    private Category addCategoryToDB(Category category) {

        if (category == null) {
            throw new AppException("OBJECT IS NULL");
        }
        return categoryRepository.addOrUpdate(category)
                .orElseThrow(() -> new AppException("CANNOT INSERT CATEGORY"));
    }

    public void createCategoriesInDB() {

        List<Category> categories = List.of(
                Category.builder().name("FOOD").build(),
                Category.builder().name("TECH").build(),
                Category.builder().name("CAR").build(),
                Category.builder().name("BOOK").build()
        );

        for (Category category : categories) {

            categoryValidator.validate(category);
            if (categoryValidator.hasErrors()) {
                throw new AppException("ERROR IN CATEGORY VALIDATION");
            }

            Optional<Category> categoryByName = categoryRepository.findByName(category.getName());
            if (!(categoryByName.isPresent() && categoryByName.get().getName().equals(category.getName()))) {
//                throw new AppException("THERE IS RECORD WITH SIMILAR CATEGORY NAME IN DB");
                addCategoryToDB(category);
            }

        }


    }


    public Category findCountryInDB() {
        List<Category> categories = categoryRepository.findAll();
        int index = new Random().nextInt(categories.size() - 1);
        return categories.get(index);
    }

    public void printAllRecordsInCategories() {
        categoryRepository.findAll().forEach(System.out::print);
    }

}

package com.app.repo.impl;


import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.ErrorRepository;

public class ErrorRepositoryImpl extends AbstractCrudRepository<Error, Long> implements ErrorRepository {
    public ErrorRepositoryImpl(String persistenceUnit) {
        super(persistenceUnit);
    }

}

package com.app.repo.impl;


import com.app.model.Customer;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class CustomerRepositoryImpl extends AbstractCrudRepository<Customer, Long> implements CustomerRepository {
    public CustomerRepositoryImpl(String persistenceUnit) {
        super(persistenceUnit);
    }

    @Override
    public Optional<Customer> findByName(String surName) {

            EntityManager em = null;
            EntityTransaction tx = null;
            Optional<Customer> customerBySurName = Optional.empty();

            try {
                em = emf.createEntityManager();
                tx = em.getTransaction();
                tx.begin();

                customerBySurName = em
                        .createQuery("select c from Customer c where c.surName = :surName", Customer.class)
                        .setParameter("surName", surName)
                        .getResultStream().findFirst();

                tx.commit();

            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                if (em != null) {
                    em.close();
                }
            }

        return customerBySurName;
        }

    }

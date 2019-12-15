package com.app.repo.impl;

import com.app.model.Product;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.ProductRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class ProductRepositoryImpl extends AbstractCrudRepository<Product, Long> implements ProductRepository {
    public ProductRepositoryImpl(String persistenceUnit) {
        super(persistenceUnit);
    }


    @Override
    public Optional<Product> findByName(String name) {
        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<Product> list = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            list = em
                    .createQuery("select c from Product c where c.name = :name", Product.class)
                    .setParameter("name", name)
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
        return list;
    }




}




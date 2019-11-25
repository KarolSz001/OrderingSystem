package com.app.repo.impl;



import com.app.model.Shop;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.ShopRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class ShopRepositoryImpl extends AbstractCrudRepository<Shop,Long> implements ShopRepository {
    public ShopRepositoryImpl(String persistenceUnit) {
        super(persistenceUnit);
    }

    @Override
    public Optional<Shop> findByName(String name) {
        EntityManager em = null;
        EntityTransaction tx = null;

        Optional<Shop> shop = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            shop = em
                    .createQuery("select s from Shop s where s.name = :name", Shop.class)
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
        return shop;
    }
}

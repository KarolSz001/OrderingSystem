package com.app.repo.impl;

import com.app.model.Product;
import com.app.model.Stock;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.StockRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;


public class StockRepositoryImpl extends AbstractCrudRepository<Stock, Long> implements StockRepository {
    public StockRepositoryImpl(String persistenceUnit) {
        super(persistenceUnit);
    }

    public void query1() {


    }


    public List<Object[]> query4() {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<Object[]> resultList = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            resultList = em
                    .createQuery("select p, ss from Stock s join s.product p join s.shop ss", Object[].class)
                    .getResultList();

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
        return resultList;
    }

    public List<Object[]> query5(String tradeName) {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<Object[]> resultList = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            resultList = em
                    .createQuery("select p, s.quantity from Stock s join s.product.producer p where p.trade.name =:tradeName", Object[].class)
                    .setParameter("tradeName", tradeName)
                    .getResultList();

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
        return resultList;
    }


}

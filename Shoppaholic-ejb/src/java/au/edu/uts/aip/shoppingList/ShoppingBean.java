/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.uts.aip.shoppingList;

import au.edu.uts.aip.accounts.Account;
import au.edu.uts.aip.accounts.AccountBean;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author jessekras
 */
@Stateless
public class ShoppingBean {

    @PersistenceContext
    private EntityManager em;

    Account account = new Account();
    
    @EJB
    private AccountBean accountBean;

    public void addSampleData(String email) {
        ShoppingList weeklyList = new ShoppingList();
        weeklyList.setName("Weekly Shopping List");

        ShoppingItem item1 = new ShoppingItem();
        item1.setAddedby("Jesse");
        item1.setDescription("Boiled Salmon Sticks");
        item1.setName("Sammies Sticks");
        item1.setPrice(new BigDecimal("3.99"));

        ShoppingItem item2 = new ShoppingItem();
        item2.setAddedby("Jesse");
        item2.setDescription("Cheezy Balls of Goodness");
        item2.setName("Cheezles");
        item2.setPrice(new BigDecimal("9.12"));
        
        account = accountBean.findByEmail(email);
        
        weeklyList.getShoppingListItems().add(item1);
        weeklyList.getShoppingListItems().add(item2);
        weeklyList.setAcctId(account.getAcctId());

        
        account.getShoppingLists().add(weeklyList);
        
        item1.setShoppingList(weeklyList);
        item2.setShoppingList(weeklyList);
        
        em.persist(account);
        em.persist(weeklyList);
        em.persist(item1);
        em.persist(item2);

    }

    /**
     *
     * Return all items in the shopping item table
     *
     * Will need to be changed to a join once individual lists exist
     *
     * @return 
     *
     */
    public List<ShoppingItem> findAll() {
        TypedQuery<ShoppingItem> query = em.createNamedQuery(
                "findAll", ShoppingItem.class
        );
        //query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    public void create(ShoppingList list, ShoppingItem shoppingItem) {
        list.getShoppingListItems().add(shoppingItem);
        em.merge(list);
        em.persist(shoppingItem);

    }

    /**
     * *
     *
     * @return currently active list for the user
     *
     *
     */
    public List<ShoppingItem> getCurrentList(String email) {
        // get active list id
        Logger log = Logger.getLogger(this.getClass().getName());
        int count = accountBean.findByEmail(email).getShoppingLists().size();
        log.info("List size: " + count);
        if (count > 0) {
            int id = accountBean.findByEmail(email).getShoppingLists().get(0).getId();
            String name = accountBean.findByEmail(email).getShoppingLists().get(0).getName();

            
            log.info("List ID: " + id + " Name: " + name);

            List<ShoppingItem> items = accountBean.findByEmail(email).getShoppingLists().get(0).getShoppingListItems();

            for (ShoppingItem i : items) {
                log.info(i.toString());
            }

        }

        // get items from the list whose id is...
        TypedQuery<ShoppingItem> query = em.createNamedQuery(
                "findAll", ShoppingItem.class
        );
        //query.setParameter("lastName", lastName);
        return query.getResultList();
    }

}

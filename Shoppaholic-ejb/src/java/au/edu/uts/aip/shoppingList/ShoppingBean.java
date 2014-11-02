/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.shoppingList;

import java.math.BigDecimal;
import javax.ejb.Stateless;
import java.util.*;
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
    
    public void addSampleData() {
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
        
        weeklyList.getShoppingListItems().add(item1);
        weeklyList.getShoppingListItems().add(item2);
        
        item1.setShoppingList(weeklyList);
        item2.setShoppingList(weeklyList);
        
        em.persist(weeklyList);
        em.persist(item1);
        em.persist(item2);
                
       
        
    }
    
    
    /**
     * 
     * Return all items in the shopping item table
     * 
     * Will need to be changed to a join once individual lists exist
     * @return 
     * */
    public List<ShoppingItem> findAll() {
        TypedQuery<ShoppingItem> query = em.createNamedQuery(
                "findAll", ShoppingItem.class
        );
        //query.setParameter("lastName", lastName);
        return query.getResultList();
    }
    
}

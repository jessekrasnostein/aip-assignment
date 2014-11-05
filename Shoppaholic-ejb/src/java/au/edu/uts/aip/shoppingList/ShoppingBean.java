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
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

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
        account.setCurrentList(weeklyList);          
        
        item1.setShoppingList(weeklyList);
        item2.setShoppingList(weeklyList);
        
        em.persist(account);                           
        em.persist(weeklyList);                        
        em.persist(item1);
        em.persist(item2);

    }
    
    /**
     * Create a new shopping list for the current user
     * @param accountEmail
     * @param listName 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createNewShoppingList(String accountEmail, String listName, 
            ShoppingList shoppingList, Account account_um) throws EJBException {
        account = accountBean.findByEmail(accountEmail);
        ShoppingList newList = new ShoppingList();
        
        newList.setName(listName);
        newList.setAcctId(account.getAcctId());
        
        account.getShoppingLists().add(newList);
        em.persist(newList);
        
        account.setCurrentList(newList);
        em.persist(account);
        
    }

    public void deleteShoppingList(ShoppingList list) 
            throws ConstraintViolationException, EJBException {
        // Get an equivalent managed object
        ShoppingList managed = em.find(ShoppingList.class, list.getId());
        
        // We need to keep the bi-directional relationships up-to-date
        managed.getAccount().getShoppingLists().remove(managed);
        
        // Update the relationship on the Person entity (if it is a detached entity)
        if (managed != list) {
            list.getAccount().getShoppingLists().remove(list);
        }
        em.remove(managed);
    }
    
    public void deleteShoppingListItem(int id) {
        // Get an equivalent managed object
        ShoppingItem managed = em.find(ShoppingItem.class, id);
        em.remove(managed);
    }



    public void updateShoppingList(ShoppingList list) {
        em.merge(list); 
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

    public void createShoppingItem(ShoppingList list, ShoppingItem shoppingItem) {
        ShoppingList managed = em.find(ShoppingList.class, list.getId());
        
        shoppingItem.setShoppingList(managed);
        
        managed.getShoppingListItems().add(shoppingItem);
        
        if (list != managed) {
            list.getShoppingListItems().add(shoppingItem);
        }
        
        em.persist(shoppingItem);
        
    }

    
    public List<ShoppingList> getAvailableLists(String email) {
        Logger log = Logger.getLogger(this.getClass().getName());
        //List<ShoppingList> lists = new ArrayList<>();
        return accountBean.findByEmail(email).getShoppingLists();
    }
    
    public ShoppingList currentList(String email) {
        Logger log = Logger.getLogger(this.getClass().getName());
        log.info("Shopping bean current list: " + accountBean.findByEmail(email).getCurrentList().getName());
        return accountBean.findByEmail(email).getCurrentList();
    }
    
    public ShoppingList getListById(Account account, int listId) {
        ShoppingList managed = em.find(ShoppingList.class, listId);
        
        if (managed.getAcctId() == account.getAcctId()){
            return managed;
        } else {
           return null;
        }
        
    }
    /**
     * *
     *
     * @return currently active list for the user
     */
    public List<ShoppingItem> getCurrentListItems(String email)  {
        // get active list id
        Logger log = Logger.getLogger(this.getClass().getName());
        List<ShoppingItem> items = new ArrayList<>();
        
        try {
            items = accountBean.findByEmail(email).getCurrentList().getShoppingListItems();
        } catch (NullPointerException np) {
            log.severe(np.getMessage());
        } 
//        int count = accountBean.findByEmail(email).getShoppingLists().size();
//        log.info("List size: " + count);
//        if (count > 0) {
//            int id = accountBean.findByEmail(email).getShoppingLists().get(0).getId();
//            String name = accountBean.findByEmail(email).getShoppingLists().get(0).getName();
//
//            
//            log.info("List ID: " + id + " Name: " + name);
//
//            items = accountBean.findByEmail(email).getShoppingLists().get(0).getShoppingListItems();
//
//            for (ShoppingItem i : items) {
//                log.info(i.toString());
//            }
//
//        }

        // get items from the list whose id is...
//        TypedQuery<ShoppingItem> query = em.createNamedQuery(
//                "findAll", ShoppingItem.class
//        );
        //query.setParameter("lastName", lastName);
        
        return items;
    }
    
    public List<ShoppingItem> getCurrentList(String email) {
        // get active list id
        Logger log = Logger.getLogger(this.getClass().getName());
        List<ShoppingItem> items = new ArrayList<>();
        
        try {
            items = accountBean.findByEmail(email).getCurrentList().getShoppingListItems();
        } catch (NullPointerException np) {
            log.severe(np.getMessage());
        } 
//        int count = accountBean.findByEmail(email).getShoppingLists().size();
//        log.info("List size: " + count);
//        if (count > 0) {
//            int id = accountBean.findByEmail(email).getShoppingLists().get(0).getId();
//            String name = accountBean.findByEmail(email).getShoppingLists().get(0).getName();
//
//            
//            log.info("List ID: " + id + " Name: " + name);
//
//            items = accountBean.findByEmail(email).getShoppingLists().get(0).getShoppingListItems();
//
//            for (ShoppingItem i : items) {
//                log.info(i.toString());
//            }
//
//        }

        // get items from the list whose id is...
//        TypedQuery<ShoppingItem> query = em.createNamedQuery(
//                "findAll", ShoppingItem.class
//        );
        //query.setParameter("lastName", lastName);
        
        return items;
    }
        public void clearCache() {
        em.getEntityManagerFactory().getCache().evictAll();
    }

}

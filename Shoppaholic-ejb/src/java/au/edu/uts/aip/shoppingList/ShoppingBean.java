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
 * 
 */
@Stateless
public class ShoppingBean {

    @PersistenceContext
    private EntityManager em;

    Account account = new Account();
    
    @EJB
    private AccountBean accountBean;

    /**
     * Add Sample Data Method to DB
     * @param email passes in User Email to add the data
     */
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
     * @param accountEmail Owner of the List
     * @param listName List Name of the shopping list
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

    /**
     * Method to delete a specific Shopping List from the DB
     * @param list Pass in the List to be deleted
     * @throws ConstraintViolationException Catches Exception
     * @throws EJBException Catches Exception
     */
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
    
    /**
     * Delete Specific Shopping List Item
     * @param id pass in the id of the List Item
     */
    public void deleteShoppingListItem(int id) {
        // Get an equivalent managed object
        ShoppingItem managed = em.find(ShoppingItem.class, id);
        // Remove the Item
        em.remove(managed);
    }

    /**
     * Update a specific Shopping List after Changes on Edit View
     * @param list specific list
     */
    public void updateShoppingList(ShoppingList list) {
        em.merge(list); 
    }
    
    /**
     * Return all items in the shopping item table
     * Will need to be changed to a join once individual lists exist
     *
     * @return return the list of Shopping List Items
     */
    public List<ShoppingItem> findAll() {
        TypedQuery<ShoppingItem> query = em.createNamedQuery(
                "findAll", ShoppingItem.class
        );
        return query.getResultList();
    }

    /**
     * Create a shopping List Item for a specific List
     * @param list specific list to add to
     * @param shoppingItem shoppingItem to add to the list
     */
    public void createShoppingItem(ShoppingList list, ShoppingItem shoppingItem) {
        ShoppingList managed = em.find(ShoppingList.class, list.getId());
        
        // To persist a Shopping Item, we need to ensure that it is related to a 
        // managed entity (not another detached entity)
        shoppingItem.setShoppingList(managed);
        
        
        // Update the relationship on the Person entity (if it is a detached entity)
        managed.getShoppingListItems().add(shoppingItem);
        if (list != managed) {
            list.getShoppingListItems().add(shoppingItem);
        }
        
        // save changes to the contact (the contact is the "owner" of the relationship)
        em.persist(shoppingItem);
        
    }

    /**
     * Return a list of Lists that a user has.
     * @param email use email parameter
     * @return list of shopping Lists for a specific user
     */
    public List<ShoppingList> getAvailableLists(String email) {
        Logger log = Logger.getLogger(this.getClass().getName());
        //List<ShoppingList> lists = new ArrayList<>();
        return accountBean.findByEmail(email).getShoppingLists();
    }
    
    /**
     * Return list specific to a user
     * @param email use email parameter
     * @return current list tied to a specific user
     */
    public ShoppingList currentList(String email) {
        Logger log = Logger.getLogger(this.getClass().getName());
        log.info("Shopping bean current list: " + accountBean.findByEmail(email).getCurrentList().getName());
        return accountBean.findByEmail(email).getCurrentList();
    }
    
    /**
     * Return Shopping list by Id
     * @param account specific account owner of the list
     * @param listId specific list id
     * @return shopping list object
     */
    public ShoppingList getListById(Account account, int listId) {
        ShoppingList managed = em.find(ShoppingList.class, listId);
        if (managed.getAcctId() == account.getAcctId()){
            return managed;
        } else {
           return null;
        }
    }
    
    /**
     * Return specific Shopping Item
     * @param email specific user email
     * @return List of shopping Items
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
        return items;
    }
    
    /**
     * Get Current Shopping Items of a specific user list
     * @param email specific user email
     * @return return shoppingItem list
     */
    public List<ShoppingItem> getCurrentList(String email) {
        // get active list id
        Logger log = Logger.getLogger(this.getClass().getName());
        List<ShoppingItem> items = new ArrayList<>();
        
        try {
            items = accountBean.findByEmail(email).getCurrentList().getShoppingListItems();
        } catch (NullPointerException np) {
            log.severe(np.getMessage());
        } 
        return items;
    }
        public void clearCache() {
        em.getEntityManagerFactory().getCache().evictAll();
    }

}

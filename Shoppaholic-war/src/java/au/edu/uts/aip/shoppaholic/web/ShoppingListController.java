package au.edu.uts.aip.shoppaholic.web;


import au.edu.uts.aip.accounts.Account;
import au.edu.uts.aip.accounts.AccountBean;
import au.edu.uts.aip.shoppingList.*;
import au.edu.uts.aip.shoppingList.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * ShoppingListController is responsible for managing all functionality around
 shopping items and lists.
 */
@Named
@SessionScoped
public class ShoppingListController implements Serializable {

    private ShoppingItem item = new ShoppingItem();
    private ShoppingList list = new ShoppingList();

    @EJB
    private ShoppingBean shoppingBean;
    

    public void addSampleData() {
        shoppingBean.addSampleData(
                AccountsController.getCurrentUser().getEmail()
        );
    }

    /**
     * Load an item from the database
     *
     * @param index is the unique id of the item to retrieve
     */
    public void loadItem(int index) {
        //     item = shoppingBean.findOne(index);
    }

    public String createNewShoppingItem() {

        shoppingBean.createShoppingItem(curAccountList(), item);
        item = new ShoppingItem();
        return "viewlist?index="+list.getId();
    }
    
    /**
     * Retrieve entire shopping list
     *
     * @return List of ShoppingItems
     */
    public List<ShoppingItem> list() {
        return shoppingBean.getCurrentListItems(
                AccountsController.getCurrentUser().getEmail()
        );
    }

    /**
     * Retrieve entire shopping list
     *
     * @return List of ShoppingItems
     */
    public List<ShoppingItem> curList() {
//       List<ShoppingItem> m = shoppingBean.currentList(
//                AccountsController.getCurrentUser().getEmail())
//                .getShoppingListItems();
        int listId = AccountsController.getCurrentUser().getCurrentList().getId();
        Account account = AccountsController.getCurrentUser();
        
        return shoppingBean.getListById(account, listId).getShoppingListItems();
    }
    
    public String curListName() {

        return AccountsController.getCurrentUser().getCurrentList().getName();
    }
    
    public int curListId() {
        return AccountsController.getCurrentUser().getCurrentList().getId();
    }
    
    public ShoppingList curAccountList(){
        return AccountsController.getCurrentUser().getCurrentList();
    }

    public String updateList() {
        shoppingBean.updateShoppingList(list);
        setCurrent(list.getId());
        return "home?faces-redirect=true";
    }
    
    public String deleteList(ShoppingList list) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            shoppingBean.deleteShoppingList(list);
        } catch (ConstraintViolationException e) {
            context.addMessage(null, new FacesMessage(e.getMessage() + " Cannot delete list if it is currently in use."));
            return null;
        } catch (EJBException e) {
            context.addMessage(null, new FacesMessage(e.getMessage()  + " Cannot delete list if it is currently in use."));
            return null;
        }

        return "home?faces-redirect=true";
    }
    
    public String deleteItem(int id) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            shoppingBean.deleteShoppingListItem(id);
        } catch (ConstraintViolationException e) {
            context.addMessage(null, new FacesMessage(e.getMessage() + " Cannot delete list if it is currently in use."));
            return null;
        } catch (EJBException e) {
            context.addMessage(null, new FacesMessage(e.getMessage() + " Cannot delete list if it is currently in use."));
            return null;
        }

        return null;
    }

    public String createList() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            shoppingBean.createNewShoppingList(AccountsController
                    .getCurrentUser().getEmail(), list.getName(), list,
                    AccountsController.getCurrentUser());
        } catch (EJBException e) {
            context.addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
        setCurrentListInSession();
        return "home?faces-redirect=true";
    }

    /**
     * Load a list from the database
     *
     * @param listId is the unique id of the item to retrieve
     */
    public void loadList(int listId) {
        Logger log = Logger.getLogger(this.getClass().getName());
        log.info("ShoppingItemController loadList: @param listId: " + listId + " <");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        ShoppingList sList = new ShoppingList();
        try {
            List<ShoppingList> lists = AccountsController
                    .getCurrentUser().getShoppingLists();
            int index = -1;
            for (ShoppingList l: lists) {
                if (l.getId() == listId) {
                    index = lists.indexOf(l);
                }
            }
            if (index>-1) {
                sList = AccountsController.getCurrentUser().getShoppingLists().get(index);
            }
            
             //sList = AccountsController.getCurrentUser().getShoppingLists().indexOf(shoppingList)
                    
//            list = shoppingBean.getListById(
//                    AccountsController.getCurrentUser(), listId);
            log.log(Level.INFO, "List name is {0}", sList.getName());
//            AccountsController.getCurrentUser().setCurrentList(list);
//            setCurrentList();
            setCurrent(sList.getId());
            this.list = sList;
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("Looks like you are trying to access something you shouldn't be. Naughty!"));
        } 

    }

    public ShoppingList getList() {
        return list;
    }

    public void setList(ShoppingList list) {
        this.list = list;
    }

    /**
     * The item that our controller will manipulate
     *
     * @return a ShoppingItem
     */
    public ShoppingItem getItem() {
        return item;
    }

    public String clearCache() {
        shoppingBean.clearCache();
        return "home?faces-redirect=true";
    }

    /**
     * Retrieve available shopping lists for current user
     *
     * @return Lists of ShoppingLists
     */
    public List<ShoppingList> availableLists() {
        List<ShoppingList> lists = AccountsController.getCurrentUser()
                .getShoppingLists();
        return lists;
    }
    
    public void setCurrentList() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("currentList", shoppingBean.currentList(
                        AccountsController.getCurrentUser().getEmail())
                );
    }

    /**
     * Pass in the id of a list you would like to set as the current list if you
     * have access to it and it exists setCurrentList in Account and add to
     * session or else do nothing
     *
     * @param id
     */
    public void setCurrent(int id) {
        Logger log = Logger.getLogger(this.getClass().getName());
        List<ShoppingList> lists = availableLists();
        for (ShoppingList l : lists) {
            if (l.getId() == id) {
                AccountsController.getCurrentUser().setCurrentList(l);
                //accountBean.findByEmail(null)
                setCurrentListInSession();
            } else {
                //do nothing
            }
        }
    }

    /**
     * Add the Accounts currently active list to the session
     */
    public void setCurrentListInSession() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("currentList", AccountsController.getCurrentUser()
                        .getCurrentList()
                );
    }

    /**
     * @return Check what current shopping list in Session
     */
    public static ShoppingList getCurrentList() {
        return (ShoppingList) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("currentList");
    }

}

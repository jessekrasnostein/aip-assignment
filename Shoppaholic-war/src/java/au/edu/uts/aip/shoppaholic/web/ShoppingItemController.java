/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.uts.aip.shoppaholic.web;

import au.edu.uts.aip.shoppingList.*;

import au.edu.uts.aip.accounts.Account;
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
 * ShoppingItemController is responsible for managing all functionality around
 * shopping items and lists.
 *
 * @author jessekras
 */
@Named
@SessionScoped
public class ShoppingItemController implements Serializable {

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
        shoppingBean.createShoppingItem(shoppingBean.currentList(
                AccountsController.getCurrentUser().getEmail()), item);

        item = new ShoppingItem();
        return "home?faces-redirect=true";
    }

    /**
     * Retrieve entire shopping list
     *
     * @return List of ShoppingItems
     */
    public List<ShoppingItem> list() {
    public List<ShoppingItem> curList() {
        return shoppingBean.getCurrentListItems(
                AccountsController.getCurrentUser().getEmail()
        );
    }

    public String updateList() {
        shoppingBean.updateShoppingList(list);
        setCurrent(list.getId());
        return "home?faces-redirect=true";
    }
    
    public String deleteList() {
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
        
        try {
            list = shoppingBean.getListById(
                    AccountsController.getCurrentUser(), listId);
            log.log(Level.INFO, "List name is {0}", list.getName());
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
        return shoppingBean.getAvailableLists(
                AccountsController.getCurrentUser().getEmail()
        setCurrentList();
        
        return shoppingBean.getCurrentListItems(
            AccountsController.getCurrentUser().getEmail()
        );
    }
    
    public List<ShoppingList> availableLists() {
        Logger log = Logger.getLogger(this.getClass().getName());
        log.info("ShoppingItemController: availableLists");
        log.info("Current User: " + AccountsController.getCurrentUser().getEmail());
        return shoppingBean.getAvailableLists(
            AccountsController.getCurrentUser().getEmail()
        );
    }
    
    public void setCurrentList() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("currentList", shoppingBean.currentList(
                        AccountsController.getCurrentUser().getEmail())
                );
    }

    public static ShoppingList getCurrentList() {
        return (ShoppingList) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("currentList");
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
        for (ShoppingList list : lists) {
            if (list.getId() == id) {
                AccountsController.getCurrentUser().setCurrentList(list);
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

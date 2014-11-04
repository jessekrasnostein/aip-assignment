/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.shoppaholic.web;

import au.edu.uts.aip.shoppingList.*;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
/**
 * ShoppingItemController is responsible for managing all functionality 
 * around shopping items and lists.
 * @author jessekras
 */
@Named
@SessionScoped
public class ShoppingItemController implements Serializable {

    private ShoppingItem item = new ShoppingItem();    
    
    @EJB
    private ShoppingBean shoppingBean;
    
    /**
     * The item that our controller will manipulate
     * @return a ShoppingItem
     */
    public ShoppingItem getItem() {
        return item;
    }
    
    public void addSampleData() {
        shoppingBean.addSampleData(
                AccountsController.getCurrentUser().getEmail()
        );
    }
    
//    /**
//     * Load an item from the database
//     * @param index is the unique id of the item to retrieve
//     */
//    public void loadItem(int index) {
//        item = ShoppingItemDAO.findOne(index);
//    }
//    
//    /**
//     * Save current item as new line in database
//     * @return a redirect to view the whole shopping list
//     */
//    public String saveAsNew() {
//        //Create in database class
//        ShoppingItemDAO.create(item);
//        return "home?faces-redirect=true";
//    }
//    
//    /**
//     * Update item in the database that matches current item
//     * @return a redirect to view the whole shopping list
//     */
//    public String update() {
//        //Create in database class
//        ShoppingItemDAO.update(item);
//        return "home?faces-redirect=true";
//    }
//
//    /**
//     * Delete item from the database that matches current item id.
//     * @return a redirect to view the whole shopping list
//     */
//    public String delete() {
//        ShoppingItemDAO.delete(item.getId());
//        return "home?faces-redirect=true";
//    }
//    
    /**
     * Retrieve entire shopping list
     * @return List of ShoppingItems
     */
    public List<ShoppingItem> list() {
        return shoppingBean.getCurrentListItems(
            AccountsController.getCurrentUser().getEmail()
        );
    }
    
    
    /**
     * Retrieve available shopping lists for current user
     * @return Lists of ShoppingLists
     */
    public List<ShoppingList> availableLists() {
        return shoppingBean.getAvailableLists(
            AccountsController.getCurrentUser().getEmail()
        );
    }
    

    /**
     * Pass in the id of a list you would like to set as the current list
     * if you have access to it and it exists setCurrentList in Account 
     * and add to session or else do nothing
     * @param id 
     */
    public void setCurrent(int id) {
        Logger log = Logger.getLogger(this.getClass().getName());
        List<ShoppingList> lists = availableLists();
        for(ShoppingList list: lists) {
            if (list.getId() == id) {
                AccountsController.getCurrentUser().setCurrentList(list);
                setCurrentListInSession();
            } else {
                //do nothing
                log.info("Cannot set current list as data found for user id: " 
                        + AccountsController.getCurrentUser().getAcctId());
            }
        }
    }
    
    /**
     *  Add the Accounts currently active list to the session
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



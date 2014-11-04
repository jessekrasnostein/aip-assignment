/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.shoppaholic.web;

import au.edu.uts.aip.accounts.Account;
import au.edu.uts.aip.shoppingList.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
/**
 *
 * @author jessekras
 */
@Named
@RequestScoped
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
     * @return ArrayList of ShoppingItem'
     */
    public List<ShoppingItem> list() {
        Logger log = Logger.getLogger(this.getClass().getName());
        log.info("ShoppingItemController: list");
        log.info("Current User: " + AccountsController.getCurrentUser().getEmail());

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

}



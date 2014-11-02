/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.shoppingList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author jessekras
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "findAll",
                query = "select i from ShoppingItem i"
        ),
        @NamedQuery(
                    name = "findById",
                    query = "select i from ShoppingItem i "
                    + "where i.id = :id"
        ),
})
public class ShoppingItem implements Serializable {
    
    @Id
    @GeneratedValue
    private int id; 
    
    @ManyToOne
    private ShoppingList shoppingList;
    
    private String name;
    private String description;
    private String addedby;
    private String store;
    
    private BigDecimal price;
    private String priceString;
    
    @Temporal(TemporalType.DATE)
    private Date timeCreated;

    @Override
    public String toString() {
        return "ShoppingItem{" + "id=" + id + ", name=" + name + ", description=" + description + ", addedby=" + addedby + ", store=" + store + ", price=" + price + ", priceString=" + priceString + ", timeCreated=" + timeCreated + '}';
    }
    
    public ShoppingItem(int id, String name, String description, String addedby, 
            String store, BigDecimal price, Date timeCreated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.addedby = addedby;
        this.store = store;
        this.price = price;
        this.timeCreated = timeCreated;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    

    public ShoppingItem (String name, String description, String addedby, 
            String store, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.addedby = addedby;
        this.store = store;
        this.price = price;
        
    }
    
    public ShoppingItem() {}
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getPriceString() {
        return this.price.toString();
    }
    @Size(min=1) 
    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Min(0)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @Size(min=1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedBy() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    
    
    
}


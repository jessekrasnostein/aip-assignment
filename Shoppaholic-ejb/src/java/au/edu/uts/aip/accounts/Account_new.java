/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.accounts;

import au.edu.uts.aip.shoppingList.ShoppingList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jessekras
 */
@Entity
public class Account_new {
    
    @Id
    @GeneratedValue
    private int id;
    
    @NotNull(message = "Username is required")
    @Size(min = 3, message = "Username must be longer than 3 chars")
    private String username;
    
    @NotNull(message = "First name is required")
    @Size(min = 1, message = "First name is required")
    private String firstname;
    
    @NotNull(message = "Last name is required")
    @Size(min = 1, message = "Last name is required")
    private String lastname;
    
    @NotNull(message = "Password is required")
    @Size(min = 1, message = "Password is required")
    private String password;
    
        
    @NotNull(message = "Date of birth is required")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @NotNull(message = "Email is required")
    @Size(min = 1, message = "Email is required")
    private String email;
    
    private List<ShoppingList> shoppingLists = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

//    
//    ACCOUNT
//     -List of ShoppingLists
//
//     Shopping List
//     -List of ShoppingListItems
//
//     ShoppingListItems
//
//
//     FREE
//     -ONLY ONE LIST
//     -ONYL 10 items in total.
//
//     PRO
//     -5 Lists
//     -20 Items per list
//
//     ULTIMATE
//     -Unlimited Lists
//     -Unlimited Items per list

  
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }
    
    
    
    
}

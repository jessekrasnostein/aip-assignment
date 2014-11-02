package au.edu.uts.aip.accounts;

import au.edu.uts.aip.shoppingList.ShoppingList;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Account implements Serializable {
   
    private int id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private Date dateOfBirth;
    private String email;
    private List<ShoppingList> shoppingLists = new ArrayList<>();
    private SubscriptionPlan plan;
    
    @Enumerated(EnumType.STRING)
    public SubscriptionPlan getSubscriptionPlan() {
        return plan;
    }

    public void setSubscriptionPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }
    
    /*
    ACCOUNT
     -List of ShoppingLists

     Shopping List
     -List of ShoppingListItems

     ShoppingListItems


     FREE
     -ONLY ONE LIST
     -ONYL 10 items in total.

     PRO
     -5 Lists
     -20 Items per list

     ULTIMATE
     -Unlimited Lists
     -Unlimited Items per list

    */
    
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
   // @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(List<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }
    
    @NotNull(message = "Password is required")
    @Size(min = 1, message = "Password is required")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "Password is required")
    @Size(min = 1, message = "Password is required")
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "First name is required")
    @Size(min = 1, message = "First name is required")
    public String getFirstname() {
        return firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @NotNull(message = "Last name is required")
    @Size(min = 1, message = "Last name is required")
    public String getLastname() {
        return lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    @NotNull(message = "Date of birth is required")
    @Temporal(TemporalType.DATE)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @NotNull(message = "Email is required")
    @Size(min = 1, message = "Email is required")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}

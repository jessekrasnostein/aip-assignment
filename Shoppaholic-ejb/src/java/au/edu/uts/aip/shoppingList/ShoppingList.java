package au.edu.uts.aip.shoppingList;

import au.edu.uts.aip.accounts.Account;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ShoppingList implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    /*
    * Name of Shopping List
    */
    private String name;

    /**
     * Shopping List Items
     */
    @OneToMany(mappedBy = "shoppingList", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ShoppingItem> items = new ArrayList<>();

    /**
     * Account ID owner for the user
     */
    @Column(name = "ACCT_ID")
    private int acctId;

    /**
     * An Account can be linked to multiple Lists
     */
    @ManyToOne(optional = false)
    @PrimaryKeyJoinColumn(name = "ACCT_ID", referencedColumnName = "ACCT_ID")
    public Account account;

    /**
     * Get Name of List and Validation of field
     * @return 
     */
    @NotNull(message = "A Name is required for the List")
    @Size(min = 1, message = "A Name is required for the List")
    public String getName() {
        return name;
    }

    /**
     * Set name of Shopping List
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get id of Shopping list
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Set id of Shopping list
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return ShoppingList Items
     * @return 
     */
    public List<ShoppingItem> getShoppingListItems() {
        return items;
    }

    /**
     * Return Account Id
     * @return acctId
     */
    public int getAcctId() {
        return acctId;
    }

    /**
     * Set Account owner
     * @param acctId Account ID for lists
     */
    public void setAcctId(int acctId) {
        this.acctId = acctId;
    }

    /**
     * Get Account
     * @param account Account object
     */
    public Account getAccount() {
        return account;
    }
    
    /**
     * Set Account
     * @param account Account object
     */
    public void setAccount(Account account) {
        this.account = account;
    }

}

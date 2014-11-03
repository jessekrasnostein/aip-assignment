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

    private String name;

    @OneToMany(mappedBy = "shoppingList")
    private List<ShoppingItem> items = new ArrayList<>();

    @Column(name = "ACCT_ID")
    private int acctId;

    @ManyToOne(optional = false)
    @PrimaryKeyJoinColumn(name = "ACCT_ID", referencedColumnName = "ACCT_ID")
    public Account account;

    @NotNull(message = "A Name is required for the List")
    @Size(min = 1, message = "A Name is required for the List")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ShoppingItem> getShoppingListItems() {
        return items;
    }

    public int getAcctId() {
        return acctId;
    }

    public void setAcctId(int acctId) {
        this.acctId = acctId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}

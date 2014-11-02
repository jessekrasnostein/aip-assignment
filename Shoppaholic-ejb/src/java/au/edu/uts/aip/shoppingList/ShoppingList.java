package au.edu.uts.aip.shoppingList;

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
    
    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL)
    private List<ShoppingItem> items = new ArrayList<>();
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @NotNull(message = "A Name is required for the List")
    @Size(min = 1, message = "A Name is required for the List")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<ShoppingItem> getShoppingListItems() {
        return items;
    }

}

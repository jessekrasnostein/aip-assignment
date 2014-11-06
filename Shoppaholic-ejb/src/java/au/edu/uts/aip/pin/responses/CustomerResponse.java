    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.pin.responses;

import java.io.*;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author jessekras
 */
@XmlRootElement
public class CustomerResponse implements Serializable {
    
    private CustomerResponse response;

    public CustomerResponse getResponse() {
        return response;
    }

    public void setResponse(CustomerResponse response) {
        this.response = response;
    }
    
    private String token;
    private String email;
    private String created_at;
    private CardResponse card;
    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public CardResponse getCard() {
        return card;
    }

    public void setCard(CardResponse card) {
        this.card = card;
    }

    
    
    
    
}

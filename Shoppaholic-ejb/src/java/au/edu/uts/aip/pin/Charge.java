/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.pin;

/**
 *
 * @author jessekras
 */
public class Charge {
    
    private String email;
    //Amount to be charged in cents >= 100
    private String amount;
    //Description of what the card is for
    private String desc = ""; 
    //Full credit card number of the card to be charged
    private String card;
    //Card token as stored in the db to card
    private String card_token;
    //IP Address of the originating client
    private String ip;
    
    

    public Charge(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCard_token() {
        return card_token;
    }

    public void setCard_token(String card_token) {
        this.card_token = card_token;
    }


    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.pin;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jessekras
 */
@XmlRootElement(name = "card")
public class Card implements Serializable{
    

    private String number;
    private String expiry_month;
    private String expiry_year;
    private String cvc;
    private String name;
    private String address_line1;
    private String address_line2;
    private String address_city;
    private String address_postcode;
    private String address_state;
    private String address_country;

    public Card() {
        
    }

    @Override
    public String toString() {
        return "CCard{" + " number=" + number + ", expiry_month=" + expiry_month + ", expiry_year=" + expiry_year + ", cvc=" + cvc + ", name=" + name + ", address_line1=" + address_line1 + ", address_line2=" + address_line2 + ", address_city=" + address_city + ", address_postcode=" + address_postcode + ", address_state=" + address_state + ", address_country=" + address_country + '}';
    }
    
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiry_month() {
        return expiry_month;
    }

    public void setExpiry_month(String expiry_month) {
        this.expiry_month = expiry_month;
    }

    public String getExpiry_year() {
        return expiry_year;
    }

    public void setExpiry_year(String expiry_year) {
        this.expiry_year = expiry_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_postcode() {
        return address_postcode;
    }

    public void setAddress_postcode(String address_postcode) {
        this.address_postcode = address_postcode;
    }

    public String getAddress_state() {
        return address_state;
    }

    public void setAddress_state(String address_state) {
        this.address_state = address_state;
    }

    public String getAddress_country() {
        return address_country;
    }

    public void setAddress_country(String address_country) {
        this.address_country = address_country;
    }
}

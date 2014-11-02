/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.pin;

import au.edu.uts.aip.accounts.Account;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 *
 * @author jessekras
 */
public class Charges {
    
    private static final String targetEndpoint = "charges";
    private final PinConfig pinConfig = new PinConfig();
    
    
    public static void main(String[] args) {
        Account a = new Account();
        Charge c = new Charge("150");
        
        a.setEmail("jessekras@gmail.com");
        c.setCard_token("card_0utaijxS1-LBIz2iCV8I1Q");
        c.setIp("172.50.40.123");
        Test t = new Test();
        t.create(a,c);
        
        
        return;
    }
    
    
    public void create(Account account, Charge charge) {
        //call pinpayments api with account email and charge details
        Client client = pinConfig.pinClient(); //remove this line and use in class context
        WebTarget wt = client.target(pinConfig.getTarget());
        
        Form form = new Form();
        form.param("email", account.getEmail());
        form.param("card_token", charge.getCard_token());
        form.param("amount", charge.getAmount());
        form.param("ip_address", charge.getIp());
        form.param("desc", charge.getDesc());
        Charge bean = wt.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), 
                        Charge.class);
        System.out.println(bean);
        
        
//        
//        
//       // Request request = client.target(pinConfig.getTarget());
//        //pinConfig.pinClient().target(pinConfig.getTarget())
//        Response response = client.target(pinConfig.getTarget())
//                .queryParam("email", account.getEmail())
//                .queryParam("card_token", charge.getCard_token())
//                .queryParam("amount", charge.getAmount())
//                .queryParam("ip_address", charge.getIp())
//                .queryParam("desc", charge.getDesc())
//                .request("application/json")
//                .buildPost(Entity<?>);
        
        //System.out.println(response.toString());
        
    }
    
}

//curl https://test-api.pin.net.au/1/customers -u 6ir6nigWNX5AAsh4QubHfg: \
// -d "email=roland@pin.net.au" \
// -d "card[number]=5520000000000000" \
// -d "card[expiry_month]=05" \
// -d "card[expiry_year]=2015" \
// -d "card[cvc]=123" \
// -d "card[name]=Roland Robot" \
// -d "card[address_line1]=42 Sevenoaks St" \
// -d "card[address_line2]=" \
// -d "card[address_city]=Lathlain" \
// -d "card[address_postcode]=6454" \
// -d "card[address_state]=WA" \
// -d "card[address_country]=Australia"

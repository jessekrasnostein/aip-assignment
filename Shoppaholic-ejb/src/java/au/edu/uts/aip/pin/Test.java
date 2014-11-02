/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.pin;

import au.edu.uts.aip.accounts.Account;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jessekras
 */
public class Test {
    
        private static final String targetEndpoint = "charges";
        private final PinConfig pinConfig = new PinConfig();
        
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

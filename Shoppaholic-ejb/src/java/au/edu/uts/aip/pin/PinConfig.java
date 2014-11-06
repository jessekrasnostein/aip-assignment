/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.pin;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.core.Response;
/**
 *
 * @author jessekras
 */
public class PinConfig {
    private static final String ENDPOINT = "https://test-api.pin.net.au/";
    private static final String API_VERSION = "1/";
    private final String API_SECRET_KEY = "6ir6nigWNX5AAsh4QubHfg";
    
    public static String getTarget() {
        return ENDPOINT + API_VERSION;
    }
    
    public  String getKey() {
        return API_SECRET_KEY;
    }
    
    public Client pinClient() {
        Client client = ClientBuilder.newClient().register(new Authenticator(API_SECRET_KEY));
        //
        return client;
       
    }
}

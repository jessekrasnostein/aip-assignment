package au.edu.uts.aip.shoppaholic.web;

import au.edu.uts.aip.accounts.Account;
import au.edu.uts.aip.accounts.AccountBean;
import au.edu.uts.aip.accounts.SubscriptionPlan;
import au.edu.uts.aip.pin.Authenticator;
import au.edu.uts.aip.pin.requests.ChargeRequest;
import au.edu.uts.aip.pin.requests.CustomerRequest;
import au.edu.uts.aip.pin.charge.FullChargeResponse;
import au.edu.uts.aip.pin.responses.CustomerResponse;
import au.edu.uts.aip.pin.responses.FullCustomerResponse;
import java.net.InetAddress;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.*;
import javax.faces.application.*;
import javax.faces.context.*;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.*;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.*;

@Named
@RequestScoped
public class PaymentController {

    @Resource(name = "pinService")
    String pinService;
    @Resource(name = "proPlanAmount")
    String proPlanAmount;
    @Resource(name = "ultimatePlanAmount")
    String ultimatePlanAmount;

    @Resource(name = "pinPaymentsAPIPrivateKey")
    String pinPaymentsAPIPrivateKey;

    @EJB
    private AccountBean accounts;

    private SubscriptionPlan plan;
    
    private CustomerRequest request = new CustomerRequest();
    private FullCustomerResponse response;

    /**
     * Get the customer request object
     *
     * @return CustomerRequest object
     */
    public CustomerRequest getRequest() {
        return request;
    }

    /**
     * Set the customer request object up from webform
     *
     * @param request
     */
    public void setRequest(CustomerRequest request) {
        this.request = request;
    }

    public String pay() {
        Account account;
        if (AccountsController.getCurrentUser().getToken()==null) {
            account = createCustomer(); 
        } else {
            account = AccountsController.getCurrentUser(); 
        }
        
        chargeCustomer(account);
        return "home";
    }
    
    public Account createCustomer() {
        //find the account currently logged in
        Account account = accounts.findByUsername(AccountsController.getCurrentUser().getUsername());
        //Add the users current email to the request
        request.setEmail(AccountsController.getCurrentUser().getEmail());

        //Create a new JAX RS client to send json request to
        Client client = null;

        try {
            /**
             * Instantiate client and register it with the Authenticator and
             * apiKey
             */
            client = ClientBuilder.newClient()
                    .register(new Authenticator(pinPaymentsAPIPrivateKey));

            /**
             * Send the request object to the client as JSON and catch the
             * response
             */
            response = client.target(pinService + "customers")
                    .request()
                    .post(Entity.json(request), FullCustomerResponse.class);

            
            //add the token to the account from the response to the account
            account.setToken(response.getResponse().getToken());
            accounts.update(account);

            // Charge Customer Account
            //chargeCustomer(account);

        } catch (ProcessingException | WebApplicationException e) {
            Logger log = Logger.getLogger(this.getClass().getName());
            log.log(Level.SEVERE, "Could not communicate with Pin Payments API service", e);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("An error occurred communicating with the Pin Payments API server, please try again later"));
            // Stay on the same page in the event of an error
            //return null;
        } finally {
            // make sure that client is closed, if it was created
            if (client != null) {
                client.close();
            }
        }

        return account;
    }

    /**
     * Charge Account with CreditCard details and get a response to handle
     * subscription of the Account
     *
     * @param account Account that the PinPayments API will Charge to and return
     * a response
     */
    public void chargeCustomer(Account account) {

        FacesContext context = FacesContext.getCurrentInstance();

        // Initiate a new Client for REST API
        Client client = null;

        // Initiate a Charge Request Object 
        ChargeRequest chargeRequest = new ChargeRequest();

        // Initiate a Charge Response Object
        FullChargeResponse chargeResponse;
        account.setPlan(plan);
        
        // Set Account Email, Description, Ip Address, Amount and Customer Token to Charge Request
        chargeRequest.setEmail(account.getEmail());
        chargeRequest.setDescription(account.getPlan().toString() + " Subscription Plan 1 Month Payment");
        chargeRequest.setIp_address(getCustomerIp());
        
        // Switch Statement to set Amount to be Charge
        switch (account.getPlan()) {
            case PRO:
                chargeRequest.setAmount(proPlanAmount);
                break;

            case ULTIMATE:
                chargeRequest.setAmount(ultimatePlanAmount);
                break;

            default:

                break;
        }
        //set the request customer token from the user account
        chargeRequest.setCustomer_token(account.getToken());
        try {
            client = ClientBuilder.newClient().register(new Authenticator(pinPaymentsAPIPrivateKey));
            
            chargeResponse = client.target(pinService + "charges")
                    .request()
                    .post(Entity.json(chargeRequest), FullChargeResponse.class);

            if (chargeResponse.getResponse().getSuccess().equalsIgnoreCase("true")) {

                // Create Date Object one Month from Purchase Date for Expiry Date
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, 1);

                // Create new Date for Expiry Date of Account Subscription
                Date date = new Date(cal.getTimeInMillis());

                //Set Expiry Date to Account
                account.setSubscriptionExpiry(date);

                //Update Account in Database
                accounts.update(account);

                context.addMessage(null,
                        new FacesMessage("Success: "
                                + account.getPlan().toString() 
                                + " Subscription Plan Purchased - "
                                + " Credit Card has been charged."));
                
                context.addMessage(null,
                        new FacesMessage(account.getPlan().toString() 
                                + " subscription plan Expires on "
                                + account.getSubscriptionExpiry().toString()));
            }
        } catch (ProcessingException | WebApplicationException e) {
            Logger log = Logger.getLogger(this.getClass().getName());
            log.log(Level.SEVERE, "Could not communicate with "
                    + "Pin Payments API service", e);
            context.addMessage(null, new FacesMessage("An error occurred "
                    + "communicating with the Pin Payments API server, "
                    + "please try again later"));

        } finally {
            // make sure that client is closed, if it was created
            if (client != null) {
                client.close();
            }
        }
    }
    
    private String getCustomerIp() {
        // Get IP Address of the localhost making the Payment
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = httpServletRequest.getRemoteAddr();
        return ip;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }
}

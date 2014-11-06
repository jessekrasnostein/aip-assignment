/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.uts.aip.shoppaholic.web;

import au.edu.uts.aip.accounts.Account;
import au.edu.uts.aip.accounts.AccountBean;
import au.edu.uts.aip.accounts.SubscriptionPlan;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jessekras
 */
@Named
@RequestScoped
public class AccountsController implements Serializable {

    private final Account account = new Account();
    
    @Resource(name = "defaultAccountType")
    String defaultAccountType;
    
    @EJB
    private AccountBean accounts;

    public Account getAccount() {
        return account;
    }

    public List<Account> getAllAccounts() {
        return accounts.allAccounts();
    }
    
    public String isAdmin() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) 
                context.getExternalContext().getRequest();
        System.out.println("Testing validity.");
        if (AccountsController.getCurrentUser().getAccountType().equalsIgnoreCase("USER")) {
            context.addMessage(null, new FacesMessage("Access Denied"));
            System.out.println("Check failed..");
            return "home?faces-redirect=true";
        }
        return null;
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) 
                context.getExternalContext().getRequest();
        try {
            request.login(account.getUsername(), account.getPassword());
            setCurrentUser();
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
        //if (account.getPlan() != SubscriptionPlan.FREE && account.getChargeToken())
        return "/app/home?faces-redirect=true";
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request
                = (HttpServletRequest) 
                context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            context.addMessage(null, 
                    new FacesMessage(e.getCause() + ": " + e.getMessage()));
        }
        return "/login?faces-redirect=true";
    }

    public String register() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) 
                context.getExternalContext().getRequest();
        try {  
            account.setAccountType(defaultAccountType);
            accounts.create(account);
            FacesMessage message = new FacesMessage("Account Created Sucessfully. Please Login Below");
            message.setSeverity(SEVERITY_INFO);
            context.addMessage(null, message);
            context.getExternalContext().getFlash().setKeepMessages(true);
        } catch (EJBException e) {
            context.addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
        return "login?faces-redirect=true";
    }
    
    // Set user in the session. 
    public void setCurrentUser() {
        Account currentAccount = accounts.findByUsername(account.getUsername());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("account", currentAccount);
        if (currentAccount.getCurrentList() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("currentList", currentAccount.getCurrentList());
        }
        
    }

    public static Account getCurrentUser() {
        return (Account) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("account");
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.uts.aip.shoppaholic.web;

import au.edu.uts.aip.accounts.Account;
import au.edu.uts.aip.accounts.AccountBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
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
    
    
    @EJB
    private AccountBean accounts;

    public Account getAccount() {
        return account;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) 
                context.getExternalContext().getRequest();
        try {
            request.login(account.getEmail(), account.getPassword());
            setCurrentUser();
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }
        return "/app/home";
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
        return "/login";
    }

//    public String register() {
//        AccountDAO.create(account);
//        return "login";
//    }
    
    // Set user in the session. 
    public void setCurrentUser() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .put("account", accounts.findByEmail(account.getEmail()));
    }

    public static Account getCurrentUser() {
        return (Account) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("account");
    }

}
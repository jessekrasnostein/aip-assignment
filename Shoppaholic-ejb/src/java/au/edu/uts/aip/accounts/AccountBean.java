/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package au.edu.uts.aip.accounts;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author jessekras
 */
@Stateless
public class AccountBean {
    
    @PersistenceContext
    private EntityManager em;
    
    public Account findByEmail(String email) {
        
        TypedQuery<Account> query = em.createQuery("select a from Account a "
                + "where a.email = :email ", Account.class);
        query.setParameter("email", email);
        return query.getResultList().get(0);
    }
    
    public void create(Account account) {
        Logger log = Logger.getLogger(this.getClass().getName());
        log.info("Saving account to database");
        em.persist(account);
      
        
    }
}

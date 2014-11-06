package au.edu.uts.aip.accounts;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class AccountBean {
    
    @PersistenceContext
    private EntityManager em;
    
    @Resource(name = "defaultAccountType")
    String defaultAccountType;
    
    public List<Account> allAccounts() {
        TypedQuery<Account> query = em.createQuery("select a from Account a",
                Account.class);
        return query.getResultList();
    }
    
    public Account findByEmail(String email) {
        
        TypedQuery<Account> query = em.createQuery("select a from Account a "
                + "where a.email = :email ", Account.class);
        query.setParameter("email", email);
        return query.getResultList().get(0);
    }
    
    public Account findByUsername(String username) {

        TypedQuery<Account> query = em.createQuery("select a from Account a "
                + "where a.username = :username ", Account.class);
        query.setParameter("username", username);
        return query.getResultList().get(0);
    }
    
    public void create(Account account){
        account.setAccountType(defaultAccountType);
        
        try {
            account.setPassword(Sha.hash256(account.getPassword()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AccountBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        em.persist(account);
      
        
    }
    
    public void update(Account account) {
        em.merge(account);
    }
    
    
    
}

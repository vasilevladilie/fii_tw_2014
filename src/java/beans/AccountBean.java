/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.Serializable;
import javax.ejb.EJB;
import resources.entities.CUserAccount;
import services.CRequestProcessor;

/**
 *
 * @author vladilie
 */
/*
@ManagedBean(name="account")

@SessionScoped
*/
public class AccountBean implements Serializable {
    
    //@EJB
    protected CRequestProcessor m_requestProcessor;
    //@EJB
    protected CUserAccount m_userAccount;
    
    protected String m_strErrorMessage="";  
    /**
     * Creates a new instance of AccountManagerBean
     */
    public AccountBean() {
        m_requestProcessor = CRequestProcessor.getInstance();
        m_userAccount = new CUserAccount();
    }
    
    public void setErrorMessage(String strErr) {
        m_strErrorMessage = strErr;
    }
    public String getErrorMessage() {
        return m_strErrorMessage;
    }
    
    public String getUsername() {
        return m_userAccount.getUsername();
    }
    public String getPassword() {
        return m_userAccount.getPassword();
    }
    public void setUsername(String strUsername) {
        m_userAccount.setUsername(strUsername);
    }
    public void setPassword(String strPassword) {
        m_userAccount.setPassword(strPassword);
    }
    public void setEmail(String strEmail) {
        m_userAccount.setEmail(strEmail);
    }
    public String getEmail() {
        return m_userAccount.getEmail();
    }
    public void setPhoneNumber(String strPhoneNumber) {
        m_userAccount.setPhoneNumber(strPhoneNumber);
    }
    public String getPhoneNumber() {
        return m_userAccount.getPhoneNumber();
    }
    public void setAddress(String strAddress) {
        m_userAccount.setAddress(strAddress);
    }
    public String getAddress() {
        return m_userAccount.getAddress();
    }
            
    public String login() {
        String strNextView ="";
        strNextView = m_requestProcessor.isAccountValid(m_userAccount.getUsername()
                                                        ,m_userAccount.getPassword());
        return strNextView;
    }
    public String register() {
        String strNextView = "";
        if(0 < m_userAccount.getUsername().length()) {
            if(0 < m_userAccount.getPassword().length()) {
                strNextView = m_requestProcessor.createAccount(  m_userAccount.getUsername()
                                                                ,m_userAccount.getPassword());
            }
            else {
                setErrorMessage("No password provided!");
                strNextView = CRequestProcessor.strERROR_PAGE;
            }
        }
        else {
                setErrorMessage("Before going to registration page, enter your username!");
                strNextView = CRequestProcessor.strERROR_PAGE;
        }
        return strNextView;
    }
    public String completeRegistration() {
        String strNextView ="";
        strNextView = m_requestProcessor.updateAccount(m_userAccount);
        return strNextView;
    }    
}

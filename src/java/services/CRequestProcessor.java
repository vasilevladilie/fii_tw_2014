/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.util.ArrayList;
import java.util.HashMap;
import resources.entities.CChangeRate;
import resources.entities.CUserAccount;

/**
 *
 * @author vladilie
 * @description This class is the controller,
 *              hence the view chooser, for the drool application
 */
public class CRequestProcessor {

    public static final String strREGISTRATION_COMPLETION="registration_completion";
    public static final String strSUCCESSFUL_LOGIN="successful_login";
    public static final String strLOGOUT="logout";
    public static final String strBACK_TO_HOMEPAGE="back_to_homepage";
    public static final String strUSER_PROFILE="user_profile";
    public static final String strALERTS="alerts";
    public static final String strERROR_PAGE ="error_page";
    protected String m_strErrorMessage ="";
    protected static CRequestProcessor s_requestProcessor;
    protected CServicesBridge m_servicesBridge;
    static {
        s_requestProcessor = null;
    }
    
    public CRequestProcessor(){
        m_servicesBridge = CServicesBridge.getInstance();
    }
    public static CRequestProcessor getInstance() {
        if(null == s_requestProcessor){
            s_requestProcessor = new CRequestProcessor();
        }
        return s_requestProcessor;
    }
    public String getErrorMessage() {
        return m_strErrorMessage;
    }
    public void setErrorMessage(String strErr) {
        m_strErrorMessage = strErr;
    }
    public String createAccount(String strUsername
                               ,String strPassword) {
        String strNextView = new String();
        boolean bRetVal = true;
        setErrorMessage("");
        bRetVal = m_servicesBridge.createAccount(strUsername, strPassword);
        if(true == bRetVal) {
            strNextView = strREGISTRATION_COMPLETION;
        }
        else {
            setErrorMessage("Cannot create account!");
            strNextView = strBACK_TO_HOMEPAGE;
        }
        return strNextView;
    }
    public String updateAccount( CUserAccount userAccount) {
        String strNextView = new String();
        boolean bRetVal = true;
        setErrorMessage("");
        bRetVal = m_servicesBridge.updateAccount(userAccount);
        if(true == bRetVal) {
            strNextView = strSUCCESSFUL_LOGIN;
        }
        else {
            setErrorMessage("Complete all the fields!");
            strNextView = strREGISTRATION_COMPLETION;
        }
        return strNextView;
    }
    public String isAccountValid(String strUserName
                                ,String strPassword) {
        String strNextView = new String();
        
        int iRetVal = CServicesBridge.iACCOUNT_IS_VALID;
        setErrorMessage("");
        iRetVal = m_servicesBridge.isAccountValid(strUserName, strPassword);
        
        if(CServicesBridge.iACCOUNT_IS_VALID == iRetVal) {
            strNextView = strSUCCESSFUL_LOGIN;            
        }
        else if(CServicesBridge.iACCOUNT_DB_ERROR == iRetVal){
            strNextView = strERROR_PAGE;
            m_strErrorMessage = "Database error, please verify again your credentials!";
        }
        else if(CServicesBridge.iACCOUNT_DOES_NOT_EXIST == iRetVal) {
            strNextView = strBACK_TO_HOMEPAGE;
            m_strErrorMessage = "Account does not exist!";
        }
        else if(CServicesBridge.iACCOUNT_IS_NOT_COMPLETE == iRetVal) {
            strNextView = strREGISTRATION_COMPLETION;
            m_strErrorMessage = "Account is not complete!";
        }
        else if(CServicesBridge.iACCOUNT_WRONG_PASSWORD == iRetVal) {
            strNextView = strBACK_TO_HOMEPAGE;
            m_strErrorMessage = "Wrong password!";
        }
        else {
            //CRITICAL ERROR
            //THIS CASE SHOULD BE IMPOSSIBLE
            //A LOG HERE COULD BE USEFUL
        }
        return strNextView;
    }
    public CUserAccount getUserInfo( String strUsername
                                               ,String strPassword) {
        CUserAccount userAccount;
        userAccount = m_servicesBridge.getUserInfo(strUsername, strPassword);
        return userAccount;
    }
    public ArrayList<CChangeRate> getChangeRates() {
        ArrayList<CChangeRate> arrChangeRates = new ArrayList<CChangeRate>();
        
        
                
        return arrChangeRates;
    }

}

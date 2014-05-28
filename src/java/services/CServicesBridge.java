/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import com.mongodb.BasicDBObject;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import resources.entities.CUserAccount;
/**
 *
 * @author vladilie
 */
public class CServicesBridge {
    protected CBtcEClient m_btceClient;
    protected CMongoDbStorage m_mongoDb;
    protected CLogging m_logger;
    protected static CServicesBridge s_BridgeInstance =null;
    /*!
        \brief Assign the data expiration time in seconds
    */
    protected static final long lDATA_EXPIRATION_TIME = 5;
    protected static final long lALL_REQUESTS_PROCESSING_TIME = 1;
    /*!
        \brief The info needed to connect to the mongo db
    */
    protected static final int s_iPORT = 27017;
    protected static final String s_strHOST ="127.0.0.1";
    
    public static final int iACCOUNT_IS_VALID        =0;
    public static final int iACCOUNT_IS_NOT_COMPLETE =1;
    public static final int iACCOUNT_WRONG_PASSWORD  =2;
    public static final int iACCOUNT_DOES_NOT_EXIST  =3;
    public static final int iACCOUNT_DB_ERROR = 4;
    public CServicesBridge( CBtcEClient btceClient
                           ,CMongoDbStorage mongoDb
                           ,CLogging logger) {
        m_btceClient = btceClient;
        m_mongoDb = mongoDb;
        m_logger = logger;
    }
    public static CServicesBridge getInstance() {
        if(null == s_BridgeInstance){
            CBtcEClient btceClient = new CBtcEClient();
            CLogging logger = new CLogging();
            logger.setupLogger();
            CMongoDbStorage mongoDb = new CMongoDbStorage(s_strHOST, s_iPORT);
            s_BridgeInstance = new CServicesBridge(btceClient, mongoDb, logger);
        }
        return s_BridgeInstance;
    }
    protected void requestChangeRate(String strCurrencyPair
                                ,StringBuffer strDocument
                                ,long lDataExpirationTime) {
        StringBuffer strbufDbDocument;
        strbufDbDocument = new StringBuffer("");
        m_mongoDb.retrieveCurrenciesDocument(strCurrencyPair,strbufDbDocument);
        boolean bShouldBeReplaced = true;
        if(0 < strbufDbDocument.length()) {
            m_logger.writeLog("[CServicesBridge][requestChangeRate]BufDbDocument "
                    +strbufDbDocument.toString());
            BasicDBObject dbJsonObject = m_mongoDb.createJsonObject(strbufDbDocument.toString());
            
            java.lang.Object longObj = dbJsonObject.get(CMongoDbStorage.UPDATE_TIME_FIELD_NAME);
            
            //m_logger.writeLog("[CServicesBridge][requestChangeRate]"
            //        + "bdecimalDbUpdateTime = ");
            long lDbUpdateTime = (long)Double.parseDouble(longObj.toString());
            m_logger.writeLog("[CServicesBridge][requestChangeRate]"
                + "lDbUpdateTime = "+ lDbUpdateTime);            
            if(m_mongoDb.isExpired(lDbUpdateTime, lDataExpirationTime)) {
                bShouldBeReplaced =true;
            }
            else {
                bShouldBeReplaced = false;
                dbJsonObject.remove("_id");
                dbJsonObject.remove("$oid");
                strbufDbDocument.delete(0, strbufDbDocument.length());
                strbufDbDocument.append(dbJsonObject.toString());
            }
            m_logger.writeLog("[CServicesBridge][requestChangeRate]"
                + "bShouldBeReplaced = "+ bShouldBeReplaced);
            
        }
        if(true == bShouldBeReplaced) {
            try {
                m_btceClient.requestChangeRate(strCurrencyPair);
                String strChangeRate = m_btceClient.getRequestResult();
                m_mongoDb.updateCurrenciesDocument(
                                         strCurrencyPair
                                        ,strChangeRate
                                        ,lDataExpirationTime
                                        );
                BasicDBObject dbJsonObject = m_mongoDb.createJsonObject(strChangeRate);
                long lCurrentTime = System.currentTimeMillis();
                dbJsonObject.put(CMongoDbStorage.UPDATE_TIME_FIELD_NAME, (double)lCurrentTime);
                dbJsonObject.put(CMongoDbStorage.CURRENCY_PAIR_FIELD_NAME, strCurrencyPair);
                strDocument.append(dbJsonObject.toString());
            } catch (IOException
                    | NoSuchAlgorithmException
                    | KeyStoreException
                    | KeyManagementException 
                    | CertificateException ex) {
                m_logger.writeLog(CServicesBridge.class.getName()+ ex.getMessage());
            }
        }
        else {
            strDocument.append(strbufDbDocument.toString());
        }
    }
    public void getChangeRate(String strCurrencyPair
                              ,StringBuffer strDocument) {
        long lDataExpirationTime = lDATA_EXPIRATION_TIME;
        requestChangeRate(strCurrencyPair, strDocument, lDataExpirationTime);
    }
    public void getAllChangeRates(StringBuffer strDocuments) {
        long lDataExpirationTime = lDATA_EXPIRATION_TIME - lALL_REQUESTS_PROCESSING_TIME;
        strDocuments.delete(0, strDocuments.length());
        strDocuments.append("{ \"currencies\" : [");
        int iCounter;
        long lBegin;
        long lTimePassed;
        for(iCounter =0;
            iCounter<IBtcEClientDefs.arrCURRENCIES.length;
            ++iCounter) {
            lBegin = System.currentTimeMillis();
            requestChangeRate(
                         IBtcEClientDefs.arrCURRENCIES[iCounter]
                        ,strDocuments
                        ,lDataExpirationTime
                        );
            lTimePassed = lBegin - System.currentTimeMillis();
            lDataExpirationTime += lTimePassed;
            if(iCounter < IBtcEClientDefs.arrCURRENCIES.length -1) {
                strDocuments.append(",");
            }
        }
        strDocuments.append("] }");
    }
    
    public boolean createAccount(String strUsername, String strPassword) {
        boolean bRetVal = true;
        
        bRetVal = m_mongoDb.insertUserAccount(strUsername, strPassword);
        
        return bRetVal;
    }
    public boolean updateAccount(CUserAccount userAccount) {
        boolean bRetVal = true;
        HashMap<String, String> mapUserAccountInfo = new HashMap<String,String>();
        mapUserAccountInfo.put(CUserAccount.strUSERNAME, userAccount.getUsername());
        mapUserAccountInfo.put(CUserAccount.strPASSWORD, userAccount.getPassword());
        mapUserAccountInfo.put(CUserAccount.strEMAIL, userAccount.getEmail());
        mapUserAccountInfo.put(CUserAccount.strADDRESS, userAccount.getAddress());
        mapUserAccountInfo.put(CUserAccount.strPHONE_NUMBER, userAccount.getPhoneNumber());
        
        bRetVal = m_mongoDb.updateUserAccount(userAccount.getUsername(),mapUserAccountInfo);
        
        return bRetVal;
    }
    public int isAccountValid(String strUserName, String strPassword) {
        /*
           iACCOUNT_IS_VALID
           iACCOUNT_IS_NOT_COMPLETE
           iACCOUNT_DOES_NOT_EXIST
           iACCOUNT_WRONG_PASSWORD
        */
        int iRetVal =iACCOUNT_IS_VALID;
        
        iRetVal = m_mongoDb.isAccountValid(strUserName, strPassword);
        
        return iRetVal;
    }
    public CUserAccount getUserInfo( String strUsername
                                    ,String strPassword) {
        
        CUserAccount userAccount = new CUserAccount();
        HashMap<String, String> mapUserAccountInfo;
        mapUserAccountInfo = m_mongoDb.getUserInfo(strUsername, strPassword);
        
        if(true == mapUserAccountInfo.containsKey("is_complete")) {
            if("true".equals(mapUserAccountInfo.get("is_complete"))) {
                String strFieldValue = "";
                strFieldValue = mapUserAccountInfo.get(CUserAccount.strPHONE_NUMBER);
                userAccount.setPhoneNumber(strUsername);
                strFieldValue = mapUserAccountInfo.get(CUserAccount.strEMAIL);
                userAccount.setEmail(strFieldValue);
                strFieldValue = mapUserAccountInfo.get(CUserAccount.strADDRESS);
                userAccount.setAddress(strFieldValue);
                strFieldValue = mapUserAccountInfo.get(CUserAccount.strUSERNAME);
                userAccount.setUsername(strFieldValue);
                strFieldValue = mapUserAccountInfo.get(CUserAccount.strPASSWORD);
                userAccount.setPassword(strFieldValue);
            }
        }
        return userAccount;
    }
}

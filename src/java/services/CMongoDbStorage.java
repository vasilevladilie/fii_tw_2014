/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

/**
 *
 * @author vladilie
 */

import com.mongodb.*;
import java.io.StringReader;
import java.net.UnknownHostException;
import java.util.HashMap;
import javax.json.Json;
import javax.json.stream.JsonParser;

public class CMongoDbStorage {
    protected static String MONGO_DB_NAME ="bitcoin_db";
    protected static String MONGO_DB_COLLECTION_NAME ="DroolData";
    public static String UPDATE_TIME_FIELD_NAME ="update_field";
    public static String CURRENCY_PAIR_FIELD_NAME ="currency_pair";
    protected MongoClient m_dbMongoDbClient;
    protected DBCollection m_dbDroolCurrencies;
    protected DB m_dbDroolDb;
    protected String m_strDbHost;
    protected int m_iDbPort;
    protected static CLogging s_LoggerInstance;
    public CMongoDbStorage(String p_strDbHost, int p_iDbPort) {
        m_strDbHost = p_strDbHost;
        m_iDbPort = p_iDbPort;
        s_LoggerInstance = CLogging.getInstance();
        s_LoggerInstance.setupLogger();
        try {
            m_dbMongoDbClient = new MongoClient(m_strDbHost,m_iDbPort);
        } catch (UnknownHostException ex) {
            m_dbMongoDbClient =null;
            s_LoggerInstance.writeSevereLog("["+CMongoDbStorage.class.getName()+"]"+
                    ex.getMessage());
        }
        if(null != m_dbMongoDbClient) {
            m_dbDroolDb = m_dbMongoDbClient.getDB(MONGO_DB_NAME);
            m_dbDroolCurrencies = m_dbDroolDb.getCollection(MONGO_DB_COLLECTION_NAME);
        }
    }
    public void closeDatabase() {
        m_dbMongoDbClient.close();
    }
    public boolean isExpired(long lTimeUpdated
                                ,long lExpirationTime) {
        s_LoggerInstance.writeLog("[CMongoDbStorage][isExpired] UpdateTime = " 
                + lTimeUpdated +" ExpTime = " + lExpirationTime);
        long lCurrentTime = System.currentTimeMillis();
        long lTimePassed = (lCurrentTime - lTimeUpdated)/1000;
        s_LoggerInstance.writeLog("[CMongoDbStorage][isExpired] TimePassed = " 
        + lTimePassed);
        boolean bRetVal = true;
        if(lExpirationTime < lTimePassed) {
            bRetVal =true;
        }
        else {
            bRetVal = false;
        }
        
        return bRetVal;
    }
    public BasicDBObject createJsonObject(String strDocument) {
       JsonParser jsonParser = Json.createParser(new StringReader(strDocument));
       BasicDBObject jsonObject = new BasicDBObject();
       javax.json.stream.JsonParser.Event jsonEvent;
       String strKeyName= "";
       java.math.BigDecimal bdValue = null;
       String strValue;
       boolean boolValue;
       boolean bHasPairs = true;
       boolean bIsTickerKey = false;
       s_LoggerInstance.writeLog("[CMongoDbStorage][createJsonObject] " + strDocument);
      
       try {
       while(jsonParser.hasNext()) {
           /* 
           if(true == jsonParser.hasNext()) {
           */     

                /*
            }
            else {
                bHasPairs = false;
            }
                */
            jsonEvent = jsonParser.next();
            if(javax.json.stream.JsonParser.Event.KEY_NAME == jsonEvent) {
                strKeyName = jsonParser.getString();
                if(0==strKeyName.compareTo("ticker")) {
                    bIsTickerKey =true;
                }
                else {
                    bIsTickerKey = false;
                }
            }
            else if((false == bIsTickerKey)
                    &&(javax.json.stream.JsonParser.Event.VALUE_NUMBER == jsonEvent)) {
                   bdValue = jsonParser.getBigDecimal();
                   double jsonDoubleValue = bdValue.doubleValue();
                   s_LoggerInstance.writeLog("[CMongoDbStorage][createJsonObject] "
                           + strKeyName + " : "+ jsonDoubleValue);
                   jsonObject.put(strKeyName, jsonDoubleValue);
            }
            else if((false ==bIsTickerKey)
                    &&(javax.json.stream.JsonParser.Event.VALUE_STRING == jsonEvent)){
                strValue = jsonParser.getString();
                s_LoggerInstance.writeLog("[CMongoDbStorage][createJsonObject] "
                        + strKeyName + " : " + strValue);
                jsonObject.put(strKeyName, strValue);
            }
            else if((false == bIsTickerKey)
                    &&(javax.json.stream.JsonParser.Event.VALUE_TRUE ==jsonEvent)) {
                boolValue = true;
                s_LoggerInstance.writeLog("[CMongoDbStorage][createJsonObject] "
                        + strKeyName + " : " + boolValue);
                jsonObject.put(strKeyName, boolValue);
            }
            else if((false ==bIsTickerKey)
                    &&(javax.json.stream.JsonParser.Event.VALUE_FALSE ==jsonEvent)) {
                boolValue = false;
                s_LoggerInstance.writeLog("[CMongoDbStorage][createJsonObject] "
                        + strKeyName + " : " + boolValue);
                jsonObject.put(strKeyName, boolValue);
            }
        }
       }
       catch(javax.json.stream.JsonParsingException ex) {
                s_LoggerInstance.writeSevereLog("[CMongoDbStorage]Exception "
                            + ex.getMessage());
       }
        if(false == bHasPairs){
            s_LoggerInstance.writeLog("[CMongoDbStorage]Does not have more pairs");
        }
        jsonParser =null;
       return jsonObject;
    }
    public void updateCurrenciesDocument( String strCurrencyPair
                                ,String strDocument
                                ,long lExpirationTime) {
       BasicDBObject basicDbQuery = new BasicDBObject("currency_pair", strCurrencyPair);
       DBCursor cursorChangeRates = m_dbDroolCurrencies.find(basicDbQuery);
       DBObject rate = null;
       String strCurrencyField="";
       long lUpdateTime  =0;
       long lCurrentTime =0;
       boolean bShouldBeInserted =true;
       s_LoggerInstance.writeWarningLog("[CMongoDbStorage][updateDocument] begin");
       
       while(cursorChangeRates.hasNext()) {
           rate = cursorChangeRates.next();
           s_LoggerInstance.writeWarningLog("[CMongoDbStorage][updateDocument] rate = " + rate.toString());
           s_LoggerInstance.writeSevereLog("[CMongoDbStorage][updateDocument] rate = " + rate.toString());
           s_LoggerInstance.writeLog("[CMongoDbStorage][updateDocument] rate = " + rate.toString());
           strCurrencyField ="";
           lUpdateTime =0;    
           if(rate.containsField(UPDATE_TIME_FIELD_NAME)) {
                if(rate.containsField(UPDATE_TIME_FIELD_NAME)) {
                    String strUpdateValue = rate.get(UPDATE_TIME_FIELD_NAME).toString();
                    if(null!=strUpdateValue) {
                        lUpdateTime = (long)Double.parseDouble(strUpdateValue);
                    }
                    else {
                         s_LoggerInstance.writeLog("[CMongoDbStorage]"
                                 + "[updateDocument]Field " + UPDATE_TIME_FIELD_NAME 
                            + " cannot be found in the rate object");
                    }
                }
                else {
                    s_LoggerInstance.writeLog("[CMongoDbStorage]"
                            + "[updateDocument]Field " + UPDATE_TIME_FIELD_NAME 
                            + " cannot be found in the rate object");
                }
           }
           if(rate.containsField(CURRENCY_PAIR_FIELD_NAME)) {
                strCurrencyField = rate.get(CURRENCY_PAIR_FIELD_NAME).toString();
           }
           if((0 < strCurrencyField.length())
                 &&(0 == strCurrencyField.compareTo(strCurrencyPair))) {
               if(isExpired(lUpdateTime, lExpirationTime)) {
                   //BasicDBObject dbObjQuery = new BasicDBObject()
                   /*!
                    \brief Remove the drool currency
                           from the database if the
                           expiration time has been reached
                   */
                   m_dbDroolCurrencies.remove(rate);
                   bShouldBeInserted =true;
               }
               else {
                   bShouldBeInserted =false;
               }
           }
           else {
               bShouldBeInserted =true;
           }
        }
       System.out.println("[CMongoDbStorage]updateDocument bShouldBeInserted = " + bShouldBeInserted);
       s_LoggerInstance.writeLog("[CMongoDbStorage][updateDocument] " + bShouldBeInserted);
       if(true == bShouldBeInserted) {
           s_LoggerInstance.writeSevereLog("[CMongoDbStorage][updateDocument] Should be inserted " + strDocument);
            
           s_LoggerInstance.writeSevereLog("[CMongoDbStorage][updateDocument] " + strDocument);
            BasicDBObject droolCurrencyPair = createJsonObject(strDocument);
            s_LoggerInstance.writeLog("[CMongoDbStorage][updateDocument]"
                    + " droolCurrencyPair " + droolCurrencyPair.toString());
            lCurrentTime = System.currentTimeMillis();
            droolCurrencyPair.put(UPDATE_TIME_FIELD_NAME, (double)lCurrentTime);
            droolCurrencyPair.put(CURRENCY_PAIR_FIELD_NAME, strCurrencyPair);
             /*!
              \brief Pairs were found therefore
                     insert the currency pair change rate into the database
             */
            m_dbDroolCurrencies.insert(droolCurrencyPair);
       }
       else {
            s_LoggerInstance.writeSevereLog("[CMongoDbStorage][updateDocument]"
                    + "Should not be inserted ");           
       }
        s_LoggerInstance.writeWarningLog("[CMongoDbStorage][updateDocument] end");
    }
    public void retrieveCurrenciesDocument(String strCurrencyPair
                                ,StringBuffer strDocument) {
       DBCursor currencyPairRetriever = null; //m_dbDroolCurrencies.find();
       DBObject queryObject = new BasicDBObject();
       DBObject currencyPair = null;
       s_LoggerInstance.writeSevereLog("[CMongoDbStorage][retrieveDocument] begin");
       if(0 < strDocument.length()) {
            strDocument.delete(0, strDocument.length());
       }
       queryObject.put(CURRENCY_PAIR_FIELD_NAME, strCurrencyPair);
       currencyPairRetriever = m_dbDroolCurrencies.find(queryObject);
       if(1 < currencyPairRetriever.count()) {
           //log
           s_LoggerInstance.writeSevereLog("["+CMongoDbStorage.class.getName()+"]"
                   + "[retrieveDocument]"+
              "More than one currency pair with the same name has been found in the database");
       }
       else if(0 >= currencyPairRetriever.count()){
           //log
           s_LoggerInstance.writeSevereLog("["+CMongoDbStorage.class.getName()+"]"
                   + "[retrieveDocument]"+
            "No currency pair with this name has been found in the database");
       }
       else if(currencyPairRetriever.hasNext()) {
           currencyPair = currencyPairRetriever.next();
           strDocument.append(currencyPair.toString());
           s_LoggerInstance.writeLog("[CMongoDbStorage][retrieveDocument]"
                   + " Document retrieved: " + strDocument.toString());
       }
       else {
           s_LoggerInstance.writeSevereLog("["+CMongoDbStorage.class.getName()+"]"
                   + "[retrieveDocument]"+
            "Something is wrong!");
       }
       s_LoggerInstance.writeSevereLog("[CMongoDbStorage][retrieveDocument] end");
    }
    
    public boolean insertUserAccount(String strUsername
                                    ,String strPassword) {
        boolean bRetVal = true;
        BasicDBObject userAccount = new BasicDBObject();
        userAccount.put("username", strUsername);
        userAccount.put("password", strPassword);
        userAccount.put("is_complete", "false");
        DBCursor userAccounts = m_dbDroolCurrencies.find(userAccount);
        if(0 >= userAccounts.count()) {
            m_dbDroolCurrencies.insert(userAccount);
        }
        else {
            bRetVal = false;
        }
        return bRetVal;
    }
    public boolean updateUserAccount(String strUsername
                                 ,HashMap<String, String> mapAdditionalInfo) {
        boolean bRetVal = true;
        BasicDBObject searchAccount = new BasicDBObject();
        searchAccount.put("username",strUsername);
        DBCursor foundAccounts = m_dbDroolCurrencies.find(searchAccount);
        if((0 >= foundAccounts.count())||(1 < foundAccounts.count())) {
            s_LoggerInstance.writeSevereLog("[CMongoDbStorage][updateUserAccount]"
                                        + "Two accounts with same credentials found in the db"
                                        + "or no account with this username found");
            bRetVal = false;
        }
        else {
            BasicDBObject userAccount = new BasicDBObject();
            for(String strKey : mapAdditionalInfo.keySet()) {
                if(null != mapAdditionalInfo.get(strKey)) {
                    userAccount.put(strKey, mapAdditionalInfo.get(strKey));
                }
            }
            userAccount.put("is_complete", "true");
            m_dbDroolCurrencies.update(searchAccount, userAccount);
        }
        return bRetVal;
    }
    public int isAccountValid(String strUsername, String strPassword) {
        int iRetVal =CServicesBridge.iACCOUNT_IS_VALID;
        BasicDBObject searchAccount = new BasicDBObject();
        searchAccount.put("username",strUsername);
        DBCursor foundAccounts = m_dbDroolCurrencies.find(searchAccount);
        if((0 >= foundAccounts.count())) {
            iRetVal = CServicesBridge.iACCOUNT_DOES_NOT_EXIST;
        }
        else if(1 < foundAccounts.count()) {
            iRetVal = CServicesBridge.iACCOUNT_DB_ERROR;
        }
        else {
            searchAccount.put("password", strPassword);
            foundAccounts = m_dbDroolCurrencies.find(searchAccount);
            if(0 >= foundAccounts.count()) {
                iRetVal = CServicesBridge.iACCOUNT_WRONG_PASSWORD;
            }
            else {
                searchAccount.put("is_complete", "true");
                foundAccounts = m_dbDroolCurrencies.find(searchAccount);
                if(0 >= foundAccounts.count()) {
                    iRetVal = CServicesBridge.iACCOUNT_IS_NOT_COMPLETE;
                }
                else {
                    iRetVal = CServicesBridge.iACCOUNT_IS_VALID;
                }
            }
        }               
        return iRetVal;
    }
    public HashMap<String, String> getUserInfo(String strUsername, String strPassword) {
        HashMap<String, String> mapAdditionalInfo = new HashMap<String,String>();
        BasicDBObject searchAccount = new BasicDBObject();
        searchAccount.put("username",strUsername);
        searchAccount.put("password", strPassword);
        mapAdditionalInfo.put("username", strUsername);
        mapAdditionalInfo.put("password", strPassword);
        DBCursor foundAccounts = m_dbDroolCurrencies.find(searchAccount);
        if(0 < foundAccounts.count()){
            DBObject foundObject = foundAccounts.next();
            for(String strKey : foundObject.keySet()) {
                mapAdditionalInfo.put(strKey, (String) foundObject.get(strKey));
            }
            mapAdditionalInfo.put("exists", "false");
        }
        else {
            mapAdditionalInfo.put("exists", "true");
        }
        return mapAdditionalInfo;
    }
}

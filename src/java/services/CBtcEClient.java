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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class CBtcEClient {
    protected String m_strRequestResult;
    protected int m_iHttpStatusCode;
    protected CLogging m_BtcEClientLogger;
    protected static final String s_strPATH_TO_CERTIFICATE = "/home/vladilie/btceserver.jks";
    protected static final String s_strPASSWORD = "password";
    public CBtcEClient() {
        m_strRequestResult ="";
        m_iHttpStatusCode =0;
        m_BtcEClientLogger = CLogging.getInstance();
    }
    public String getRequestResult() {
        return m_strRequestResult;
    }
    public int getHttpStatusCode() {
        return m_iHttpStatusCode;
    }
    private void queryRestService(String strRestResourcePath) 
                    throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        
        SSLSocketFactory sslFactory;
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        
        // get user password and file input stream
        String password = s_strPASSWORD;

        try (java.io.FileInputStream certificate = new java.io.FileInputStream(s_strPATH_TO_CERTIFICATE)) {
            keyStore.load(certificate, password.toCharArray());
        }
        TrustManagerFactory tmf = 
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, tmf.getTrustManagers(), null);
        sslFactory = ctx.getSocketFactory();
        
        URL url = new URL(strRestResourcePath);
        HttpsURLConnection restConn = (HttpsURLConnection)url.openConnection();
        restConn.setRequestMethod("GET");
        restConn.setSSLSocketFactory(sslFactory);
        //restConn.setRequestProperty("Accept", "application/json");
        restConn.setRequestProperty("Accept","text/html");
        
        if (restConn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                                + restConn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                                                (restConn.getInputStream())));
        String output;
        //m_strRequestResult ="s:";
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
                m_strRequestResult += output;
                m_BtcEClientLogger.writeLog(output);
                //System.out.println(output);
        }
        //m_strRequestResult =":s";
        restConn.disconnect();
    }
    public void requestChangeRate(String strCurrencyPair) 
            throws IOException
                  ,NoSuchAlgorithmException
                  ,KeyStoreException
                  ,KeyManagementException
                  ,CertificateException {
        
        String strRestResourcePath = IBtcEClientDefs.CHANGE_RATE_BTC_REST_API_BASE_URL;
        strRestResourcePath = strRestResourcePath.replace(
                                    IBtcEClientDefs.CURRENCY_PAIR_ARGUMENT_HOLDER
                                    ,strCurrencyPair
                                    );
        if(0 < m_strRequestResult.length()) {
            m_strRequestResult = "";
        }
        m_BtcEClientLogger.writeLog(strRestResourcePath);
        queryRestService(strRestResourcePath); 
    }
}
  


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources.rest_service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import services.CBtcEClient;
import services.IBtcEClientDefs;

/**
 * REST Web Service
 *
 * @author vladilie
 */
import services.CLogging;
import services.CServicesBridge;

public class QuoteResource {

    private String c1c2;
    protected static CLogging m_DroolLogger;
    
    
    static {
        m_DroolLogger = CLogging.getInstance();    
    }

    /**
     * Creates a new instance of QuoteResource
     */
    private QuoteResource(String c1c2) {
        this.c1c2 = c1c2;   
    }

    /**
     * Get instance of the QuoteResource
     */
    public static QuoteResource getInstance(String c1c2) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of Quote  Resource class.
        return new QuoteResource(c1c2);
    }

    /**
     * Retrieves representation of an instance of resources.QuoteResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        String strRequestResult ="";
        switch(c1c2) {
            case IBtcEClientDefs.BTC_EUR_CURRENCY_PAIR:
            case IBtcEClientDefs.BTC_CNH_CURRENCY_PAIR:
            case IBtcEClientDefs.BTC_GBP_CURRENCY_PAIR:
            case IBtcEClientDefs.BTC_RUR_CURRENCY_PAIR:
            case IBtcEClientDefs.BTC_USD_CURRENCY_PAIR:
            case IBtcEClientDefs.EUR_RUR_CURRENCY_PAIR:
            case IBtcEClientDefs.EUR_USD_CURRENCY_PAIR:
            case IBtcEClientDefs.FTC_BTC_CURRENCY_PAIR:
            case IBtcEClientDefs.GBP_USD_CURRENCY_PAIR:
            case IBtcEClientDefs.LTC_BTC_CURRENCY_PAIR:
            case IBtcEClientDefs.LTC_CNH_CURRENCY_PAIR:
            case IBtcEClientDefs.LTC_EUR_CURRENCY_PAIR:
            case IBtcEClientDefs.LTC_GBP_CURRENCY_PAIR:
            case IBtcEClientDefs.LTC_RUR_CURRENCY_PAIR:
            case IBtcEClientDefs.LTC_USD_CURRENCY_PAIR:
            case IBtcEClientDefs.NMC_BTC_CURRENCY_PAIR:
            case IBtcEClientDefs.NMC_USD_CURRENCY_PAIR:
            case IBtcEClientDefs.NVC_BTC_CURRENCY_PAIR:
            case IBtcEClientDefs.PPC_BTC_CURRENCY_PAIR:
            case IBtcEClientDefs.PPC_USD_CURRENCY_PAIR:
            case IBtcEClientDefs.TRC_BTC_CURRENCY_PAIR:
            case IBtcEClientDefs.USD_CNH_CURRENCY_PAIR:
            case IBtcEClientDefs.USD_RUR_CURRENCY_PAIR:
            case IBtcEClientDefs.XPM_BTC_CURRENCY_PAIR: {
                StringBuffer strDocumentBuffer = new StringBuffer("");
                CServicesBridge.getInstance().getChangeRate(c1c2,strDocumentBuffer);
                strRequestResult = strDocumentBuffer.toString();
                break;
            }
            default : {
                strRequestResult = "{Error : \'Unknown currency pair\'}";
            }
        }
        return strRequestResult;
    }

    /**
     * PUT method for updating or creating an instance of QuoteResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource QuoteResource
     */
    @DELETE
    public void delete() {
    }
}

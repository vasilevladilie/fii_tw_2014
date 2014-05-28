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
public interface IBtcEClientDefs {
    /*!
    * \brief: Defines some constants that will be used to retrieve information from the btc-e
    *         REST service
    */
    public static final String CURRENCY_PAIR_ARGUMENT_HOLDER = "%ARG%";
    
    public static final String CHANGE_RATE_BTC_REST_API_BASE_URL ="https://btc-e.com/api/2/"
                              +CURRENCY_PAIR_ARGUMENT_HOLDER+"/ticker";
    
    public static final String BTC_USD_CURRENCY_PAIR = "btc_usd";
    public static final String BTC_EUR_CURRENCY_PAIR = "btc_eur";
    public static final String BTC_RUR_CURRENCY_PAIR = "btc_rur";
    public static final String BTC_CNH_CURRENCY_PAIR = "btc_cnh";
    public static final String BTC_GBP_CURRENCY_PAIR = "btc_gbp";
    public static final String LTC_BTC_CURRENCY_PAIR = "ltc_btc";
    public static final String LTC_USD_CURRENCY_PAIR = "ltc_usd";
    public static final String LTC_RUR_CURRENCY_PAIR = "ltc_rur";
    public static final String LTC_EUR_CURRENCY_PAIR = "ltc_eur";
    public static final String LTC_CNH_CURRENCY_PAIR = "ltc_cnh";
    public static final String LTC_GBP_CURRENCY_PAIR = "ltc_gbp";
    public static final String NMC_BTC_CURRENCY_PAIR = "nmc_btc";
    public static final String NMC_USD_CURRENCY_PAIR = "nmc_usd";
    public static final String NVC_BTC_CURRENCY_PAIR = "nvc_btc";
    public static final String NVC_USD_CURRENCY_PAIR = "nvc_usd";
    public static final String USD_RUR_CURRENCY_PAIR = "usd_rur";
    public static final String EUR_USD_CURRENCY_PAIR = "eur_usd";                                
    public static final String EUR_RUR_CURRENCY_PAIR = "eur_rur";
    public static final String USD_CNH_CURRENCY_PAIR = "usd_cnh";
    public static final String GBP_USD_CURRENCY_PAIR = "gbp_usd";
    public static final String TRC_BTC_CURRENCY_PAIR = "trc_btc";
    public static final String PPC_BTC_CURRENCY_PAIR = "ppc_btc";                                
    public static final String PPC_USD_CURRENCY_PAIR = "ppc_usd";
    public static final String FTC_BTC_CURRENCY_PAIR = "ftc_btc";
    public static final String XPM_BTC_CURRENCY_PAIR = "xpm_btc";
    
    public static final String [] arrCURRENCIES = {
                                         BTC_USD_CURRENCY_PAIR
                                        ,BTC_EUR_CURRENCY_PAIR
                                        ,BTC_RUR_CURRENCY_PAIR
                                        ,BTC_CNH_CURRENCY_PAIR
                                        ,BTC_GBP_CURRENCY_PAIR
                                        ,LTC_BTC_CURRENCY_PAIR
                                        ,LTC_USD_CURRENCY_PAIR
                                        ,LTC_RUR_CURRENCY_PAIR
                                        ,LTC_EUR_CURRENCY_PAIR
                                        ,LTC_CNH_CURRENCY_PAIR
                                        ,LTC_GBP_CURRENCY_PAIR
                                        ,NMC_BTC_CURRENCY_PAIR
                                        ,NMC_USD_CURRENCY_PAIR
                                        ,NVC_BTC_CURRENCY_PAIR
                                        ,NVC_USD_CURRENCY_PAIR
                                        ,USD_RUR_CURRENCY_PAIR
                                        ,EUR_USD_CURRENCY_PAIR                                
                                        ,EUR_RUR_CURRENCY_PAIR
                                        ,USD_CNH_CURRENCY_PAIR
                                        ,GBP_USD_CURRENCY_PAIR
                                        ,TRC_BTC_CURRENCY_PAIR
                                        ,PPC_BTC_CURRENCY_PAIR                                
                                        ,PPC_USD_CURRENCY_PAIR
                                        ,FTC_BTC_CURRENCY_PAIR
                                        ,XPM_BTC_CURRENCY_PAIR
                                        };
    
    
}

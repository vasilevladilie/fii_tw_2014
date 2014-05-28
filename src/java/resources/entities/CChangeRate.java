/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources.entities;

/**
 *
 * @author vladilie
 */
public class CChangeRate {
    
    protected String strCurrency;
    protected String strHighestBidPrice;
    protected String strLowestAskPrice;
    protected String strAskPrice;
    protected String strTradingVolume;
    protected String strCurrentTradingVolume;
    protected String strLastSellOrder;
    protected String strLastBuyOrder;
    protected String strUpdateTime;
    
    public CChangeRate() {
        strCurrency = new String();
        strHighestBidPrice = new String();
        strLowestAskPrice = new String();
        strAskPrice = new String();
        strTradingVolume = new String();
        strCurrentTradingVolume = new String();
        strLastSellOrder = new String();
        strLastBuyOrder = new String();
        strUpdateTime = new String();
    }    
    public String getCurrency() {
        return strCurrency;
    }
    public void setCurrency(String currency) {
        strCurrency = currency;
    }
    public String getHighestBidPrice() {
        return strHighestBidPrice;
    }
    public void setHighestBidPrice(String highestBidPrice) {
        strHighestBidPrice = highestBidPrice;
    }
    public String getLowestAskPrice(){
        return strLowestAskPrice;
    }
    public void setLowestAskPrice(String lowestAskPrice) {
        strLowestAskPrice = lowestAskPrice;
    }
    public String getAskPrice() {
        return strAskPrice;
    }
    public void setAskPrice(String askPrice) {
        strAskPrice = askPrice;
    }
    public String getTradingVolume() {
        return strTradingVolume;
    }
    public void setTradingVolume(String tradingVolume) {
        strTradingVolume = tradingVolume;
    }
    public String getCurrentTradingVolume() {
        return strCurrentTradingVolume;
    }
    public void setCurrentTradingVolume(String currentTradingVolume) {
        strCurrentTradingVolume = currentTradingVolume;
    }
    public String getLastSellOrder() {
        return strLastSellOrder;
    }
    public void setLastSellOrder(String lastSellOrder) {
        strLastSellOrder = lastSellOrder;
    }
    public String getLastBuyOrder() {
        return strLastBuyOrder;
    }
    public void setLastBuyOrder(String lastBuyOrder) {
        strLastBuyOrder = lastBuyOrder;
    }
    public String getUpdateTime() {
        return strUpdateTime;
    }
    public void setUpdateTime(String updateTime) {
        strUpdateTime = updateTime;
    }
}

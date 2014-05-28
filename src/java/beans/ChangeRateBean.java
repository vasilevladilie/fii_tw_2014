/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import java.io.Serializable;
import resources.entities.CChangeRate;

/**
 *
 * @author vladilie
 */
public class ChangeRateBean implements Serializable {
    
    protected CChangeRate exchangeRate;
    
    public ChangeRateBean() {
        exchangeRate = new CChangeRate();
    }
    public String getCurrency() {
        return exchangeRate.getCurrency();
    }
    public void setCurrency(String currency) {
        exchangeRate.setCurrency(currency);
    }
    public String getHighestBidPrice() {
        return exchangeRate.getHighestBidPrice();
    }
    public void setHighestBidPrice(String highestBidPrice) {
        exchangeRate.setHighestBidPrice(highestBidPrice);
    }
    public String getLowestAskPrice(){
        return exchangeRate.getHighestBidPrice();
    }
    public void setLowestAskPrice(String lowestAskPrice) {
        exchangeRate.setLowestAskPrice(lowestAskPrice);
    }
    public String getAskPrice() {
        return exchangeRate.getLowestAskPrice();
    }
    public void setAskPrice(String askPrice) {
        exchangeRate.setAskPrice(askPrice);
    }
    public String getTradingVolume() {
        return exchangeRate.getTradingVolume();
    }
    public void setTradingVolume(String tradingVolume) {
        exchangeRate.setTradingVolume(tradingVolume);
    }
    public String getCurrentTradingVolume() {
        return exchangeRate.getCurrentTradingVolume();
    }
    public void setCurrentTradingVolume(String currentTradingVolume) {
        exchangeRate.setCurrentTradingVolume(currentTradingVolume);
    }
    public String getLastSellOrder() {
        return exchangeRate.getLastSellOrder();
    }
    public void setLastSellOrder(String lastSellOrder) {
        exchangeRate.setLastSellOrder(lastSellOrder);
    }
    public String getLastBuyOrder() {
        return exchangeRate.getLastBuyOrder();
    }
    public void setLastBuyOrder(String lastBuyOrder) {
        exchangeRate.setLastBuyOrder(lastBuyOrder);
    }
    public String getUpdateTime() {
        return exchangeRate.getUpdateTime();
    }
    public void setUpdateTime(String updateTime) {
        exchangeRate.setUpdateTime(updateTime);
    }
}

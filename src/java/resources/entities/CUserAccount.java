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
public class CUserAccount {
    
    protected String strUsername;
    protected String strPassword;
    protected String strEmail;
    protected String strPhoneNumber;
    protected String strAddress;
    public static final String strUSERNAME ="username";
    public static final String strPASSWORD = "password";
    public static final String strEMAIL ="email";
    public static final String strPHONE_NUMBER ="phone_number";
    public static final String strADDRESS = "address";
    
    public CUserAccount() {
        strUsername     = new String();
        strPassword     = new String();
        strEmail        = new String();
        strPhoneNumber  = new String();
        strAddress      = new String();    
    }
    
    public void setUsername(String username) {
        strUsername = username;
    }
    public String getUsername() {
        return strUsername;
    }
    public void setPassword(String password) {
        strPassword = password;
    }
    public String getPassword() {
        return strPassword;
    }
    public void setEmail(String email) {
        strEmail = email;
    }
    public String getEmail() {
        return strEmail;
    }
    public void setPhoneNumber(String phoneNumber) {
        strPhoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return strPhoneNumber;
    }
    public void setAddress(String address) {
        strAddress = address;
    }
    public String getAddress() {
        return strAddress;
    }
}

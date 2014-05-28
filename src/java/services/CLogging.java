/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author vladilie
 */
public class CLogging {

    protected static CLogging s_InstanceLogging;
    protected static Logger s_DroolLogger;
    protected static FileHandler s_DroolFileHandler;
    protected static SimpleFormatter s_DroolTextFormater;
    protected String m_strInitializationErrors;
    protected String PATH_TO_LOG_FILE = "/home/vladilie/NetBeansProjects/Drool/Drool.log";
    static final String DROOL_GLOBAL_LOGGER ="fii.ilievvlad.Drool";
    static {
        s_DroolFileHandler  =null;
        s_DroolLogger       =null;
        s_DroolTextFormater =null;       
        s_InstanceLogging   =null;
    }
    public static CLogging getInstance() {
        if(null == s_InstanceLogging) {
            s_InstanceLogging = new CLogging();
            s_InstanceLogging.setupLogger();
        }
        return s_InstanceLogging;
    }
    public CLogging() {
        m_strInitializationErrors = null;
    }
    public void setupLogger() {
        if(null == s_DroolLogger) {
            s_DroolLogger = Logger.getLogger(DROOL_GLOBAL_LOGGER);
            s_DroolLogger.setLevel(Level.INFO);
            try {
                s_DroolFileHandler = new FileHandler(PATH_TO_LOG_FILE);
                s_DroolLogger.addHandler(s_DroolFileHandler);
            } catch (IOException | SecurityException ex) {
                m_strInitializationErrors = CLogging.class.getName() + ex.getMessage();
            }
            s_DroolTextFormater = new SimpleFormatter();
            s_DroolFileHandler.setFormatter(s_DroolTextFormater);     
        }
    }
    public void writeWarningLog(String strLog) {
        if(null!=s_DroolLogger) {
            s_DroolLogger.log(Level.WARNING, strLog);
        }
    }
    public void writeSevereLog(String strLog) {
        if(null!=s_DroolLogger) {
            s_DroolLogger.log(Level.SEVERE, strLog);
        }
    }
    public void writeLog(String strLog) {
        if(null!=s_DroolLogger) {
            s_DroolLogger.log(Level.INFO,strLog);
        }
    }
}

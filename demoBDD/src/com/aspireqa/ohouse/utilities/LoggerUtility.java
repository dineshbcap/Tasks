package com.aspireqa.ohouse.utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtility {

	 
    private static LoggerUtility instance = null;
    public static String className = LoggerUtility.class.getName();
    protected final static Logger log = Logger.getLogger(className);
 
    private LoggerUtility() {
        super();
    }
 
    public static LoggerUtility getInstance(@SuppressWarnings("rawtypes") Class classToLog){
        if(instance  == null){
            instance  = new LoggerUtility();
            PropertyConfigurator.configure("log4J.properties");
        }
        
        className = classToLog.getName();
        
        return instance;
    }
 
    public void info(String msg) {
        log.info("[" + className + "] " + msg);
 
    }
    
    public void debug(String msg) {
        log.debug("[" + className + "] " + msg);
 
    }
 
    public void error(String msg, Exception ce) {              
        log.error("[" + className + "] " + msg, ce);      
    }
    
    public void error(String msg) {              
        log.error("[" + className + "] " + msg);      
    }
 
    public void warning(String msg) {
        log.warn("[" + className + "] " + msg);
    }    


}

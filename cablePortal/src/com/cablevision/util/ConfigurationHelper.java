package com.cablevision.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ConfigurationHelper 
{ 
    
    private static ConfigurationHelper helper;
    static Logger log = Logger.getLogger(ConfigurationHelper.class);
    private Properties configurationProperties;
    
    private ConfigurationHelper(){
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        java.io.InputStream is = cl.getResourceAsStream("configuracion.properties");
        if(is != null)
        {
            this.configurationProperties = new Properties();
            try
            {
                configurationProperties.load(is);
            }
            catch(IOException e){
                log.error("Error al cargar las propiedades de configuracion: ", e);
            }finally{
                if(is != null){
                    try{                    
                        is.close();
                    }catch(Exception ex){
                        log.error("Error al cerrar el input stream: ", ex);
                    }
                }
            }
        }        
    }
    
    public static ConfigurationHelper getInstance(){
        if(helper == null)
            helper = new ConfigurationHelper();
            
        return helper;
    }
    
    public String obtenerPropiedad(String propertyName, String defaultValue) {
        String temp;
        
        temp = (String) configurationProperties.get(propertyName);
        
        if(temp == null)
            return defaultValue;
        
        return temp;
    }    
    
    public static String getPropiedad(String propertyName,String defaultValue){
    	return getInstance().obtenerPropiedad(propertyName,defaultValue);
    }
    
    public static String getPropiedad(String propertyName){
    	return getInstance().obtenerPropiedad(propertyName,null);
    }
    
    public static Properties getPropiedades(){
    	return getInstance().configurationProperties;
    }
} 
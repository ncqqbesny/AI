package com.soft.mapp.basecenter.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Random;

public class ToolsUtils {
	private final static Logger log = LoggerFactory.getLogger(ToolsUtils.class);

	/**
	 * 产生多位随机数
	 * @param bit
	 * @return
	 */
	public static String bitRandom(int bit){
		if(bit==4) {
			return Integer.toString(new Random().nextInt(900000) + 100000);
		}
		return Integer.toString(new Random().nextInt(9000) + 1000);
	}
	/**
     * 读取配置文件
     * @param key
     * @return
     * @throws IOException 
     */
    public static String readConfigFile(String key,String fileName ){ 
    	
    	 String token="";
         try {
        	// InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties");
        	File file = new File(fileName);
            Properties pro = new Properties();                      
 			pro.load(new FileInputStream(file));
 			token =pro.getProperty(key);
 		} catch (Exception e) { 			
 			log.error("token读取失败"+e);
 		}
		return token;
    }
    /**
     * 读取配置文件
     * @param key
     * @return
     */
    public static void writeConfigFile(String key,String text,String fileName ){
    	try {
    		 File file = new File(fileName);
             Properties pro = new Properties();
             pro.setProperty(key, text);             
             pro.store(new PrintStream(file), "utf-8");
    		
    	} catch (IOException e) {
    		log.error("token保存失败"+e);
 		}
    	
        
    }
}

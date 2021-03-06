package com.ibm.issac.toolkit.param;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.ibm.issac.toolkit.DevLog;
import com.ibm.issac.toolkit.logging.ColorLog;

/**
 * ����TEXT��ʽ��PROPERTIES����
 * 
 * @author issac
 * 
 */
public class TextualProperties {
	private Properties p;
	
	public Object pobj(String key){
		return p.getProperty(key);
	}
	
	public String[] pstra(String key, String expr){
		String token = p.getProperty(key);
		return token.split(expr);
	}
	
	public Properties parse(String fileName) {
		p = new Properties();
		try {
			FileInputStream pInStream = new FileInputStream(new File(fileName));
			p.load(pInStream);
			DevLog.trace("Printing loaded properties from "+fileName);
			Set kS = p.keySet();
			Iterator it = kS.iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				DevLog.trace(key+"="+p.getProperty(key));
			}
			DevLog.trace("All loaded properties have been printed.");
			return p;
		} catch (FileNotFoundException e) {
			ColorLog.warn("The config file "+fileName+" could not be found.");
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

	public void parseForException(String fileName) throws FileNotFoundException, IOException {
		final Properties p = new Properties();
		FileInputStream pInStream = new FileInputStream(new File(fileName));
		p.load(pInStream);
		p.list(System.out);
	}

	public List pintl(String key, String expr) {
		String token = p.getProperty(key);
		String[] strA = token.split(expr);
		List intL = new ArrayList();
		for(int i=0; i<strA.length; i++){
			intL.add(Integer.valueOf(strA[i]));
		}
		return intL;
	}
}

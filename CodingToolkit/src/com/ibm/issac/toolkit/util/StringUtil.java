package com.ibm.issac.toolkit.util;

import java.io.UnsupportedEncodingException;

import com.ibm.issac.toolkit.logging.ColorLog;
import com.ibm.issac.toolkit.validation.StringValidation;

public class StringUtil {
	/**
	 * ���STRING�а����ɶ����ݣ������棻����Ϊ�١�
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isReadable(String string) {
		if (string == null)
			return false;
		if ("".equals(string.trim()))
			return false;
		/*
		if ("null".equals(string.trim().toLowerCase())) {
			ColorLog.warn("You have provided a string consisted of 4 characters: N-U-L-L. It's been treated as not-readable.");
			return false;
		}*/
		return true;
	}

	public static boolean isEmpty(String str){
		return !StringUtil.isReadable(str);
	}
	
	/**
	 * ��String��ʽ��ʾһ��String����
	 * 
	 * @param stringArray
	 * @return
	 */
	public static String flattenStringArray(String[] stringArray) {
		final StringBuffer sb = new StringBuffer("[");
		for (int i = 0; i < stringArray.length; i++) {
			if (i != 0)
				sb.append(',');
			sb.append(stringArray[i]);
		}
		sb.append(']');
		return sb.toString();
	}

	/**
	 * Returns the supplied string, up to (and excluding) the first newline character. If the supplied string is null, the output string is also null.
	 * 
	 * @param string
	 *            String to parse.
	 * @return String the first line of the input string.
	 */
	public static String getFirstLine(String string) {
		String retVal = null;
		if (string != null) {
			int firstNewline = string.indexOf('\n');
			if (firstNewline != -1) {
				retVal = string.substring(0, firstNewline);
			} else {
				retVal = string;
			}
		}
		return retVal;
	}

	/**
	 * ���شӿ�ͷ����һ���ո���ַ���
	 * 
	 * @param partStr
	 * @param delimeterStr
	 * @return
	 */
	public static String getSubStringEndingAtTheFirstDelimeter(String partStr, String delimeterStr) throws StringIndexOutOfBoundsException {
		return partStr.substring(0, partStr.indexOf(delimeterStr));
	}

	/**
	 * ���شӵ�һ���ո�ʼ����β�Ĳ����ַ���
	 * 
	 * @param partStr
	 * @param delimeterStr
	 * @return
	 */
	public static String getSubStringBeginningFromTheFirstDelimeter(String partStr, String delimeterStr) throws StringIndexOutOfBoundsException {
		return partStr.substring(partStr.indexOf(delimeterStr) + delimeterStr.length(), partStr.length());
	}

	/**
	 * ��������ָ���֮����ַ���
	 * 
	 * @param delimiterA
	 * @param delimiterB
	 * @return
	 */
	public static String getSubStringInbetween(String partStr, String delimiterA, String delimiterB) {
		partStr = StringUtil.getSubStringBeginningFromTheFirstDelimeter(partStr, delimiterA);
		return StringUtil.getSubStringEndingAtTheFirstDelimeter(partStr, delimiterB);
	}

	/**
	 * ���������п��ܰ�������ʱʹ�ø�ѡ�
	 * 
	 * @param propertyName
	 * @param defaultValue
	 *            ������Ľ���ʧ�ܣ�������Ĭ��ֵ
	 * @return
	 */
	public static String readChinese(String str, String defaultValue) {
		try {
			// return new String(str.getBytes("ISO8859_1"), "GBK");
			return new String(str.getBytes("UTF-8"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return defaultValue;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static String generateRamdomString(String prefix) {
		long uniqueNumber = System.currentTimeMillis() % 1000;
		if (!StringValidation.isStringReadable(prefix))
			prefix = "RAMDOM_STRING_";
		return prefix + uniqueNumber;
	}

	/**
	 * ���ַ����ÿո��ж�Ϊ
	 * 
	 * @return
	 */
	public static String[] splitIntoList(String str, String seperator) {
		String splittedStr[] = str.split(seperator+"");
		return splittedStr;
	}
	
	/**
	 * �滻�ַ����еĻس����Ʊ����������š�
	 * @param inStr ��Ҫ�滻���ַ���
	 * @param replacement ��Ҫ�滻Ϊ���ַ�����������Ϊ���ַ���
	 * @return
	 */
	public static String removeControlCharacters(String inStr,String replacement){
		if(!StringUtil.isReadable(inStr)){
			return inStr;
		}
		return inStr.replaceAll("[\\t\\n\\r]", replacement);
	}
}

package com.ibm.issac.toolkit.format;

import java.text.DecimalFormat;

/**
 * ��ʽ���������ͣ������ʽ��С����ȡ�
 * VasF = Vas Format
 * @author issac
 * 
 */
public class NuF {

	/**
	 * ��ʾΪ�ֽ��ʽ
	 *  cu =currency
	 * @param d
	 * @return
	 */
	public static String cu(double d) {
		DecimalFormat df3 = new DecimalFormat("#.###");
		return df3.format(d);
	}
	
	/**
	 * ֻ����һλС��������� dot 1
	 * @param d
	 * @return
	 */
	public static String d1(double d) {
		DecimalFormat df3 = new DecimalFormat("#.#");
		return df3.format(d);
	}
	
	/**
	 * ֻ����һλС��������� dot 4
	 * @param d
	 * @return
	 */
	public static String d4(double d) {
		DecimalFormat df4 = new DecimalFormat("#.####");
		return df4.format(d);
	}
	public static String in(double d){
		DecimalFormat df0 = new DecimalFormat("#");
		return df0.format(d);
	}
	
	/**
	 * ��ʾ�ٷֱ�
	 * @param d
	 * @return
	 */
	public static String pe(double d){
		DecimalFormat df0 = new DecimalFormat("#.##%");
		return df0.format(d);
	}

	
}

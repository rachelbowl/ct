package com.ibm.issac.toolkit.format;

/**
 * string format
 * @author issac
 *
 */
public class StF {

	/**
	 * ͳһ�ַ������ȣ����ַ����������
	 * @see ���ܻ�������
	 * @param sb
	 * @param c ���Ȳ���ʱ�����ڲ�����ַ�
	 * @param len
	 */
	public static StringBuffer unifyLengthBackward(StringBuffer sb, char c, int len1) {
		//��鳤�ȣ���ӿո�
		int len = sb.length();
		for(int i=len; i<=len1; i++){
			sb.append(c);
		}
		return sb;
	}
	
	/**
	 * ͳһ�ַ������ȣ����ַ�������ǰ����
	 * @param sb
	 * @param c ���Ȳ���ʱ�����ڲ�����ַ�
	 */
	public static StringBuffer unifyLengthForward(StringBuffer sb, char c, int len1) {
		//��鳤�ȣ���ӿո�		
		int len = sb.length();
		StringBuffer sb1 = new StringBuffer(c);
		for(int i=len; i<=len1; i++){
			sb1.append(sb);
		}
		return sb1;		
	}
	
	/**
	 * ΪSTRING�����������ţ�������־������ⲻ��ʶ�����ҵĿո�
	 * @param str
	 * @return
	 */
	public static String quoted(String str){
		StringBuffer sb = new StringBuffer(">");
		sb.append(str).append("<");
		return sb.toString();
	}
}

package com.ibm.issac.toolkit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * ���ɸ�ʽǡ��������
 * 
 * @author issac
 * 
 */
public class DatetimeUtility {
	/**
	 * �õ�yyyy/MM/dd HH:mm��ʽ�ĵ�ǰʱ��
	 * 
	 * @return
	 */
	public static String getDatetimeNow() {
		return DatetimeUtility.formatDate("yyyy/MM/dd HH:mm:ss:SSS", new Date());
	}

	public static String getNow(String fmt) {
		return DatetimeUtility.formatDate(fmt, new Date());
	}

	public static String formatDateByRoutine(Date date) {
		return DatetimeUtility.formatDate("yyyy/MM/dd HH:mm:ss", date);
	}

	/**
	 * ��ʽ��ʱ��
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String formatDate(String format, Date date) {
		final SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}

	public static String formatGregorianCalendar(GregorianCalendar c) {
		Date date = c.getTime();
		return DatetimeUtility.formatDate("yyyy/MM/dd HH:mm:ss", date);
	}

	/**
	 * ��������DATE���͵�ʱ���(dt1-dt2)����HOUR��ʾ
	 * 
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static double computeHourGap(Date dt1, Date dt2) {
		final long msGap = dt1.getTime() - dt2.getTime();
		return msGap / (1000.0 * 60.0 * 60.0);
	}

	/**
	 * ��������DATE���͵�ʱ���(dt1-dt2)����DAY��ʾ
	 * ע������׼ȷ��ʱ�������һ������5�㵽�ڶ�������5��ǰ���Ǽ���Ϊ1���ڡ�
	 * ���ϣ������ͱ�ʾһ�죬ʹ�����12Сʱ����ӽ���
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static double computeDayGap(Date dt1, Date dt2) {
		final long msGap = dt1.getTime() - dt2.getTime();
		return msGap / (1000.0 * 60.0 * 60.0 * 24.0);
	}

	/**
	 * �ַ���ת��Ϊ����
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Date toDate(String dateStr, String formatStr) {
		DateFormat dd = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
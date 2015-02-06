package com.ibm.issac.toolkit.nativeRuntime;

import com.ibm.issac.toolkit.DevLog;

/**
 * ִ��NATIVE����ʱ�����˴��󷵻ء�
 * ����NATIVE�����ʹ��ȷִ���ˣ�ִ�н������Ҳ�Ǳ�ʾ����Ľ����ͨ����0��ʾ��ȷ������Ϊ����
 * @author issac
 *
 */
public class ErrorRunningNativeCommandException extends Exception {

	public ErrorRunningNativeCommandException(String errmsg) {
		super(errmsg);
		DevLog.error(errmsg);
	}
}

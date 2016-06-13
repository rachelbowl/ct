package com.ibm.issac.toolkit.app;

import com.ibm.issac.toolkit.DevLog;
import com.ibm.issac.toolkit.format.StF;
import com.ibm.issac.toolkit.param.SysProp;

public class VerifyJavaVersion {
	/**
	 * ���汾����ʱ������RUNTIME�쳣���˳���<br/>
	 * ��֧�ֵڶ�λ�汾��ֻ�ܱȽ� 1.6>1.5����һ��С�汾���ܱȽϡ�<br/>
	 * ע�⣺�������ֻ��������JDK�汾�ͱ���JDK�汾����ʱ��Ҫ�ر�ָ���İ汾���Ż������塣�ٸ�����������JAVA 1.5������࣬���ʹ��JDK 1.4���У�ֱ��JDK�ͻᱨ��IncompatibleJavaVersionError�����ƿ������󣩣���������������������ݡ�
	 * @param versionStr
	 * @param comment
	 */
	public static boolean isMinimalJavaVersionSatisfied(float minVer, String comment){
		final String currentVer = SysProp.pstr("java.version");
		DevLog.trace("VerifyJavaVersion#Java version at "+StF.quoted(currentVer));
		//��ȡǰ��λ����Ϊ���֣����ڱȽϴ�С
		final String subVer = currentVer.substring(0, 3);
		final float verF = Float.valueOf(subVer).floatValue();
		DevLog.trace("VerifyJavaVersion#converted float: "+verF);
		if(verF < minVer){
			DevLog.error(comment);
			DevLog.trace("VerifyJavaVersion#Current java version: "+currentVer+" does not satisfy minimal prerequisite for this program: "+verF+".");
			return false;
		}
		return true;
	}
}

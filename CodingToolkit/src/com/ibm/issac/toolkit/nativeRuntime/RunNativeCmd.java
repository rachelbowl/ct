package com.ibm.issac.toolkit.nativeRuntime;

import java.io.IOException;
import java.util.Map;

import com.ibm.issac.toolkit.DevLog;
import com.ibm.issac.toolkit.param.SysProp;

/**
 * ִ��һ��NATIVE���ִ��ʱ����ṩ�������ж��У��������ύ��OSִ�С�
 * 
 * @author issac
 * 
 */
public class RunNativeCmd {
	private Map outputM;

	/**
	 * ֱ��ִ���ַ�����ʽ������
	 * @param cmdStr
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public int runNativeCmd(String cmdStr) throws IOException, InterruptedException{
		final NativeCmdUnit ncu = new NativeCmdUnit();
		ncu.setCmd_ALL(cmdStr);
		return this.runNativeCmd(ncu);
	}
	
	/**
	 * ִ��NATIVE����
	 * 
	 * @param ncu
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public int runNativeCmd(NativeCmdUnit ncu) throws IOException, InterruptedException {
		// ����OS��������
		final String osName = SysProp.getOSName();
		DevLog.super_trace("[NATIVE CMD] Running native command for OS: " + osName);
		AbstractNativeCommandSupport s = null;
		if (osName.startsWith("Windows")) {
			s = new RunWindowsNativeCommand();
			int retVal = s.process(ncu.getCmd_Windows(), false);
			outputM = s.getOutputM();
			return retVal;
		}
		if (osName.startsWith("Linux")) {
			s = new RunUnixNativeCommand();
			int retVal = s.process(ncu.getCmd_Linux(), false);
			outputM = s.getOutputM();
			return retVal;
		}
		if (osName.startsWith("AIX")) {
			s = new RunUnixNativeCommand();
			int retVal = s.process(ncu.getCmd_AIX(), false);
			outputM = s.getOutputM();
			return retVal;
		}
		if (osName.startsWith("Mac OS X")) {
			s = new RunUnixNativeCommand();
			int retVal = s.process(ncu.getCmd_MacOSX(), false);
			outputM = s.getOutputM();
			return retVal;
		}
		DevLog.warn("[NATIVE CMD] Un-categorized OS " + osName + ", the script will be in a default way.");
		s = new RunUnixNativeCommand();
		int retVal = s.process(ncu.getCmd_Other(), false);
		outputM = s.getOutputM();
		return retVal;
	}

	public Map getOutputM() {
		return outputM;
	}
}

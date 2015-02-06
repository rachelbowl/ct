package com.ibm.issac.toolkit.nativeRuntime;

import java.io.IOException;

import com.ibm.issac.toolkit.DevLog;
import com.ibm.issac.toolkit.param.SysProp;

/**
 * ��WINDOWS���������� <br/>
 * TODO Ŀǰ����ƴ������⣺ÿ��ֻ�����е�һ��������к�������ǲ���ִ�еġ�<br/>
 * ��Ϊ������Ƶ�������AIX�ϣ���������ʱ���á�
 * 
 * @author issac
 *
 */
public class RunWindowsNativeCommand extends AbstractNativeCommandSupport {
	public int process(String sourceStr, boolean filterRequired) throws IOException, InterruptedException {
		String commandLine = sourceStr.replaceAll("/", "\\\\");
		DevLog.trace("[NATIVE CMD] Command line: >" + commandLine + "<");
		String[] commandArray = new String[] { "cmd.exe", "/C", commandLine };
		// ��������
		Runtime rt = Runtime.getRuntime();
		Process proc;
		proc = rt.exec(commandArray);
		// ��ӡ����������������
		StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "E");
		StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "O");
		errorGobbler.start();
		outputGobbler.start();
		int retVal = proc.waitFor();
		DevLog.debug("[NATIVE CMD] Process exitValue: " + retVal);
		// ����ִ�б���
		outputM.put("last report", this.reportRunningInfo(retVal, outputGobbler, errorGobbler));
		outputM.put("error output", errorGobbler.getAllOutput());
		return retVal;
	}
}

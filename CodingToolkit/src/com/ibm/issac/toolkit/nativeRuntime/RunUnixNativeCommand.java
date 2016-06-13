package com.ibm.issac.toolkit.nativeRuntime;

import java.io.IOException;

import com.ibm.issac.toolkit.DevLog;

/**
 * ��AIX������OS���� ע�⣺�����Ͱѽű�������ֱ�ӽ���OS
 * 
 * @author issac
 * 
 */
public class RunUnixNativeCommand extends AbstractNativeCommandSupport {
	public int process(String sourceStr, boolean filterRequired) throws IOException, InterruptedException {
		String commandStr = "";
		// ����ű�Ҫ���滻�ض����ݣ������Ԥ���ĺ����滻
		if (filterRequired) {
			commandStr = this.filterSource(sourceStr);
		} else {
			commandStr = sourceStr;
		}
		DevLog.super_trace("[NATIVE CMD] Command line: >" + commandStr + "<");
		// ִ�д����Ľű�
		String[] commandArray = new String[] { "/bin/sh", "-c", commandStr };
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
		DevLog.super_trace("[NATIVE CMD] Process exitValue: " + retVal);
		outputM.put("last report", this.reportRunningInfo(retVal, outputGobbler, errorGobbler));
		outputM.put("error output", errorGobbler.getAllOutput().toString());
		return retVal;
	}

	private String filterSource(String sourceStr) {
		DevLog.super_trace("[NATIVE CMD] Command fitler will be implemented as instructed.");
		String filteredStr = sourceStr.replaceAll("\\$\\(basename \\$0\\)", "\\$SP_SOURCE_FILE_NAME");
		return filteredStr;
	}
}

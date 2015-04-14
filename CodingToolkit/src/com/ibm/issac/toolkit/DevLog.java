package com.ibm.issac.toolkit;

import java.io.FileWriter;
import java.io.IOException;

import com.ibm.issac.toolkit.param.SysProp;
import com.ibm.issac.toolkit.util.DateUtil;
import com.ibm.issac.toolkit.util.StringUtil;

/**
 * Logger��ΪCONSOLE��ӡ��FILE��ӡ��������CONSOLEֻ������ʾ������Ϣ����FILE����ʾ������Ϣ
 * 
 * @author issac
 * 
 */
public final class DevLog {
	private static String appName;
	private static String logFileName;
	private static FileWriter logWriter;

	public static void setLogFileName(String logFileStr) {
		DevLog.logFileName = logFileStr;
	}

	public static String getLogFileName() {
		return logFileName;
	}

	public static void init(String appName) {
		//-----------------------------------
		//ȷ����־�ļ����ɵ�λ��
		if(!SysProp.b_bool("issac.DevLog.writeFile", true)){
			logWriter = null;//������д��־�ļ����Ӷ������������ģ�����J2EE��System.out�Ѿ�д��������Ӧ�������
			DevLog.debug("DevLog will not write to a file.");
			return;
		}
		DevLog.appName = appName;
		if (DevLog.appName == null) {
			DevLog.appName = "IreApp";
		}
		// ���û���ر�ָ��LOG FILE���ļ�λ�ã�����ݻ�������������־�ļ�
		if (!StringUtil.isReadable(DevLog.logFileName)) {
			final String logPath = SysProp.b_str("issac.logPath", SysProp.b_str("java.io.tmpdir", ""));
			logFileName = logPath+SysProp.getFS() + "devlog_" + appName + "_" + DateUtil.getNow("yyyyMMdd") + ".log";
		}
		try {
			DevLog.debug("log file written to " + logFileName);
			logWriter = new FileWriter(logFileName, true);
		} catch (IOException e) {
			DevLog.warn("Failed initiating DevLog. Logs will not be saved to disk.");
			e.printStackTrace();
		}
	}

	protected void finalize() throws Throwable {
		if (logWriter != null)
			logWriter.close();
		super.finalize();
	}

	public static String buildPrefixedLog(String type, String msg) {
		// ΪLOG����ʱ���
		StringBuffer sb = new StringBuffer(DateUtil.getNow("yyyy-MM-dd HH:mm:ss.SSS "));
		// �����߳�����
		sb.append(Thread.currentThread().getName()).append(' ');
		// ������Ϣ���
		// if (appName != null) {
		// sb.append(appName.toUpperCase());
		// }
		sb.append(type).append(' ');
		// ������־����
		sb.append(msg);
		sb.append('\n');
		return sb.toString();
	}

	public static void debug(String logStr) {
		DevLog.writeLog("D", logStr);
	}

	public static void trace(String logStr) {
		DevLog.writeLog("T", logStr);
	}

	private static void writeLog(String type, String logStr) {
		String prefixedLog = DevLog.buildPrefixedLog(type, logStr);
		// ���ļ��м�¼��־��������־�ȼ�������Σ��������ļ��м�¼
		if (logWriter != null) {
			try {
				logWriter.write(prefixedLog);
				logWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ������־�������;����Ƿ���SYSTEM OUT��ӡ��־����
		String logLevel = SysProp.b_str("issac.logLevel", "-T-D-I-W-E-");
		if (logLevel.indexOf(type) < 0) { // ����ӡlogLevel�����в������������־
			return;
		}
		// ��SYSTEM OUT����ʾ��־�����ڽ�����ʾ��������ʱ�䡢�̣߳�������WAS,TOMCAT��Ӧ������ʾʱ��ʾ�������ʱ�䡣
		// StringBuffer sb = new StringBuffer(type);
		// sb.append(' ').append(logStr).append('\n');
		DevLog.display(prefixedLog);
	}

	/**
	 * ��ʾ���������־
	 * 
	 * @deprecated ����PLOG
	 * @param logStr
	 */
	public static void progress(String logStr) {
		DevLog.writeLog("D", logStr);
	}

	public static void info(String logStr) {
		DevLog.writeLog("I", logStr);
	}

	public static void warn(String logStr) {
		DevLog.writeLog("W", logStr);
	}

	public static void error(String logStr) {
		DevLog.writeLog("E", logStr);
	}

	/**
	 * show string without any header
	 * 
	 * @param logStr
	 */
	public static void display(String logStr) {
		System.out.print(logStr);
	}

	/**
	 * show string without any header
	 * 
	 * @param logStr
	 */
	public static void displayln(String logStr) {
		System.out.println(logStr);
	}
}

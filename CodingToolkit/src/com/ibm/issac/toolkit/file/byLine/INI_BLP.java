package com.ibm.issac.toolkit.file.byLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.issac.toolkit.DevLog;

/**
 * ����mqs.ini
 * 
 * @author issac
 *
 */
public class INI_BLP implements ByLineProcesser {
	private List sectionL = new ArrayList();// mqs.ini���˵õ���QMGR����
	private Map currentDataM = new HashMap(); // ��ǰһ�ڵ�����

	public void process(String text) {
		if (text == null)
			return;
		final String trimmedStr = text.trim();
		// �ж��Ƿ��µ�һ��QMGR���ݿ�ʼ
		if (this.isNewSectionBeginning(trimmedStr)) {
			DevLog.debug("Discovered a new section :" + trimmedStr);
			// �ѵ�ǰ���ݱ�������
			if (currentDataM != null) {
				// ��������
				DevLog.trace("Saving previously-discovered section.");
				if (!currentDataM.isEmpty()) {
					final Map dataM = new HashMap();
					dataM.putAll(currentDataM);
					sectionL.add(dataM);
				}else{
					DevLog.trace("Not adding an empty map.");
				}
			}
			// ��ʼ����������
			currentDataM = new HashMap();
			currentDataM.put("$anaSetting.SECTION_KEYWORD", trimmedStr);
		}
		// �ռ�����
		if (trimmedStr.startsWith("#")) {
			DevLog.trace("ignoring text due to starting with # in >" + trimmedStr + "<");
			return;
		}
		int sepIndex = trimmedStr.indexOf('=');
		if (sepIndex < 0) {
			DevLog.trace("ignoring text due to no \'=\' in >" + trimmedStr + "<");
			return;
		}
		final String keyStr = trimmedStr.substring(0, sepIndex);
		final String valStr = trimmedStr.substring(sepIndex + 1, trimmedStr.length());
		DevLog.trace("Adding KEY: " + keyStr + "\tVAL: " + valStr);
		currentDataM.put(keyStr, valStr);
	}

	/**
	 * �ж�INI�Ƿ��¿�ʼ��һ��
	 * 
	 * @param trimmedStr
	 * @return
	 */
	private boolean isNewSectionBeginning(String trimmedStr) {
		if (trimmedStr.isEmpty())
			return false;
		if (trimmedStr.indexOf('=') >= 0)
			return false;
		if (trimmedStr.startsWith("#"))
			return false;
		return true;
	}

	public Object afterProcessing(Object msg) {
		// ���һ������currentDataM��Ҫ���ȱ����
		if (currentDataM != null) {
			DevLog.trace("Saving lastly discovered data.");
			final Map dataM = new HashMap();
			dataM.putAll(currentDataM);
			sectionL.add(dataM);
			currentDataM = null;
		}
		return sectionL;
	}
}

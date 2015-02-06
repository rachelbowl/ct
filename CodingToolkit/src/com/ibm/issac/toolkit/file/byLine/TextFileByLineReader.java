package com.ibm.issac.toolkit.file.byLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * ���д���TEXT FILE
 * 
 * @author issac
 * 
 */
public class TextFileByLineReader {
	/**
	 * @deprecated ȱ��EXCEPTION�������ļ��Ҳ���������ǲ��㴦���
	 * @param fileName
	 * @param p
	 * @return
	 */
	public String process(String fileName, ByLineProcesser p) {
		try {
			return this.processForException(fileName, p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String processForException(String fileName, ByLineProcesser p) throws IOException {
		final File file = new File(fileName);
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			// repeat until all lines is read
			while ((text = reader.readLine()) != null) {
				p.process(text);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		// show file contents here
		return contents.toString();
	}
}

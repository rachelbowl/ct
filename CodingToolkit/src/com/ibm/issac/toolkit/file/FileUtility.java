package com.ibm.issac.toolkit.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Date;

import com.ibm.issac.toolkit.Cube;
import com.ibm.issac.toolkit.DatetimeUtility;
import com.ibm.issac.toolkit.DevLog;
import com.ibm.issac.toolkit.logging.ColorLog;

/**
 * �����ļ�����
 * 
 * @author issac
 * 
 */
public final class FileUtility {
	/**
	 * <p>
	 * Creates an InputStream from a file, and fills it with the complete file. Thus, available() on the returned InputStream will return the full number of bytes the file contains
	 * </p>
	 * 
	 * @param fname
	 *            The filename
	 * @return The filled InputStream
	 * @exception IOException
	 *                , if the Streams couldn't be created.
	 **/
	public static InputStream fullStream(String fname) throws IOException {
		FileInputStream fis = new FileInputStream(fname);
		DataInputStream dis = new DataInputStream(fis);
		byte[] bytes = new byte[dis.available()];
		dis.readFully(bytes);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
	}

	/**
	 * Based on an old file name, prompt up with a new one.
	 * 
	 * @param oldName
	 */
	public static String promptNewName(String oldName) {
		return oldName + Cube.produceRandomLong();
	}

	/**
	 * normalize separtors and formats for a path presentation. This method works for both file and directory.
	 * 
	 * @param pathStr
	 * @return
	 */
	public static String normalizePathString(String pathStr) {
		String normalizedStr = pathStr;
		normalizedStr = normalizedStr.replaceAll("\\\\", "/");
		// normalizedStr = normalizedStr.replaceAll("\\", "/");
		return normalizedStr;
	}

	/**
	 * ��ָ��Ŀ¼����һ����ʱ�ļ�����Ҫ�ṩ�����ļ������ڵ���������·����
	 * 
	 * @param absolutePath
	 * @return
	 */
	public static File createTempFile(String absolutePath) {
		final File file = new File(absolutePath);
		file.deleteOnExit();// ��ΪSTREAMû�йرյ�ԭ�򣬸÷����п�����Ч
		return file;
	}

	public static void move(File srcF, File dstF, boolean overwriteWhenExisted) {
		DevLog.trace("[FILE MOVE] Trying to move file from >" + srcF.getAbsolutePath() + "< to >" + dstF.getAbsolutePath() + "<");
		if (srcF.getAbsolutePath().equals(dstF.getAbsolutePath())) {
			DevLog.debug("[FILE MOVE] Source file\'s absoluate path is the same as the destination file. Operation aborted.");
			return;
		}
		if (!srcF.isFile()) {
			DevLog.debug("[FILE MOVE] Source is not a file. Operation aborted.");
			return;
		}
		// ---------------------------------
		if (dstF.exists()) {// ���ڴ�ת��Ŀ¼�£��Ѿ�����Ŀ���ļ�
			DevLog.debug("[FILE MOVE] Destination file existed. Overwrite? " + overwriteWhenExisted);
			if (!overwriteWhenExisted) {//�������ǣ����������
				DevLog.trace("[FILE MOVE] Destination file existed. Operation aborted.");
				return;
			}
			//���������ļ�
			srcF.renameTo(dstF);
			return;
		}
		//������Ŀ���ļ�
		srcF.renameTo(dstF);
	}

	/**
	 * ���ļ�s����Ϊ�ļ�t
	 * 
	 * @param srcF
	 * @param dstF
	 * @param overwriteWhenExisted
	 *            ���Ŀ���ļ��Ѿ����ڣ��Ƿ񸲸�
	 */
	public static void copy(File srcF, File dstF, boolean overwriteWhenExisted) {
		DevLog.trace("[FILE COPY] Trying to copy file from >" + srcF.getAbsolutePath() + "< to >" + dstF.getAbsolutePath() + "<");
		if (!srcF.isFile()) {
			DevLog.debug("[FILE COPY] Source is not a file. Operation aborted.");
			return;
		}
		if (dstF.exists()) {
			DevLog.debug("[FILE COPY] The destination file existed when trying to perform file copy. Overwrite? " + overwriteWhenExisted);
			if (!overwriteWhenExisted) {
				DevLog.debug("[FILE COPY] Destination file existed. Operation aborted.");
				return;
			}
		}
		// ��ʼ�����ļ������������ļ��򸲸ǡ�
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(srcF);
			fo = new FileOutputStream(dstF);
			in = fi.getChannel();// �õ���Ӧ���ļ�ͨ��
			out = fo.getChannel();// �õ���Ӧ���ļ�ͨ��
			in.transferTo(0, in.size(), out);// ��������ͨ�������Ҵ�inͨ����ȡ��Ȼ��д��outͨ��
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ѯһ���ı��ļ��ж�����
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static long countRow(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			long count = 0;
			int readChars = 0;
			while ((readChars = is.read(c)) != -1) {
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n')
						++count;
				}
			}
			return count;
		} finally {
			is.close();
		}
	}
}

package com.loquatic.cerescan.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.util.media.Media;

import com.loquatic.cerescan.api.entities.SessionInfo;

public class FileUtil {
	private static Log log = LogFactory.getLog(FileUtil.class);

	/**
	 * ZK helper to create a file obj with the absolute path
	 */
	public static File getAbsolutePath(String filePath, String fileName) {
		return new File(".." + File.separator + "storage" + File.separator
				+ filePath + File.separator + fileName).getAbsoluteFile();
	}

	public static void saveFile(SessionInfo sessionInfo, Media media,
			String storedFileName) {
		String SAVE_PATH = ".." + File.separator + "storage" + File.separator
				+ Long.toString(sessionInfo.getId());

		File dir = new File(SAVE_PATH);
		if (dir.exists() == false) {
			dir.mkdirs();
		}
		try {
			File file = new File(SAVE_PATH + File.separator + storedFileName);
			InputStream inputStream;

			if (media.isBinary()) {
				inputStream = media.getStreamData();
			} else {
				inputStream = new ByteArrayInputStream(media.getStringData()
						.getBytes());
			}
			OutputStream out = new FileOutputStream(file);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0)
				out.write(buf, 0, len);
			out.close();
			inputStream.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}

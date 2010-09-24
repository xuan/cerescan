package com.loquatic.cerescan.api.persistence.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.loquatic.cerescan.api.entities.SessionInfo;

public class FileManager {

	private static Log log = LogFactory.getLog(FileManager.class);

	private final static String PROPS_FILE_NAME = "cerescan.properties";

	private final static String STORAGE_PATH = "storage.path";

	private static Properties applicationProperties;

	private static FileManager me;

	private FileManager() {
		try {
			loadFromPropertiesFromFile();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		log.debug("storage location: "
				+ applicationProperties.getProperty(STORAGE_PATH));
	}

	public static FileManager getInstance() {
		if (me == null) {
			me = new FileManager();
		}
		return me;
	}

	public static String getStoragePath(SessionInfo sessionInfo) {
		log.debug(sessionInfo.getId());
		String path = applicationProperties.getProperty(STORAGE_PATH)
				+ File.separator + "secure" + File.separator
				+ sessionInfo.getId();
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		log.debug(pathFile.getAbsolutePath());
		return path;
	}

	private static void loadFromPropertiesFromFile() throws IOException {
		File propsFile = new File(PROPS_FILE_NAME);
		if (propsFile.exists()) {
			if (applicationProperties == null) {
				applicationProperties = new Properties();
				applicationProperties.put(PROPS_FILE_NAME, ".");
			}
			FileInputStream fis = new FileInputStream(PROPS_FILE_NAME);
			applicationProperties.load(fis);
		} else {
			if (applicationProperties == null) {
				applicationProperties = new Properties();
				applicationProperties.put(STORAGE_PATH, ".");
			}
		}
	}

}

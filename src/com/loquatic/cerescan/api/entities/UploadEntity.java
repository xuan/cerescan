package com.loquatic.cerescan.api.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UploadEntity extends CerescanBaseEntity implements
		IAuditable {

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "storedFileName")
	private String storedFileName;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "description")
	private String description;

	@Column(name = "uploaded_by_user")
	private String uploadedByUser;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUploadedByUser() {
		return uploadedByUser;
	}

	public void setUploadedByUser(String uploadedByUser) {
		this.uploadedByUser = uploadedByUser;
	}

	public String getStoredFileName() {
		return storedFileName;
	}

	public void setStoredFileName(String storedFileName) {
		this.storedFileName = storedFileName;
	}

	public String getFullyQualifedFileName() {
		StringBuilder sb = new StringBuilder();
		sb.append(filePath);
		sb.append(File.separator);
		sb.append(fileName);
		return sb.toString();
	}

	public InputStream getFileAsStream() throws FileNotFoundException {
		FileInputStream fileIn = new FileInputStream(getFullyQualifedFileName());
		return fileIn;
	}

	@Override
	public String toString() {
		return log();
	}

	public String log() {
		return "UploadEntity [description=" + description + ", fileName="
				+ fileName + ", filePath=" + filePath + "contentType"
				+ contentType + ", uploadedByUser=" + uploadedByUser
				+ ", createdDate=" + createdDate + ", id=" + id
				+ ", lastModified=" + lastModified + "]";
	}

}
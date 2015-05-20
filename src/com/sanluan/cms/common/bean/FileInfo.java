package com.sanluan.cms.common.bean;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class FileInfo {
	private String fileName;
	private String path;
	private String description;
	private boolean isDirectory;
	private Date lastModifiedTime;
	private Date lastAccessTime;
	private Date creationTime;
	private long size;

	public FileInfo(String fileName, String path, String description, boolean isDirectory) {
		this.fileName = fileName;
		this.path = path;
		this.description = description;
		this.isDirectory = isDirectory;
	}

	public FileInfo(String fileName, String path, String description, boolean isDirectory, BasicFileAttributes attrs) {
		this.fileName = fileName;
		this.path = path;
		this.description = description;
		this.isDirectory = isDirectory;
		this.lastModifiedTime = new Date(attrs.lastModifiedTime().toMillis());
		this.lastAccessTime = new Date(attrs.lastAccessTime().toMillis());
		this.creationTime = new Date(attrs.creationTime().toMillis());
		this.size = attrs.size();
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the isDirectory
	 */
	public boolean isIsDirectory() {
		return isDirectory;
	}

	/**
	 * @param isDirectory
	 *            the isDirectory to set
	 */
	public void setIsDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	/**
	 * @return the lastModifiedTime
	 */
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * @param lastModifiedTime
	 *            the lastModifiedTime to set
	 */
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * @return the lastAccessTime
	 */
	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	/**
	 * @param lastAccessTime
	 *            the lastAccessTime to set
	 */
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
package com.rawdata.domain;

public class FileUpload 
{
	private int id;
	private byte data[];
	private String fileName;
	
	
	public FileUpload() {
		super();
	}
	public FileUpload(int id, byte[] data, String fileName) {
		super();
		this.id = id;
		this.data = data;
		this.fileName = fileName;
	}
	public FileUpload(byte[] data, String fileName) {
		super();
		this.data = data;
		this.fileName = fileName;
	}
	public FileUpload(int id, String fileName) {
		super();
		this.id = id;
		this.fileName = fileName;
	}
	
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}

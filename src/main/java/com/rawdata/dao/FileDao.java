package com.rawdata.dao;

import java.util.List;

import com.rawdata.domain.FileUpload;

public interface FileDao 
{
	public int insertFile(FileUpload file);
	public List<FileUpload> list();
	public FileUpload find(Integer id);
}

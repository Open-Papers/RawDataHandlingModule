package com.rawdata.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rawdata.dao.FileDao;
import com.rawdata.domain.FileUpload;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

@PropertySource("classpath:database.properties")
@Controller
public class MainController 
{
	@Autowired
	FileDao dao;
	@Value("${folder.path}")
	String folderPath;
	
	@RequestMapping("/")
	public ModelAndView homePage(ModelAndView model)
	{
		List<FileUpload> fileList= dao.list();
		model.addObject("fileListKey",fileList);
		model.setViewName("home");
		return model;
	}
	@RequestMapping(value = "/uploadfile",method =RequestMethod.POST)
	public String fileUpload(@RequestParam("zipfile") CommonsMultipartFile file)
	{
		System.out.println(file.getSize());
		byte data[]=file.getBytes();
		String name=file.getOriginalFilename();
		FileUpload fileObj=new FileUpload(data,name);
		int val=dao.insertFile(fileObj);
		if(val==1)
		{
			return "redirect:/";
		}
		else
			return "unsuccessfull";
	}
	@RequestMapping(value = "/download",method=RequestMethod.GET)
	public ModelAndView download(HttpServletRequest request ,HttpServletResponse response) 
	{
		Integer id= Integer.parseInt(request.getParameter("id"));
		FileUpload myfile=dao.find(id);
		byte[] bytes=myfile.getData();
		String path=folderPath+myfile.getFileName()+".zip";
		try 
		{
			FileOutputStream fos= new FileOutputStream(path);
			fos.write(bytes);
			fos.close();
			
			InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		File temp= new File(path);
		temp.delete();
		return null;
	}
	@RequestMapping(value ="/viewfiles",method=RequestMethod.GET)
	public ModelAndView viewfiles(HttpServletRequest request,ModelAndView model) 
	{
		Integer id= Integer.parseInt(request.getParameter("id"));
		FileUpload myfile=dao.find(id);
		byte[] bytes=myfile.getData();
		String path=folderPath+myfile.getFileName()+".zip";
		try 
		{
			FileOutputStream fos= new FileOutputStream(path);
			fos.write(bytes);
			fos.close();
			
			ZipFile zipFile = new ZipFile(path);
			List<FileHeader> fileNames=zipFile.getFileHeaders();
			model.addObject("fileNamesKey",fileNames);
			model.addObject("idKey",id);
			model.setViewName("unzippedlist");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		File temp= new File(path);
		temp.delete();
		return model;
	}
	
	@RequestMapping(value ="/downloadThis",method=RequestMethod.GET)
	public ModelAndView viewfiles(HttpServletResponse response,HttpServletRequest request) throws ZipException 
	{
		String name=request.getParameter("name");
		Integer id= Integer.parseInt(request.getParameter("id"));
		FileUpload myfile=dao.find(id);
		byte[] bytes=myfile.getData();
		String pathOfZip=folderPath+myfile.getFileName()+".zip";
		String path=folderPath+name;
		try 
		{	
			FileOutputStream fos= new FileOutputStream(pathOfZip);
			fos.write(bytes);
			fos.close();
			
			ZipFile zipFile = new ZipFile(pathOfZip);
			zipFile.extractFile(name,folderPath);
			
			InputStream inputStream = new BufferedInputStream(new FileInputStream(path));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		File temp= new File(path);
		temp.delete();
		File temp1= new File(pathOfZip);
		temp1.delete();
		return null;
	}
}

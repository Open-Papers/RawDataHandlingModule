package com.rawdata.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rawdata.dao.FileDao;
import com.rawdata.domain.FileUpload;

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
		try 
		{
			response.setContentType("application/zip");
		    response.setHeader("Content-Disposition", "attachment; filename="+myfile.getFileName());
		    OutputStream os = response.getOutputStream();
		    os.write(bytes);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value ="/viewfiles",method=RequestMethod.GET)
	public ModelAndView viewfiles(HttpServletRequest request,ModelAndView model) 
	{
		Integer id= Integer.parseInt(request.getParameter("id"));
		FileUpload myfile=dao.find(id);
		byte[] bytes=myfile.getData();
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		ZipInputStream zin= new ZipInputStream(bin);
		List<String> entries = new ArrayList<String>();
		ZipEntry entry;
		try 
		{
			while((entry=zin.getNextEntry())!=null)
			{
				entries.add(entry.getName());
			}
			
		} catch (IOException e) 
		{
			
			e.printStackTrace();
		}
		model.addObject("idKey",id);
		model.addObject("fileNamesKey",entries);
		model.setViewName("unzippedlist");
		return model;
	}
	
	@RequestMapping(value ="/downloadThis",method=RequestMethod.GET)
	public ModelAndView viewfiles(HttpServletResponse response,HttpServletRequest request) throws IOException 
	{
		String name=request.getParameter("name");
		Integer id= Integer.parseInt(request.getParameter("id"));
		FileUpload myfile=dao.find(id);
		byte[] bytes=myfile.getData();
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		ZipInputStream zin= new ZipInputStream(bin);
		ZipEntry entry;
		try 
		{
			while((entry=zin.getNextEntry())!=null)
			{
				 if (entry.getName().equals(name)) 
				 {
					 
					 MimetypesFileTypeMap mtft = new MimetypesFileTypeMap();
	          		 String mimeType = mtft.getContentType(entry.getName());
	          		 System.out.println(entry.getName());
	          		 System.out.println(mimeType);
					 
	          		 OutputStream os = response.getOutputStream();
		             byte[] buffer = new byte[(int)entry.getSize()];
		             int len;
		             while ((len = zin.read(buffer)) >0) 
		             {
		            	 response.setContentType(mimeType);
		            	 response.setHeader("Content-Disposition", "attachment; filename="+entry.getName());
		           	  	 os.write(buffer, 0, len);
		              }
		              
		         }
			}
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}


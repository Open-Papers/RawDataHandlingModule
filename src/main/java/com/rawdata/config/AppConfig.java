package com.rawdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.rawdata.dao.FileDao;
import com.rawdata.dao.FileDaoImpl;

@PropertySource("classpath:database.properties")
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.rawdata")
public class AppConfig 
{
	@Value("${mysql.userName}")
	private String userName;
	
	@Value("${mysql.password}")
	private String password;
	
	@Value("${mysql.url}")
	private String url;
	
	@Value("${mysql.driver}")
	private String driver;
	@Bean
	public DriverManagerDataSource getDataSource() {

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(userName);
		ds.setPassword(password);

		return ds;
	}
	@Bean
	public ViewResolver getViewResolver()
	{
		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	@Bean
	public MultipartResolver multipartResolver()				/*returns Multipart Resolver bean*/
	{
		MultipartResolver multipartResolver= new CommonsMultipartResolver();
		return multipartResolver;
	}
	@Bean
	public JdbcTemplate getJdbcTemplate()
	{
		JdbcTemplate jdbcTemplate= new JdbcTemplate(getDataSource());
		return jdbcTemplate;
	}
	@Bean
	public FileDao getFileDao()
	{
		FileDao dao= new FileDaoImpl();
		return dao;
	}
}

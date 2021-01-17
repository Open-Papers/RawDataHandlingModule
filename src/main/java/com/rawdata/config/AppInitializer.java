package com.rawdata.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		Class arr[]= {AppConfig.class};
		return arr;
	}

	@Override
	protected String[] getServletMappings() {
		
		String arr[]= {"/"};
		return arr;
	}

}

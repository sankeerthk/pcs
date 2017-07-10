package com.pcs.tracker;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {AppConfig.class,SecurityConfiguration.class,SpringSecurityInitializer.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{ "/" };
	}

}

package com.pcs.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	private Environment env;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
	
	public JavaMailSenderImpl javaMailSenderImpl(){
		final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost(env.getProperty("spring.mail.host"));
		return mailSenderImpl;
		
	}

}

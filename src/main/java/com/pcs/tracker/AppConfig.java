package com.pcs.tracker;


import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@PropertySource({"classpath:email.properties","classpath:constant.properties"})
public class AppConfig extends WebMvcConfigurerAdapter{
	
	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	@Autowired
	private Environment env;
	
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain",UTF8)));
		converters.add(stringConverter);
		converters.add(new MappingJackson2HttpMessageConverter());	
		super.configureMessageConverters(converters);
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/pages/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
	
	/**
	 * Configure MessageSource to lookup any validation/error message in
	 * property files.
	 * 
	 * @return MessageSource the message source bundle
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0);
		return messageSource;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public JavaMailSenderImpl javaMailSenderImpl(){
		final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		try {
			mailSenderImpl.setHost(env.getRequiredProperty("spring.mail.host"));
			mailSenderImpl.setPassword("fire4411");
			mailSenderImpl.setUsername("sunnyfire4411@gmail.com");
			mailSenderImpl.setPort(587);
		} catch (IllegalStateException ise) {
			//LOGGER.error("Could not resolve email.properties", ise);
			throw ise;
		}
		final Properties javaMailProps = new Properties();
		javaMailProps.put("mail.smtp.sendpartial", env.getRequiredProperty("mail.smtp.sendpartial"));
		javaMailProps.put("mail.smtp.connectiontimeout", env.getRequiredProperty("mail.smtp.connectiontimeout"));
		javaMailProps.put("mail.smtp.timeout", env.getRequiredProperty("mail.smtp.timeout"));
		javaMailProps.put("mail.transport.protocol", "smtp");
		javaMailProps.put("mail.smtp.auth", "true");
		javaMailProps.put("mail.smtp.starttls.enable", "true");
		javaMailProps.put("mail.debug", "true");
		mailSenderImpl.setJavaMailProperties(javaMailProps);
		return mailSenderImpl;
	}
	
	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		matcher.setUseRegisteredSuffixPatternMatch(true);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}

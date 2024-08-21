package com.jdc.invoice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class InvoiceAppWebConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
	}

//	@Bean
//	ReloadableResourceBundleMessageSource bundleMessageSource() {
//		var source = new ReloadableResourceBundleMessageSource();
//		source.setBasename("classpath:messages");
//		source.setDefaultEncoding("UTF-8");
//		return null;
//	}
//	
//	@Override
//	public Validator getValidator() {
//		var validator = new LocalValidatorFactoryBean();
//		validator.setValidationMessageSource(bundleMessageSource());
//		return validator;
//	}
	
	
}

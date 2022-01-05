//package com.springflexcounchdb.config;
//
//import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//@Configuration
//public class AppConfig {
//	@Bean
//	public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
//		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
//		ppc.setLocation(new ClassPathResource("application.yml"));
//		ppc.setIgnoreUnresolvablePlaceholders(true);
//		return ppc;
//	}
//}
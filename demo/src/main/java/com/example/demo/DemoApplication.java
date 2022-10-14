package com.example.demo;

import com.example.demo.index.Indexer;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.impl.SearchRepositoryImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;

import java.util.ArrayList;

@EnableJpaAuditing
@ComponentScan("com.example.demo")
@SpringBootApplication
@EnableWebMvc
@Configuration
@EnableJpaRepositories(repositoryBaseClass = SearchRepositoryImpl.class)
public class DemoApplication extends SpringBootServletInitializer implements CommandLineRunner, WebMvcConfigurer {
	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}

	@Bean
	public ApplicationRunner buildIndex(Indexer indexer) throws Exception {
		return (ApplicationArguments args) -> {
			indexer.indexPersistedData("com.example.demo.entity.Post");
		};
	}

	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
//		File convFile = new File("file:///Users/yangzhelun/Desktop/development/uploadFile");
//		convFile.mkdir();
//		if (convFile.exists()){
//			System.out.println("file exists");
//		}

	}

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
		"file:/root/uploadFile/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/staticFile/**")
			.addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}

//	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
//		 "file:/Users/yangzhelun/Desktop/development/uploadFile/" };
//
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/staticFile/**")
//			.addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
//	}


}

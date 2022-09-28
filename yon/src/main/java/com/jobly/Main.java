package com.jobly;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;



import com.jobly.util.DatabaseInitializerUtil;
import com.jobly.util.DemoDatabaseInitializerUtil;

// This annotation marks this class as the entry point for the spring boot server
@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Main.class, args);
		ctx.getBean(DatabaseInitializerUtil.class).init();
		ctx.getBean(DemoDatabaseInitializerUtil.class).init();
	}	
}

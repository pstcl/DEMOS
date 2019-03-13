package com.example.mailtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.mailtest.service.ExcelFileToDatabaseJobConfig;
import com.example.mailtest.service.MailService;

@SpringBootApplication
public class MailtestApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(MailtestApplication.class, args);
		
		
	}
	
	

}

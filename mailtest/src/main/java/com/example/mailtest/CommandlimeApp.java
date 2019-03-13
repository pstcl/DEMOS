package com.example.mailtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.mailtest.service.ExcelFileToDatabaseJobConfig;


@Component
public class CommandlimeApp implements CommandLineRunner{

	
	@Autowired
	public ExcelFileToDatabaseJobConfig mailservice;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		mailservice.scheduleFixedRateTask();
	}
	
	

}

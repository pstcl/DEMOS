package com.example.mailtest.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	public JavaMailSender emailSender;

	@Scheduled(fixedRate = 5*60*1000)
	public void scheduleFixedRateTask() {
		
		sendMessageWithAttachment("varinder.maan@gmail.com", "Test Mail", "Test Body"+ System.currentTimeMillis(),"C:\\Users\\pstcl-am-it\\Downloads\\resume_.pdf");

	}

	public void sendSimpleMessage(
			String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage(); 
		message.setTo(to); 
		message.setSubject(subject); 
		message.setText(text);
		emailSender.send(message);

	}

	public void sendMessageWithAttachment(
			String to, String subject, String text, String pathToAttachment) {
		// ...
		
		try {
			Thread.currentThread().sleep((1000*37)+(long) (1000*100*Math.random()));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			FileSystemResource file 
			= new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment("Invoice", file);


		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		emailSender.send(message);
		// ...
	}


}

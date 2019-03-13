package com.example.mailtest.service;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.mailtest.Repositories.MailListRepo;
import com.example.mailtest.entity.MailList;

@Service
public class MailService {

	@Autowired
	public JavaMailSender emailSender;

	@Autowired
	public MailListRepo repository;
	
	public void scheduleFixedRateTask() {
		
		List<MailList> mails= repository.findFalse();
		
		for(MailList mailservice:mails)
		sendMessageWithAttachment(mailservice.getEmailAddress(), "Test Mail", "<b>Test Body</b>"+ System.currentTimeMillis(),"C:\\Users\\pstcl-am-it\\Downloads\\resume_.pdf");

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
			//html formatting true
			helper.setText(text,true);
			
			FileSystemResource file 
			= new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment("Invoice", file);
			emailSender.send(message);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	
		System.out.println("Sent Successfully");
		// ...
	}


}

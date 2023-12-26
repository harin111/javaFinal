package com.tdtu.JavaFn;

import com.tdtu.JavaFn.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ComponentScan(basePackages = "com.tdtu.JavaFn")
public class JavaFnApplication {
//	@Autowired
//	private EmailSenderService emailSenderService;

	public static void main(String[] args) {
	SpringApplication.run(JavaFnApplication.class, args);
	}
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void sendMail()
//	{
//		emailSenderService.sendEmail("truonglac1805@gmail.com",
//				"This is title",
//				"This is body");
//	}
}

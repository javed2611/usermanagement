package com.jk.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	private JavaMailSender javaMailSender;
	private SpringTemplateEngine templateEngine;

	public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}

	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		javaMailSender.send(message);
	}

	public void sendTemplateMail(String to, String subject, Map<String, Object> templateModel)
			throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper mime = new MimeMessageHelper(message, true);
		String html = renderTemplate("email", templateModel);
		mime.setTo(to);
		mime.setSubject(subject);
		mime.setText(html, true);
		javaMailSender.send(message);

	}

	private String renderTemplate(String templateName, Map<String, Object> model) {
		Context context = new Context();
		context.setVariables(model);
		return templateEngine.process(templateName, context);
	}

}

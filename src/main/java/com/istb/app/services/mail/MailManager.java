
package com.istb.app.services.mail;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailManager implements MailServiceI {

    @Value("${spring.mail.username}")
	private String fromMail;
	
	@Value("${app.mail.sender.name}")
	private String senderName;
	
	@Autowired
	private TemplateEngine renderEngine;

	@Autowired
	JavaMailSender mailSender;

	@Override
    public void sendEmailMessage(String message, String email, String subject) {

        sendEmail(email, subject, message);
        
    }

    @Override
    public void sendEmailTemplate(String template, String email, 
        String subject) {
        
		sendEmailTemplate(template, null, email, subject);
        
    }

    @Override
    public void sendEmailTemplate(String template, Map<String, String> data, 
        String email, String subject) {

            Context context;
            String messageBody;
    
            context = new Context();
            if( data != null ) {
                for(Map.Entry<String, String> entry : data.entrySet() ) {
                    context.setVariable(entry.getKey(), entry.getValue()); } }
            
            messageBody = renderEngine.process(
                "notifications/" + template, context);
            
            sendEmail(email, subject, messageBody);
        
    }

    private void sendEmail(String email, String subject, String messageBody) {

        Thread thread = new Thread( () -> { 

            MimeMessageHelper helper = null;
            MimeMessage message;

            try {

                message = mailSender.createMimeMessage();
                helper = new MimeMessageHelper(message, true);

                helper.setTo(email);
                helper.setSubject(subject);
                helper.setFrom(this.fromMail, this.senderName);
                helper.setText(messageBody, true);
                mailSender.send(message);

            } catch (Exception e) { 
                e.printStackTrace(); /** FailedEmailException */ }

        });

        thread.start();

	}
    
}

package com.tracker.services.impl;

import com.tracker.services.MailSenderService;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by nakulkumar on 9/7/16.
 */
@Service("mailSenderService")
public class MailSenderServiceImpl implements MailSenderService {

    private static Logger LOG = Logger.getLogger(MailSenderServiceImpl.class);

    private JavaMailSender mailSender;
    private SimpleMailMessage simpleMailMessage;

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(String dear, String content, String attachmentPath) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(simpleMailMessage.getTo());
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(String.format(simpleMailMessage.getText(), dear, content));
            LOG.info("Mail sending details : "+simpleMailMessage);

            FileSystemResource file = new FileSystemResource(attachmentPath);
            helper.addAttachment(file.getFilename(), file);
            mailSender.send(message);
            LOG.info("Mail sent successfully.");
        } catch (MessagingException e) {
            LOG.error("Exception occurred while sending mail :", e);
        }
    }
}

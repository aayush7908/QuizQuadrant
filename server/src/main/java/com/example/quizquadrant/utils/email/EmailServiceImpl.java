package com.example.quizquadrant.utils.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Value("${DEVELOPER_EMAIL}")
    private String devMail;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void sendSimpleMail(EmailDetails emailDetails) throws Exception {
//        System.out.println("Attempt to email ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(sender);
        helper.setTo(emailDetails.getRecipient());
        helper.setSubject("QuizQuadrant: " + emailDetails.getSubject());
        helper.setText(emailDetails.getMsgBody(), true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendSimpleMailRecovery(Exception e, EmailDetails emailDetails) {
        logger.error("Failed to send mail to " + emailDetails.getRecipient() + " after 4 attempts ...");
    }

    @Override
    public void sendErrorMail(String title, Exception error) {
        StringBuilder msgBody = new StringBuilder();
        msgBody.append("<h1>")
                .append(title)
                .append("</h1>")
                .append("<h3>")
                .append("Below is the stack trace:")
                .append("</h3>");
        for (StackTraceElement stackTraceElement : error.getStackTrace()) {
            msgBody.append(stackTraceElement)
                    .append("\n");
        }
        try {
            sendSimpleMail(
                    EmailDetails
                            .builder()
                            .recipient(devMail)
                            .subject(title)
                            .msgBody(msgBody.toString())
                            .build()
            );
        } catch (Exception ignored) {
        }
    }

    @Override
    public void sendOtpMail(String otp) {
        StringBuilder msgBody = new StringBuilder();
        msgBody.append("<h1>")
                .append("Welcome to QuizQuadrant")
                .append("</h1>")
                .append("<p style=\"font-size:1rem;\">")
                .append("Your One Time Password is: ")
                .append("<b>")
                .append(otp)
                .append("</b>")
                .append("</p>");
        try {
            sendSimpleMail(
                    EmailDetails
                            .builder()
                            .recipient(devMail)
                            .subject("Your OTP")
                            .msgBody(msgBody.toString())
                            .build()
            );
        } catch (Exception ignored) {
        }
    }
}

package com.example.quizquadrant.utils.email;

import com.example.quizquadrant.model.Otp;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    public void sendEmailVerificationOtp(
            String email,
            String otp
    ) {
        StringBuilder msgBody = new StringBuilder();
        msgBody.append("<h1>")
                .append("Welcome to QuizQuadrant")
                .append("</h1>")
                .append("<p style=\"font-size:1rem;\">")
                .append("Your One Time Password for email verification is: ")
                .append("<b>")
                .append(otp)
                .append("</b>")
                .append("</p>")
                .append("<p style=\"font-size:0.7rem;\">")
                .append("<b>")
                .append("NOTE: ")
                .append("</b>")
                .append("This OTP will remain valid only for 10 minutes.")
                .append("</p>");
        try {
            this.sendSimpleMail(
                    EmailDetails
                            .builder()
                            .recipient(email)
                            .subject("Your OTP")
                            .msgBody(msgBody.toString())
                            .build()
            );
        } catch (Exception ignored) {
        }
    }

    @Override
    public void sendResetPasswordOtp(
            String email,
            String otp
    ) {
        StringBuilder msgBody = new StringBuilder();
        msgBody
                .append("<p style=\"font-size:1rem;\">")
                .append("Your request was received to reset password. Your OTP is: ")
                .append("<b>")
                .append(otp)
                .append("</b>")
                .append("</p>")
                .append("<p style=\"font-size:0.7rem;\">")
                .append("<b>")
                .append("NOTE: ")
                .append("</b>")
                .append("This OTP will remain valid only for 10 minutes.")
                .append("</p>");
        try {
            this.sendSimpleMail(
                    EmailDetails
                            .builder()
                            .recipient(email)
                            .subject("Your OTP")
                            .msgBody(msgBody.toString())
                            .build()
            );
        } catch (Exception ignored) {
        }
    }

    @Override
    public void sendConfirmationMail(String email, String title, String msg) {
        StringBuilder msgBody = new StringBuilder();
        msgBody.append("<h3>")
                .append("This is a confirmation mail from QuizQuadrant")
                .append("</h3>")
                .append("<p>")
                .append(msg)
                .append("</p>");
        try {
            this.sendSimpleMail(
                    EmailDetails
                            .builder()
                            .recipient(email)
                            .subject(title)
                            .msgBody(msgBody.toString())
                            .build()
            );
        } catch (Exception ignored) {
        }
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
            msgBody.append("<p>")
                    .append(stackTraceElement)
                    .append("</p>");
        }
        try {
            this.sendSimpleMail(
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

}

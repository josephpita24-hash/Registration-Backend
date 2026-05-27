package com.Register.Registration.AppService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class emailServices {
    
    @Autowired
    public JavaMailSender sender;

    public void sendMail(String to, String token){
        String confirmationUrl = "http://localhost:8080/confirm?token=" + token;
        String message = """
        Hi there!

        You email address {userEmail} was added to you Registration App account on {timespam}

        To verify you email adress and secure you account , click link below.
                
        Here: {link}
        This link is only vaild for 24 hours and it can be only used once.

        Expire link? No worries , to recive a new verification email, vist you Account settings, and select resend 
        verification Email

        If you did not make this request, feel free to ignore this email. if you need additional login help with you Registration
        App account check out Contact us.

        if {userEmail} is not you account, let us know
               
                            @2026 all Right Recieved.
        """;

        String messageString = message
                   .replace("{userEmail}", to)
                   .replace("{link}", confirmationUrl)
                   .replace("{timespam}", LocalDateTime.now().toString());

        SimpleMailMessage mailMessage  = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Verify you Registration Account email address");
        mailMessage.setText(messageString);

        sender.send(mailMessage);
    }
}

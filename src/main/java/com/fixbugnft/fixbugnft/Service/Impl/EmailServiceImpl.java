package com.fixbugnft.fixbugnft.Service.Impl;
import com.fixbugnft.fixbugnft.Model.otpEmail;
import com.fixbugnft.fixbugnft.Repository.EmailRepository;
import com.fixbugnft.fixbugnft.Service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {
//    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private final GetJavaMailSender getJavaMailSender;

    public EmailServiceImpl(GetJavaMailSender getJavaMailSender) {
        this.getJavaMailSender = getJavaMailSender;
    }

    @Override
    public void sendPasswordResetEmail(String recipient, String token) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("psjily@gmail.com");
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject("Reset Password - SOSE");
            String text = "<html>"
                    +
                    "<body style='font-family: Arial, sans-serif;'>"
                    +
                    "<h1>Reset password</h1>"
                    +
                    "<p>Hello,</p>"
                    +
                    "<p>You requested to reset the password of the email registered in our system. Click on the link below to proceed:</p>"
                    +
                    "<p style='background-color: #24a0ed; padding: 10px; color: white; border-radius: 5px; display: inline-block;'><a href='https://front-end/"
                    + recipient
                    + token + "' style='color: white; text-decoration: none;'>Redefine password</p>"
                    +
                    "<p>Thank you for your participation and attention in testing the project. If you have any questions, feel free to contact us.</p>"
                    +
                    "<p>Yours sincerely,</p>"
                    +
                    "<p>SOSE.</p>"
                    +
                    "<span style='background-color: #0e76a8 ; padding: 10px; color: white; border-radius: 5px; display: inline-block;'><a href='https://www.linkedin.com/in/habyhongg/' style='color: white; text-decoration: none;'>LinkedIn</a></span>  "
                    +
                    "<span style='background-color: #171515; padding: 10px; color: white; border-radius: 5px; display: inline-block;'><a href='https://github.com/ductan2003/se' style='color: white; text-decoration: none;'>GitHub</a></span>"

                    +
                    "</body>" +
                    "</html>";
            mimeMessageHelper.setText(text, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //Tạo mã và gửi email.
    public void emailRegister (String email) {
        String otpChars = "123456789";
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        otp.append(otpChars.charAt(random.nextInt(otpChars.length() - 1) + 1));
        for (int i = 1; i < 6; i++) {
            otp.append(otpChars.charAt(random.nextInt(otpChars.length())));
        }
        getJavaMailSender.sendSimpleMessageRegister(String.valueOf(otp), email);
        ZonedDateTime expiredTime = ZonedDateTime.now().plusMinutes(2);
        emailRepository.registerOtpEmail(String.valueOf(otp), email, expiredTime);
    }

    //Xác thực mã OTP.
    public boolean otpConfirmService (int otp, String email) {
        otpEmail emailDatabase = emailRepository.otpConfirmRepository(email);
        if (ZonedDateTime.now().isAfter(emailDatabase.getExpiredTime())) {
            return false;
        }
        return otp == emailDatabase.getOtpCode();
    }

    public void sendBuyer(String email) {
        getJavaMailSender.sendMessageAuctionForBuyer(email);
    }

    public void sendSeller(String email) {
        getJavaMailSender.sendMessageAuctionForSeller(email);
    }

    public void sendSellerNo(String email) {
        getJavaMailSender.sendMessageAuctionForSellerNo(email);
    }
}
package com.fixbugnft.fixbugnft.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class GetJavaMailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    //Gửi email otp.
    public void sendSimpleMessageRegister(String otp, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("testcode804@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("[Hệ thống xác thực OTP] Mã OTP của bạn");
        mailMessage.setText("Xin chào,.\n"
                + "Bạn đã yêu cầu nhận mã OTP để xác thực tài khoản. Dưới đây là mã OTP của bạn:\n"
                + "Mã OTP:" + otp + ".\n"
                + "Mã OTP này sẽ hết hiệu lực sau 2 phút.\n"
                + "Nếu bạn không thực hiện yêu cầu, vui lòng bỏ qua email này.\n"
                + "Trân trọng.");
        javaMailSender.send(mailMessage);
    }

    public void sendMessageAuctionForSeller(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("testcode804@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("[NFT] Chúc mừng bạn đã bán đấu giá thành công.");
        mailMessage.setText("Xin chào,.\n"
                + "Bạn đã bán đấu giá thành công tại tranh NFT. Dưới đây là thông tin đấu giá của bạn:\n"
                + "..." + ".\n"
                + "...\n"
                + "...\n"
                + "Trân trọng.");
        javaMailSender.send(mailMessage);
    }

    public void sendMessageAuctionForBuyer(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("testcode804@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("[NFT] Chúc mừng bạn đã đấu giá thành công.");
        mailMessage.setText("Xin chào,.\n"
                + "Bạn đã đấu giá thành công tại tranh NFT. Dưới đây là thông tin NFT của bạn:\n"
                + "..." + ".\n"
                + "...\n"
                + "...\n"
                + "Trân trọng.");
        javaMailSender.send(mailMessage);
    }

    public void sendMessageAuctionForSellerNo(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("testcode804@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setSubject("[NFT] Tranh của bạn đấu giá thất bại.");
        mailMessage.setText("Xin chào,.\n"
                + "Không có ai tham gia đấu giá tranh của bạn.\n"
                + "..." + ".\n"
                + "...\n"
                + "...\n"
                + "Trân trọng.");
        javaMailSender.send(mailMessage);
    }
}

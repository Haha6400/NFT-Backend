package com.fixbugnft.fixbugnft.Service;
import com.fixbugnft.fixbugnft.Model.otpEmail;

import javax.mail.MessagingException;
import java.time.ZonedDateTime;
import java.util.Random;

public interface EmailService {
    void sendPasswordResetEmail(String recipient, String token) throws MessagingException;

    public void emailRegister (String email);

    //Xác thực mã OTP.
    public boolean otpConfirmService (int otp, String email) ;

    public void sendBuyer(String email);

    public void sendSeller(String email) ;

    public void sendSellerNo(String email) ;

}

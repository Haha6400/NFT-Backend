package com.nftapp.nftapp.Service;
import javax.mail.MessagingException;
public interface EmailService {
    void sendPasswordResetEmail(String recipient, String token) throws MessagingException;
}

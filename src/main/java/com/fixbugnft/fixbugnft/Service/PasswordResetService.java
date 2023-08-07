//package com.fixbugnft.fixbugnft.Service;
//
//import com.nftapp.nftapp.Model.PasswordResetToken;
//import com.nftapp.nftapp.Model.User;
//import jakarta.mail.MessagingException;
//
//public interface PasswordResetService {
//    void createPasswordResetTokenForUser(User user, String token);
//    PasswordResetToken getPasswordResetToken(String token);
//    void sendPasswordResetEmail(String username) throws MessagingException;
//    void changePassword(String token, String newPassword, String usernameRequest) throws MessagingException;
//}

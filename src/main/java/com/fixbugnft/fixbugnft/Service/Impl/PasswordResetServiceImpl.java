//package com.fixbugnft.fixbugnft.Service.Impl;
//
//
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
////public class PasswordResetServiceImpl implements PasswordResetService {
//    @Autowired
//    private UserRepo userRepository;
//
//    @Autowired
//    private PasswordResetTokenRepo passwordResetTokenRespository;
//
//    @Autowired
//    private EmailServiceImpl emailService;
//
//    @Override
//    public void createPasswordResetTokenForUser(User user, String token) {
//        PasswordResetToken resetToken = new PasswordResetToken();
////        resetToken.setUser(user);
//        resetToken.setToken(token);
//        passwordResetTokenRespository.save(resetToken);
//    }
//
//    @Override
//    public PasswordResetToken getPasswordResetToken(String token) {
//        return passwordResetTokenRespository.findByToken(token);
//    }
//
//    @Override
//    public void sendPasswordResetEmail(String username) throws MessagingException {
//        User user = userRepository.findByUserName(username);
//        if (user == null || user.getUsername() == null) {
//            return;
//        }
//        String token = UUID.randomUUID().toString();
//        createPasswordResetTokenForUser(user, token);
//        emailService.sendPasswordResetEmail(username, token);
//    }
//
//    @Override
//    public void changePassword(String token, String newPassword, String usernameRequest) throws MessagingException {
//        PasswordResetToken resetToken = passwordResetTokenRespository.findByToken(token);
//        if (resetToken == null) {
//            throw new MessagingException("Invalid or expired token!");
//        }
//        User user = userRepository.findById(Long.parseLong(resetToken.getUserId()));
//        if (user == null || !user.getUsername().equals(usernameRequest)) {
//            throw new MessagingException("User not found or invalid username!");
//        }
//        user.setPassword(newPassword);
//        userRepository.save(user);
//        passwordResetTokenRespository.delete(resetToken);
//    }
//}

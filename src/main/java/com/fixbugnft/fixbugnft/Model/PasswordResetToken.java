//package com.fixbugnft.fixbugnft.Model;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.Calendar;
//import java.util.Date;
//@Entity
//@Getter
//@Setter
//public class PasswordResetToken {
//    private static final int EXPIRATION = 60 * 30;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String token;
//
//    private String userId;
//
//    private Date expiryDate;
//
//    private Date calculateExpiryDate(final int expriryTimeInMinutes) {
//        final Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(new Date().getTime());
//        calendar.add(Calendar.MINUTE, expriryTimeInMinutes);
//        return new Date(calendar.getTime().getTime());
//    }
//
//    public PasswordResetToken() {
//        this.expiryDate = calculateExpiryDate(EXPIRATION);
//    }
//}
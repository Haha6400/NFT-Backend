package com.fixbugnft.fixbugnft.Repository;

//import com.nftapp.nftapp.Model.otpEmail;
import com.fixbugnft.fixbugnft.Model.otpEmail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;

public interface EmailRepository extends CrudRepository<otpEmail, Integer> {

    @Transactional
    @Modifying
    @Query(value =
            "INSERT INTO EMAILS(otp_code, expired, email) VALUES (:otp, :expired, :email)",
            nativeQuery = true)
    void registerOtpEmail (@Param("otp") String otp,
                           @Param("email") String email,
                           @Param("expired") ZonedDateTime expiredTime);

    @Query(value =
            "SELECT *FROM EMAILS WHERE email = :email",
            nativeQuery = true)
    otpEmail otpConfirmRepository (@Param("email") String email);

}

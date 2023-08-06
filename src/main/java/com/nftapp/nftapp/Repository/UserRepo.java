package com.nftapp.nftapp.Repository;
import com.nftapp.nftapp.DTO.UserDto;
import com.nftapp.nftapp.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface UserRepo extends JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query(value =
            "INSERT INTO USERS(id, username, password) VALUES (:id, :username, :password)",
            nativeQuery = true)
    int registerNewUser (@Param("id") Long id,
                         @Param("username") String username,
                         @Param("password") String password
    );
    @Transactional
    @Modifying
    @Query(value =
            "UPDATE USERS SET bio = :bio WHERE id = :id",
            nativeQuery = true)
    int updateBio(@Param("id") Long id,
                  @Param("bio") String bio);
    @Query("SELECT c FROM User c WHERE c.username = ?1")
    User findByUserName(String username);

    User findByUsername(String username);

    User findByAddress(String address);

    User findById(long userId);
}
package com.nftapp.nftapp.Service;

import com.nftapp.nftapp.DTO.UserDto;
import com.nftapp.nftapp.Model.Item;
import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    List<UserDto> getAllUsers();

    UserDto save(UserDto userDto);

    boolean checkUserName(String username);

    User findByAddress(String address);

    UserDto updateUser(User user, String username, String email, BigDecimal balance, String password, Set<Item> favoriteItems, String profileLink, String address, String nonce);

    UserDto changePassword(User user, String newPassword);

    UserDto updateProfileImage(User user, String image);

//    public void findOneByAddress(String address, String signature, HttpServletRequest req, HttpServletResponse res) throws IOException, SignatureException;
}

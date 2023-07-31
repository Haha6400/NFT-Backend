package com.nftapp.nftapp.Service;

import com.nftapp.nftapp.DTO.UserDto;
import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    List<UserDto> getAllUsers();

    UserDto save(UserDto userDto);

    boolean checkUserName(String username);

    User findByAddress(String address);

//    UserDto updateUser(User user, String username, String firstName, String lastName, String password);

    UserDto changePassword(User user, String newPassword);

    UserDto updateProfileImage(User user, String image);

}

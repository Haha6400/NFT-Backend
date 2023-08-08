package com.fixbugnft.fixbugnft.Service;

import com.fixbugnft.fixbugnft.DTO.UserDto;
import com.fixbugnft.fixbugnft.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigDecimal;
import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    List<User> getAllUsers();

    UserDto save(UserDto userDto);

    boolean checkUserName(String username);

    User findByAddress(String address);

    User findById(Long id);

    UserDto updateUser(User user, String username, String email, BigDecimal balance, String password, String profileLink, String nonce);

    UserDto changePassword(User user, String newPassword);

    UserDto updateProfileImage(User user, String image);

    User deductBalance(User user, Double price);

    User addBalance(User user, Double price);

//    public void findOneByAddress(String address, String signature, HttpServletRequest req, HttpServletResponse res) throws IOException, SignatureException;
}

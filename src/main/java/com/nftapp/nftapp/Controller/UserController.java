package com.nftapp.nftapp.Controller;

import cn.hutool.crypto.digest.BCrypt;
import com.nftapp.nftapp.DTO.UserDto;
import com.nftapp.nftapp.Model.Response;
import com.nftapp.nftapp.Model.SignRequest;
import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Repository.UserRepo;
import com.nftapp.nftapp.Service.FileService;
import com.nftapp.nftapp.Service.UserService;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import ethereumjs.util.*;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.slf4j.Logger;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/openeye/user")
@Validated
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;
    @Value("${project.image}")
    private String path;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/all")
    public ResponseEntity<?> getUserList() {
        List<UserDto> users = userService.getAllUsers();
        Map<Object, Object> map = Map.of("total", users.size(), "userList", users);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{username}")
    public User getByUserName(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }

    //User user, String username, String email, BigDecimal balance, String password, Set<Item> favoriteItems, String profileLink, String address, String nonce
    @PutMapping("/update/{username}")
    public ResponseEntity<Response> updateUser(@RequestPart String email,
                                               @RequestPart String profileLink,
                                               @RequestPart String address,
                                               @PathVariable("username") String username){
        User user = userService.getUserByUsername(username);
        if(user == null) return ResponseEntity.ok().body(new Response(false, "Username not found!"));
        String password = user.getPassword();
        UserDto updateUser = userService.updateUser(user, username, email, user.getBalance(), password, user.getFavoriteItems(), profileLink, address, user.getPrivateKey());
        LOGGER.info("User {} has been updated", username);
        return ResponseEntity.ok().body(new Response(true, "Update user successfully!"));
    }
    @PutMapping("/changePassword/{username}")
    public ResponseEntity<Response> changePassword(@RequestPart String password,
                                                   @RequestPart String newPassword,
                                                   @RequestPart String confirmPassword,
                                                   @PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        if(user == null) return ResponseEntity.ok().body(new Response(false, "Username not found!"));
        String userPassword = user.getPassword();
        if(!userPassword.equals(password) || !newPassword.equals(confirmPassword)) return ResponseEntity.ok().body(new Response(false, "Invalid password!"));
        UserDto updateUser = userService.changePassword(user, newPassword);
        LOGGER.info("User {} has been updated", username);
        return ResponseEntity.ok().body(new Response(true, "Change password successfully!"));
    }

    @PutMapping("/updateProfileImage/{username}")
    public ResponseEntity<Response> updateProfileImage(@RequestPart MultipartFile image,
                                                       @PathVariable("username") String username) throws Exception {
        User user = userService.getUserByUsername(username);
        if(user == null) return ResponseEntity.ok().body(new Response(false, "Username not found!"));
        String fileName = fileService.updateFile(path, image);
        UserDto updateUser = userService.updateProfileImage(user, fileName);
        LOGGER.info("Profile image user {} has been updated", username);
        return ResponseEntity.ok().body(new Response(true, "Change profile image successfully!"));
    }
}

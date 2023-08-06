package com.nftapp.nftapp.Controller;

import cn.hutool.system.UserInfo;
//import com.nftapp.nftapp.Jwt.JwtUtils;
//import com.nftapp.nftapp.Jwt.UserInfoResponse;
import com.nftapp.nftapp.DTO.UserDto;
import com.nftapp.nftapp.Jwt.AuthenticationResponse;
import com.nftapp.nftapp.Jwt.JwtService;
//import com.nftapp.nftapp.Jwt.JwtUtils;
//import com.nftapp.nftapp.Jwt.UserInfoResponse;
import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Repository.RoleRepo;
import com.nftapp.nftapp.Repository.UserRepo;
import com.nftapp.nftapp.Service.Impl.PasswordResetServiceImpl;
import com.nftapp.nftapp.Service.Impl.UserServiceImpl;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/openeye/auth")
public class AuthController {
    @Autowired
    @EqualsAndHashCode.Exclude
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordResetServiceImpl passwordResetService;

//    @Autowired
//    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired
//    JwtUtils jwtUtils;

    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestPart String username,
                                              @RequestPart String password){
        User user = userRepo.findByUserName(username);
        if (user == null) {
            return new ResponseEntity<>("Username not found!", HttpStatus.BAD_REQUEST);
        } else {
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new ResponseEntity<>("Wrong password!", HttpStatus.BAD_REQUEST);
            }
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName().toString())));

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = jwtService.generateToken(user, authorities);
        var refreshToken = jwtService.generateRefreshToken(user, authorities);
        return new ResponseEntity<>(AuthenticationResponse.builder().token(token).refreshToken(refreshToken).build(),
                HttpStatus.OK);
    }


    /*
    TODO: Xác thực address ví khi đăng kí
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestPart String username,
                                          @RequestPart String password,
                                          @RequestPart String confirmPassword,
                                          @RequestPart String address) {
        UserDto userDto = new UserDto(username, password, confirmPassword, address);//         add check for username exists in a DB
        if (userRepo.findByUsername(username) != null) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
            userService.save(userDto);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestParam("username") String username) throws MessagingException {
        passwordResetService.sendPasswordResetEmail(username);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Password change request sent successfully.");
        return ResponseEntity.ok(responseBody);
    }

    /*
    Yêu cầu đặt lại mật khẩu dựa trên mã token thông báo được gửi qua email (username)
     */
    @PostMapping("/reset/{token}")
    public ResponseEntity<Map<String, String>> resetPassword(@PathVariable String token, @RequestPart String username, @RequestPart String password) throws MessagingException {
        try {
            passwordResetService.changePassword(token, password, username);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password changed successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (MessagingException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}

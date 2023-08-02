package com.nftapp.nftapp.Controller;

import cn.hutool.crypto.digest.BCrypt;
import com.nftapp.nftapp.DTO.UserDto;
import com.nftapp.nftapp.Model.Response;
import com.nftapp.nftapp.Model.SignRequest;
import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Repository.UserRepo;
import com.nftapp.nftapp.Service.FileService;
import com.nftapp.nftapp.Service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import ethereumjs.util.*;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.slf4j.Logger;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    UserService userService;

    UserRepo userRepo;

    @PostMapping("/login")
    public String login(@RequestParam String singature, @RequestParam String address){
        Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/eeb16b22ff7246a1b0e34763b48310c7"));
        try{
            Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
            if(web3ClientVersion.hasError()){
                return "error";
            } else return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

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
        UserDto updateUser = userService.updateUser(user, username, email, user.getBalance(), password, user.getFavoriteItems(), profileLink, address, user.getNonce());
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



//    @PostMapping("/register")
//    public ResponseEntity registerUser(@RequestParam("id") Long id,
//                                       @RequestParam("username") String username,
//                                       @RequestParam("password") String password,
//                                       @RequestParam("cfpassword") String cfpassword) {
//        if (username.isEmpty()) {
//            return new ResponseEntity<>("Vui lòng điền tên người dùng.", HttpStatus.BAD_GATEWAY);
//        }
//        if (password.isEmpty()) {
//            return new ResponseEntity<>("Vui lòng nhập mật khẩu.", HttpStatus.BAD_GATEWAY);
//        }
//        if (cfpassword.isEmpty()) {
//            return new ResponseEntity<>("Vui lòng xác nhận mật khẩu.", HttpStatus.BAD_GATEWAY);
//        }
//        if (!password.equals(cfpassword)) {
//            return new ResponseEntity<>("Mật khẩu xác nhận chưa đúng.", HttpStatus.BAD_GATEWAY);
//        }
//        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//        int user = userService.userRepository(id, username, hashedPassword);
//        if (user != 1) {
//            return new ResponseEntity<>("Đăng ký thất bại", HttpStatus.BAD_GATEWAY);
//        } else {
//            return new ResponseEntity<>("Đăng ký thành công.", HttpStatus.OK);
//        }
//    }
//
//
//    @PostMapping("/bio")
//    public ResponseEntity changeBio (@RequestParam("id") Long id,
//                                     @RequestParam("bio") String bio) {
//        userService.bioUser(id, bio);
//        return new ResponseEntity<>("Đổi tiểu sử thành công.", HttpStatus.OK);
//    }
}

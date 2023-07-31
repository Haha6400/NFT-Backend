package com.nftapp.nftapp.Controller;

import cn.hutool.crypto.digest.BCrypt;
import com.nftapp.nftapp.Repository.UserRepo;
import com.nftapp.nftapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    UserService userService;

    UserRepo userRepo;

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

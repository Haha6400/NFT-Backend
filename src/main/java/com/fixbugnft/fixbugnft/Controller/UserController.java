package com.fixbugnft.fixbugnft.Controller;

import com.fixbugnft.fixbugnft.DTO.UserDto;
import com.fixbugnft.fixbugnft.Model.Response;
import com.fixbugnft.fixbugnft.Model.User;
import com.fixbugnft.fixbugnft.Repository.UserRepo;
import com.fixbugnft.fixbugnft.Service.FileService;
import com.fixbugnft.fixbugnft.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    @Autowired
    UserRepo userRepository;
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
    public ResponseEntity<?> updateUser(@RequestPart String email,
                                               @RequestPart MultipartFile image,
                                               @PathVariable("username") String username) throws Exception {
        UserDto userDto = new UserDto();
        User user = userService.getUserByUsername(username);
        String profileLink = fileService.updateFile(path, image);
        if(user == null) return ResponseEntity.ok().body(new Response(false, "Username not found!"));
        String password = user.getPassword();
        UserDto updateUser = userService.updateUser(user, username, email, user.getBalance(), password, profileLink, user.getPrivateKey());
        LOGGER.info("User {} has been updated", username);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
//        return ResponseEntity.ok().body(new Response(true, "Update user successfully!"));
    }

//
//    public ResponseEntity<PostDto> updatePost(@RequestPart String title,
//                                              @RequestPart String body,
//                                              @RequestPart MultipartFile image,
//                                              @RequestPart String description,
//                                              @PathVariable Long postId) throws Exception {
//        PostDto postDto = new PostDto(postId, title, body, description);
//        String fileName = fileService.updateFile(path, image);
//        postDto.setImage(fileName);
//        PostDto updatePost = postService.updatePost(postDto, postId);
//        return new ResponseEntity<>(updatePost, HttpStatus.OK);
//    }
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

    @PutMapping("/balance/deduct/{username}")
    public ResponseEntity<Response> deductBalance(@PathVariable("username") String username,
                                                  @RequestPart Double price){
        User user = userService.getUserByUsername(username);
        if(user == null) return ResponseEntity.ok().body(new Response(false, "Username not found!"));
        User updateUser = userService.deductBalance(user, price);
        return ResponseEntity.ok().body(new Response(true, "Change balance successfully!"));
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

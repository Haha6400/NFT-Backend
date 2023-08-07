package com.fixbugnft.fixbugnft.Service.Impl;


import com.fixbugnft.fixbugnft.DTO.UserDto;
import com.fixbugnft.fixbugnft.Model.User;
import com.fixbugnft.fixbugnft.Repository.UserRepo;
import com.fixbugnft.fixbugnft.Service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;
    //    @Autowired
//    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    private UserDto userDto;

    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public ModelMapper modelMapper;


    public void register(User user) {
        users.put(user.getAddress(), user);
    }

    public User findByBlockChainAddr(String address) {
        return users.get(address);
    }

    @Override
    public User findByAddress(String address){
        return userRepository.findByAddress(address);
    }


    @Override
    public UserDto updateUser(User user, String username, String email, BigDecimal balance, String password, String profileLink,  String private_key) {
        user.setUsername(username);
        user.setEmail(email);
        user.setBalance(balance);
        user.setProfileLink(profileLink);
//        user.setAddress(address);
        user.setPrivateKey(private_key);
        user.setPassword(password);
        User updateUser = userRepository.save(user);
        return this.modelMapper.map(updateUser, UserDto.class);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = (List<User>) userRepository.findAll();
        return userList.stream().map((post) ->
                        this.modelMapper.map(post, UserDto.class)).
                collect(Collectors.toList());
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = new User();
        user.setBalance(userDto.getBalance());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAddress(userDto.getAddress());
        user.setProfileLink(userDto.getProfileLink());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        return userDto;
    }

    @Override
    public boolean checkUserName(String username) {
        return userRepository.findByUserName(username) != null;
    }


    @Override
    public UserDto changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        User updateUser = userRepository.save(user);
        return this.modelMapper.map(updateUser, UserDto.class);
    }

    @Override
    public User deductBalance(User user, Double price){
        if(user.getBalance().compareTo(BigDecimal.valueOf(price)) <= 0) {
            return user;
        }
        BigDecimal newBalance = BigDecimal.valueOf(price).subtract(user.getBalance());
        user.setBalance(newBalance);
        User updateUser = userRepository.save(user);
        return updateUser;
    }

    @Override
    public User addBalance(User user, Double price){
        BigDecimal newBalance = BigDecimal.valueOf(price).add(user.getBalance());
        user.setBalance(newBalance);
        User updateUser = userRepository.save(user);
        return updateUser;
    }

    @Override
    public UserDto updateProfileImage(User user, String image) {
        user.setProfileLink(image);
        User updateUser = userRepository.save(user);
        return this.modelMapper.map(updateUser, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUserName(username);
    }

}

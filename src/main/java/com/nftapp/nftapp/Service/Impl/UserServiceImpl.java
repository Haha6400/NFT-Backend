package com.nftapp.nftapp.Service.Impl;

import com.nftapp.nftapp.DTO.ItemDto;
import com.nftapp.nftapp.DTO.UserDto;
import com.nftapp.nftapp.Model.Item;
import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Repository.UserRepo;
import com.nftapp.nftapp.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import io.jsonwebtoken.SignatureAlgorithm;

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
    public UserDto updateUser(User user, String username, String email, BigDecimal balance, String password, Set<Item> favoriteItems, String profileLink, String address, String private_key) {
        user.setUsername(username);
        user.setEmail(email);
        user.setBalance(balance);
        user.setFavoriteItems(favoriteItems);
        user.setProfileLink(profileLink);
        user.setAddress(address);
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
        user.setFavoriteItems(userDto.getFavoriteItems());
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

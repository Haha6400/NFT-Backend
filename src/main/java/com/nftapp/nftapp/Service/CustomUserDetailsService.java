package com.nftapp.nftapp.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    public UserDetails loadUserByUsername(String usernameOrEmail);
}

package com.fixbugnft.fixbugnft.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    public UserDetails loadUserByUsername(String usernameOrEmail);
}

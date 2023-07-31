package com.nftapp.nftapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nftapp.nftapp.Model.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private Long id;

    private String username;
    private String email;
    private BigDecimal balance = BigDecimal.ZERO;

    private String password;

    private boolean passwordReset;
    private Set<Item> favoriteItems = new HashSet<>();

    private String profileLink;

    private String address;

    public void syncChain(String blockChainAddr) {
        this.setAddress(blockChainAddr);
    }

    public void encodePassword(){
        this.password = passwordEncoder.encode(this.password);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

}

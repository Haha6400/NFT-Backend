package com.nftapp.nftapp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.*;
import java.util.Collection;

@Entity
@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "favoriteItems")
public class User implements UserDetails {
    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @Min(0)
    private BigDecimal balance = BigDecimal.ZERO;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Length(min = 8, max = 256)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean passwordReset;

//    private Currency currency;

    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Item> favoriteItems = new HashSet<>();

    private String profileLink;

    private String address;

    private String nonce = UUID.randomUUID().toString();

    public void syncChain(String blockChainAddr) {
        this.setAddress(blockChainAddr);
    }

    public void encodePassword(){
        this.password = passwordEncoder.encode(this.password);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonValue(value = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.nftapp.nftapp.Auth;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter @Setter
public class Web3Authentication extends AbstractAuthenticationToken {
    private String address;
    private String signature;

    public Web3Authentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public Web3Authentication(String address, String signature) {
        super(null);
        this.address = address;
        this.signature = signature;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return signature;
    }

    @Override
    public Object getPrincipal() {
        return address;
    }
}

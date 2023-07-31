package com.nftapp.nftapp.Auth;

import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Repository.UserRepo;
import org.springframework.security.authentication.BadCredentialsException;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import com.nftapp.nftapp.Service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SignatureException;

@Component
public class Web3AuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public Web3AuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    private boolean valid(String signature, String address, String nonce) throws SignatureException {
        String r = signature.substring(0, 66);
        String s = "0x" + signature.substring(66, 130);
        String v = "0x" + signature.substring(130, 132);

        Sign.SignatureData data = new Sign.SignatureData(
                Numeric.hexStringToByteArray(v),
                Numeric.hexStringToByteArray(r),
                Numeric.hexStringToByteArray(s)
        );

        BigInteger key = Sign.signedPrefixedMessageToKey(nonce.getBytes(), data);
        return matches(key, address);
    }

    private boolean matches(BigInteger key, String address) {
        return ("0x" + Keys.getAddress(key).toLowerCase()).equals(address.toLowerCase());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.findByAddress(authentication.getName());
        if(user != null){
            String signature = authentication.getCredentials().toString();
            try {
                if(valid(signature, user.getAddress(), user.getNonce())){
                    return new Web3Authentication(user.getAddress(), signature);
                }
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }
        }
        throw new BadCredentialsException(authentication.getName() + "is not allowed to log in");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Web3Authentication.class.isAssignableFrom(authentication);
    }
}

package com.nftapp.nftapp.Controller;

import com.nftapp.nftapp.Auth.Web3Authentication;
import com.nftapp.nftapp.Model.SignRequest;
import com.nftapp.nftapp.Model.User;
import com.nftapp.nftapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController("/auth")
public class AuthController {
    private  UserService userService;

    private AuthenticationManager manager;

    public AuthController(UserService userService, AuthenticationManager manager) {
        this.userService = userService;
        this.manager = manager;
    }

    @GetMapping("/{address}")
    public String challenge(@PathVariable String address) throws Exception {
        User user = userService.findByAddress(address);
        if(user != null) {
            return user.getNonce();
        }
        throw new UnknownAddressException(address);
    }

    @PostMapping("/auth")
    public Authentication auth(@RequestParam String signature, @RequestParam String address){
        SignRequest request = new SignRequest(signature, address);
        return manager.authenticate(new Web3Authentication(request.getAddress(), request.getSignature()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    class UnknownAddressException extends RuntimeException {
        public UnknownAddressException(String address) {
            super("Wallet address " + address + " is not known");
        }
    }
}

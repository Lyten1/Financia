package com.finance.configs;

import com.finance.models.User;
import com.finance.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        System.out.println("Authentication username = " + username);
        System.out.println("Authentication user = " + user);
        if (user == null) {
            throw new UsernameNotFoundException("Client not found");
        }
        String password = authentication.getCredentials().toString();

        if (!password.equals(user.getPassword())) {
            System.out.println("пароль" + password + "не подошел");
            throw new BadCredentialsException("Bad credentials");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("пароль" + password + "не подошел");
            throw new BadCredentialsException("Bad credentials");
        }


        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

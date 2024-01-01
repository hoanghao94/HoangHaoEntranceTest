package com.nexle.test.entrance.config;

import com.nexle.test.entrance.domain.Users;
import com.nexle.test.entrance.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    @Lazy
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> user = usersService.findOneByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with Email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getHash(),
                new ArrayList<>());
    }
}


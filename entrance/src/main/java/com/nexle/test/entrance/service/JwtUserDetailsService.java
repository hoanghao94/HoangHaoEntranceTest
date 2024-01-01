package com.nexle.test.entrance.service;

import com.nexle.test.entrance.config.Constants;
import com.nexle.test.entrance.config.JwtTokenUtil;
import com.nexle.test.entrance.domain.Tokens;
import com.nexle.test.entrance.domain.Users;
import com.nexle.test.entrance.dto.JwtResponseDTO;
import com.nexle.test.entrance.dto.SignUpInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TokensService tokensService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersService.findOneByEmail(email).get();
        if (user == null) {
            throw new UsernameNotFoundException("User not found with Email: " + email);
        }
        return new User(user.getEmail(), user.getHash(),
                new ArrayList<>());
    }

    public JwtResponseDTO generateToken(SignUpInfoDTO signUpInfoDTO){
        UserDetails userDetails = this.loadUserByUsername(signUpInfoDTO.getEmail());
        Users users = usersService.findOneByEmail(signUpInfoDTO.getEmail()).get();
        final String token = jwtTokenUtil.generateToken(userDetails, Constants.EXP_TOKEN);
        final String refreshToken = jwtTokenUtil.generateToken(userDetails, Constants.EXP_REFRESH_TOKEN);
        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
        jwtResponseDTO.setToken(token);
        jwtResponseDTO.setRefreshToken(refreshToken);
        signUpInfoDTO.setDisplayName(signUpInfoDTO.getFirstName() + " " + signUpInfoDTO.getLastName());

        com.nexle.test.entrance.dto.User user = new com.nexle.test.entrance.dto.User(users.getFirstName(),
                                                                                    users.getLastName(),
                                                                                    users.getEmail(),
                                                                         users.getFirstName() + " " + users.getLastName());
        jwtResponseDTO.setUser(user);
        Tokens tokens = new Tokens();
        tokens.setUserId(users.getId());
        tokens.setRefreshToken(refreshToken);
        tokens.setExpiresIn(String.valueOf(Constants.EXP_REFRESH_TOKEN));
        if (tokensService.findOneByUserId(users.getId()).isPresent()){
            tokens.setId(tokensService.findOneByUserId(users.getId()).get().getId());
            tokens.setUpdatedAt(LocalDateTime.now());
            tokens.setCreatedAt(tokensService.findOneByUserId(users.getId()).get().getCreatedAt());
        }
        else {
            tokens.setCreatedAt(LocalDateTime.now());
        }
        tokensService.save(tokens);
        return jwtResponseDTO;
    }
}

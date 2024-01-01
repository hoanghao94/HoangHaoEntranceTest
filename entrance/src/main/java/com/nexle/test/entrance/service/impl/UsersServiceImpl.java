package com.nexle.test.entrance.service.impl;

import com.nexle.test.entrance.domain.Users;
import com.nexle.test.entrance.dto.JwtResponseDTO;
import com.nexle.test.entrance.dto.SignUpInfoDTO;
import com.nexle.test.entrance.dto.SignUpOutDTO;
import com.nexle.test.entrance.repository.UsersRepository;
import com.nexle.test.entrance.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users save(Users users) {
        return usersRepository.save(users);
    }

    /***
     *
     * @return All Users
     */
    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    /***
     *
     * @param signUpInfoDTO
     */
    @Override
    public SignUpOutDTO createUser(SignUpInfoDTO signUpInfoDTO) {
    Users users = new Users();
    users.setEmail(signUpInfoDTO.getEmail());
    users.setCreatedAt(LocalDateTime.now());
    users.setFirstName(signUpInfoDTO.getFirstName());
    users.setLastName(signUpInfoDTO.getLastName());
    CharSequence pass = signUpInfoDTO.getPassword();
    users.setHash(passwordEncoder.encode(pass));
    this.save(users);
    SignUpOutDTO signUpOutDTO = new SignUpOutDTO();
    signUpOutDTO.setFirstName(signUpInfoDTO.getFirstName());
    signUpOutDTO.setLastName(signUpInfoDTO.getLastName());
    signUpOutDTO.setEmail(signUpInfoDTO.getEmail());
    signUpOutDTO.setDisplayName(signUpInfoDTO.getFirstName() + " " + signUpInfoDTO.getLastName());
    signUpOutDTO.setId(usersRepository.findOneByEmail(signUpInfoDTO.getEmail()).get().getId());
    return signUpOutDTO;
    }

    /***
     *
     * @param email
     * @return
     */
    @Override
    public Optional<Users> findOneByEmail(String email) {
        return usersRepository.findOneByEmail(email);
    }
}

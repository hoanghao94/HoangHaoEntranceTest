package com.nexle.test.entrance.service;

import com.nexle.test.entrance.domain.Users;
import com.nexle.test.entrance.dto.JwtResponseDTO;
import com.nexle.test.entrance.dto.SignUpInfoDTO;
import com.nexle.test.entrance.dto.SignUpOutDTO;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    /**
     *
     * @param users
     * @return
     */
    Users save(Users users);

    /**
     *
     * @return All List of Users
     */
    List<Users> findAll();

    /***
     *
     * @param email
     * @return
     */
    Optional<Users> findOneByEmail(String email);

    /***
     *
     * @param signUpInfoDTO
     */
    SignUpOutDTO createUser(SignUpInfoDTO signUpInfoDTO);


}

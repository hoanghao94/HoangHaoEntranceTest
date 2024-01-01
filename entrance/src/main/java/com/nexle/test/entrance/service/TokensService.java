package com.nexle.test.entrance.service;

import com.nexle.test.entrance.domain.Tokens;
import com.nexle.test.entrance.domain.Users;
import com.nexle.test.entrance.dto.SignUpInfoDTO;
import com.nexle.test.entrance.dto.SignUpOutDTO;

import java.util.List;
import java.util.Optional;

public interface TokensService {

    /**
     *
     * @param users
     * @return
     */
    Tokens save(Tokens users);

    /**
     *
     * @return All List of Users
     */
    List<Tokens> findAll();

    /***
     *
     * @param userId
     */
    void deleteAllByUserId(int userId);

    /***
     *
     * @param userId
     * @return
     */
    Optional<Tokens> findOneByUserId(Integer userId);
}

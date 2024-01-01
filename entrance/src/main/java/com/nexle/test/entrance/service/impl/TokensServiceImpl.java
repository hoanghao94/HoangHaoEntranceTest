package com.nexle.test.entrance.service.impl;

import com.nexle.test.entrance.domain.Tokens;
import com.nexle.test.entrance.domain.Users;
import com.nexle.test.entrance.dto.SignUpInfoDTO;
import com.nexle.test.entrance.dto.SignUpOutDTO;
import com.nexle.test.entrance.repository.TokensRepository;
import com.nexle.test.entrance.repository.UsersRepository;
import com.nexle.test.entrance.service.TokensService;
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
public class TokensServiceImpl implements TokensService {

    @Autowired
    private TokensRepository tokensRepository;

    @Override
    public Tokens save(Tokens tokens) {
        return tokensRepository.save(tokens);
    }

    @Override
    public List<Tokens> findAll() {
        return tokensRepository.findAll();
    }

    @Override
    public void deleteAllByUserId(int userId) {
         tokensRepository.deleteAllByEmail(userId);
    }

    @Override
    public Optional<Tokens> findOneByUserId(Integer userId) {
        return tokensRepository.findOneByUserId(userId);
    }

}

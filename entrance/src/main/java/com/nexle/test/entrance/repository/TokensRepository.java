package com.nexle.test.entrance.repository;

import com.nexle.test.entrance.domain.Tokens;
import com.nexle.test.entrance.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface TokensRepository extends JpaRepository<Tokens, Long> {
    /***
     *
     * @param userId
     */
    @Modifying
    @Transactional
    @Query("delete from Tokens u where u.userId = :userId")
    void deleteAllByEmail(@Param("userId") int userId);

    /***
     *
     * @param userId
     * @return
     */
    Optional<Tokens> findOneByUserId(Integer userId);
}

package com.example.demo.Domain.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Domain.models.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token,Long> {
    
    Optional<Token> findByToken(String token); 

    @Query(name = "GetUserValidTokens")
    List<Token> findAllValidUserTokens(@Param("userId") Long userId);

    void deleteAllByRevokedAndExpired(boolean revoked, boolean expired);
}

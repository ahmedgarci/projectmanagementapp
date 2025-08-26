package com.example.demo.Infrastructure.Schedulers;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.Domain.Repository.InvitationRepository;
import com.example.demo.Domain.Repository.TokenRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseScheduler {
    private final TokenRepository tokenRepository;
    private final InvitationRepository invitationRepository;
    
    @Scheduled(fixedDelay = 1000 * 60 *60)
    public void DeleteExpiredTokenAndInvitations(){
        tokenRepository.deleteAllByRevokedAndExpired(true, true);
        invitationRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
    
}

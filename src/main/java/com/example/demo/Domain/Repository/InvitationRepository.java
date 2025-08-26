package com.example.demo.Domain.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Domain.models.Invitation;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation,Long> {
    Optional<Invitation> findByCode(String code);
    void deleteByExpiresAtBefore(LocalDateTime now);
}

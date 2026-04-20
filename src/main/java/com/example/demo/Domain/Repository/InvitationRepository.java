package com.example.demo.Domain.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Domain.models.Invitation;
import com.example.demo.Domain.models.Project;
import com.example.demo.Domain.models.User;

@Repository
public interface InvitationRepository extends CrudRepository<Invitation,Long> {
    Optional<Invitation> findByCode(String code);
    void deleteByExpiresAtBefore(LocalDateTime now);

    boolean existsByProjectAndReceiverAndExpiresAtAfter(Project project, User receiver, LocalDateTime now);

}

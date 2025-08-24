package com.example.demo.Domain.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Domain.models.Invitation;

public interface InvitationRepository extends CrudRepository<Invitation,Long> {
    Optional<Invitation> findByCode(String code);
}

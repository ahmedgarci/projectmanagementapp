package com.example.demo.Domain.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Domain.models.User;



@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
    Optional<User> findByEmail(String email);
    Optional<User> findByPublicId(String publicId);
}

package com.example.demo.Domain.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Domain.models.Stages;

public interface StagesRepository extends CrudRepository<Stages,Long> {
    Optional<Stages> findByStageName(String stageName);
     
}

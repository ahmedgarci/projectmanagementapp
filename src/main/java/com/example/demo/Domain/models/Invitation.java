package com.example.demo.Domain.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.Domain.Constants.InvitationState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( length = 6,name = "invitation_code",nullable = false, updatable = false,unique = true)
    private String code;
    @CreatedDate
    private LocalDateTime createdAt;
    
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    private InvitationState invitation_status;

    @ManyToOne
    @JoinColumn(name = "sender_id",nullable = false,updatable = false)
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id",nullable = false,updatable = false)
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "project_id",nullable = false)
    private Project project;



}

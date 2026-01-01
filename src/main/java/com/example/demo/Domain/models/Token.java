package com.example.demo.Domain.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NamedQuery(name = "GetUserValidTokens",
    query = "SELECT t FROM Token t WHERE user.id = :userId AND (t.expired= false OR t.revoked = false)" 
    )
@Table(indexes = @Index(name = "token_Index", columnList = "jwt_token"))
public class Token {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;

    @Column(name = "jwt_token",nullable = false,columnDefinition = "VARCHAR(300)")
    private String token;

    @Column(name = "is_token_expired",nullable = false)
    private boolean expired;

    @Column(name = "is_token_revoked",nullable = false)
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
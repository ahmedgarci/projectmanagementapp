package com.example.demo.Domain.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee",
    indexes = @Index(name="employee_email_index", columnList = "email")
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails{
    @Id
    @SequenceGenerator(name = "user_seq",sequenceName = "user_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    private Long id;
    @Column(nullable = false, updatable = false,name = "user_public_id")
    private String publicId;
    @Column(nullable = false, updatable = true,unique = true)
    private String email;
    @Column(nullable = false, updatable = true,name = "user_full_name")
    private String fullName;
    @Column(updatable = false,name = "user_password")
    private String password; 
    @Column(updatable = true,name = "user_postition")
    private String jobPos;
    @CreatedDate
    private LocalDate created_at;

    @ManyToMany
    @JoinTable(
        name = "projects_users",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns=@JoinColumn(name="project_id")
    )
    private Set<Project> projects;

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy = "user"
    )
    private List<Token> tokens;

    @OneToMany(mappedBy = "sender")
    private List<Invitation> invitationsSent;
    @OneToMany(mappedBy = "receiver")
    private List<Invitation> invitationsReceived;
    @OneToMany(mappedBy = "user")
    private List<Tasks> tasks ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {return this.email;}
    @Override
    public boolean isAccountNonExpired() {return true;}
    @Override
    public boolean isAccountNonLocked() {return true;}
    @Override
    public boolean isCredentialsNonExpired() {return true;}
    @Override
    public boolean isEnabled() {return true;}

    @PrePersist
    private void initUUID(){
        if(this.publicId == null){
            this.publicId = UUID.randomUUID().toString();
        }
    }
}

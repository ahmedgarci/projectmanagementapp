package com.example.demo.Domain.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.Domain.Constants.ProjectConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
// THIS QUERY SEARCHS FOR THE PROJECTS THAT THE GIVEN USER IS INVOLVED IN
@NamedQuery(
    name = ProjectConstants.findProjectsByUser,
    query = "SELECT DISTINCT p FROM Project p WHERE :user MEMBER OF p.users"
)
public class Project {
    @Id
    @SequenceGenerator(sequenceName = "project_seq",allocationSize = 2,name = "project_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "project_seq")
    private Long id;
    @Column(unique = true,nullable = false, updatable = false)
    private String publicId;
    @Column(nullable = false, updatable = false,unique = true)
    private String projectName;
    @Column(nullable = false, updatable = true)
    private String projectDescription;
    @Column(name = "project_starting_date",nullable = false)
    private LocalDate startingDate;
    @Column(name = "project_ending_date",nullable = false)
    private LocalDate endingDate;
    
    @ManyToMany(mappedBy = "projects")
    private Set<User> users;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stages stage;

    @OneToMany(mappedBy = "project")
    private List<Invitation> projecInvitations;

    @OneToMany(mappedBy = "project")
    private List<Tasks> projectTasks;


    @PrePersist
    private void fillPublicId(){
        if(publicId == null){
            this.publicId = UUID.randomUUID().toString();
        }
    }
    
}

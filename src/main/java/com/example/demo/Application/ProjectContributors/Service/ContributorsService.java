package com.example.demo.Application.ProjectContributors.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.Application.ProjectContributors.Interface.ProjectContributorsManagementInterface;
import com.example.demo.Application.ProjectContributors.Mappers.UserMapper;
import com.example.demo.Application.ProjectContributors.Requests.Main.AddContributorRequest;
import com.example.demo.Application.ProjectContributors.Responses.ContributorDetailsResponse;
import com.example.demo.Domain.Repository.InvitationRepository;
import com.example.demo.Domain.Repository.ProjectRepository;
import com.example.demo.Domain.Repository.UserRepository;
import com.example.demo.Domain.models.Invitation;
import com.example.demo.Domain.models.Project;
import com.example.demo.Domain.models.User;
import com.example.demo.Infrastructure.Mailing.MailService;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContributorsService implements ProjectContributorsManagementInterface {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final InvitationRepository invitationRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void addNewContributorToProject(AddContributorRequest addContributorRequest,Authentication authentication) {
        User receiver = userRepository.findByEmail(addContributorRequest.getContributorEmailVo().contributorEmail())
            .orElseThrow(()-> new EntityNotFoundException("contributor was not found"));
        
        Project project = projectRepository.findByPublicId(addContributorRequest.getProjectPublicIdVo().projectPublicId())
            .orElseThrow(()-> new EntityNotFoundException("project was not found"));
        
        if(project.getUsers().contains(receiver)){
            throw new IllegalStateException("receiver is already a contributor");
        }

        User connectedUser = (User)authentication.getPrincipal();
        LocalDateTime invitationExpirationDate = Instant.ofEpochMilli(System.currentTimeMillis()+360000000).atZone(ZoneId.systemDefault()).toLocalDateTime();
        Invitation invitation = Invitation.builder().code(generateRandomInvitationCode()).receiver(receiver).sender(connectedUser).expiresAt(invitationExpirationDate)
            .project(project).build();
        invitationRepository.save(invitation);
        try {
            mailService.SendEmail(connectedUser.getEmail(),receiver.getEmail(), receiver.getFullName(),project.getProjectName(),invitation.getCode());            
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }        
    }

    @Override
    public void removeContributorFromProject() {
        
        throw new UnsupportedOperationException("Unimplemented method 'removeContributorFromProject'");
    }



    @Override
    public void acceptProjectInvite(String code) {
        Invitation invitation = invitationRepository.findByCode(code).orElseThrow(()-> new EntityNotFoundException("inv was not found"));
        if(invitation.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("invitation expired");
        }

        Project project = projectRepository.findById(invitation.getProject().getId()).orElseThrow(()-> new EntityNotFoundException("project was not found"));
        User receiver = userRepository.findById(invitation.getReceiver().getId()).orElseThrow(()-> new EntityNotFoundException("user was not found"));
        receiver.getProjects().add(project);
        project.getUsers().add(receiver);
        userRepository.save(receiver);
        projectRepository.save(project);
    }


    public List<ContributorDetailsResponse> getProjectContributors(String projectPublicId){
        Project project = projectRepository.findByPublicId(projectPublicId).orElseThrow(()-> new EntityNotFoundException("project was not found"));
        Set<User> projectContributors = project.getUsers(); 
        List<ContributorDetailsResponse> contributorsResponse = projectContributors.stream().map(userMapper::FromUserToContributorDetailsResponse).toList();
        return contributorsResponse;
    }
 


    private String generateRandomInvitationCode(){
        String values = "AZERTYUIOPMLKJHGFDSQWXCVBN1234567890azertyuiopmlkjhnbgvfcdxswq";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i=0; i < 6;i++){
            int index = random.nextInt(values.length());
            codeBuilder.append(values.charAt(index));
        }
        return codeBuilder.toString();
    }

}

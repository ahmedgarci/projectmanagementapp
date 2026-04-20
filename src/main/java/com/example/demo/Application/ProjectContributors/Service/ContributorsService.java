package com.example.demo.Application.ProjectContributors.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.example.demo.Application.ProjectContributors.Interface.ProjectContributorsManagementInterface;
import com.example.demo.Application.ProjectContributors.Mappers.UserMapper;
import com.example.demo.Application.ProjectContributors.Requests.Main.AddContributorRequest;
import com.example.demo.Application.ProjectContributors.Responses.ContributorDetailsResponse;
import com.example.demo.Domain.Constants.InvitationState;
import com.example.demo.Domain.Repository.InvitationRepository;
import com.example.demo.Domain.Repository.ProjectRepository;
import com.example.demo.Domain.Repository.UserRepository;
import com.example.demo.Domain.models.Invitation;
import com.example.demo.Domain.models.Project;
import com.example.demo.Domain.models.User;
import com.example.demo.Infrastructure.Mailing.InvitationEvent;
import com.example.demo.Infrastructure.Security.SecurityUtils;

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
    private final InvitationRepository invitationRepository;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void addNewContributorToProject(AddContributorRequest addContributorRequest) {
        User receiver = userRepository.findByEmail(addContributorRequest.getContributorEmailVo().contributorEmail())
            .orElseThrow(()-> new EntityNotFoundException("contributor was not found"));
        
        Project project = projectRepository.findByPublicId(addContributorRequest.getProjectPublicIdVo().projectPublicId())
            .orElseThrow(()-> new EntityNotFoundException("project was not found"));
        
        if(project.getUsers().contains(receiver) || invitationRepository.existsByProjectAndReceiverAndExpiresAtAfter(project, receiver, LocalDateTime.now())){
            throw new IllegalStateException("receiver is already a contributor or has already a pending invitation");
        }

        User connectedUser = SecurityUtils.getConnectedUser();
        LocalDateTime invitationExpirationDate = LocalDateTime.now().plusDays(1);
        Invitation invitation = Invitation.builder().code(generateRandomInvitationCode()).receiver(receiver)
            .sender(connectedUser).expiresAt(invitationExpirationDate)
            .invitation_status(InvitationState.PENDING).project(project).build();
        invitationRepository.save(invitation);
        eventPublisher.publishEvent(new InvitationEvent(connectedUser.getEmail(),receiver.getEmail(), receiver.getFullName(),project.getProjectName(),invitation.getCode()));
              
    }

    @Override
    public void removeContributorFromProject() {
    
        throw new UnsupportedOperationException("Unimplemented method 'removeContributorFromProject'");

    }



    @Override
    @Transactional
    public void acceptProjectInvite(String code) {
        Invitation invitation = invitationRepository.findByCode(code).orElseThrow(()-> new EntityNotFoundException("inv was not found"));
        if(invitation.getInvitation_status() != InvitationState.PENDING){
            throw new IllegalStateException("invitation already processed");
        }

        if(invitation.getExpiresAt().isBefore(LocalDateTime.now())){
            invitation.setInvitation_status(InvitationState.EXPIRED);
            invitationRepository.save(invitation);
            throw new IllegalStateException("ticket invitation is expired");
        }
        
        Project project = invitation.getProject();
        User receiver = invitation.getReceiver();
        receiver.getProjects().add(project);
        project.getUsers().add(receiver);
        invitation.setInvitation_status(InvitationState.ACCEPTED);
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

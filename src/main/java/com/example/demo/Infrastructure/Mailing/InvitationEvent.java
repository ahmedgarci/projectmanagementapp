package com.example.demo.Infrastructure.Mailing;

public record InvitationEvent(
    String from,    
    String to,
    String username,
    String projectName,
    String code
) {
} 
  



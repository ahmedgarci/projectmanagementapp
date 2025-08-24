package com.example.demo.Application.Projects.Requests.VO;

import java.time.LocalDate;


import jakarta.validation.constraints.NotNull;

public record StartingDateVO(

    @NotNull(message = "starting date is required")
    LocalDate startingDate
) {
    
}

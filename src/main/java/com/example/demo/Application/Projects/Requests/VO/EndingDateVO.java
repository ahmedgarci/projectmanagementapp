package com.example.demo.Application.Projects.Requests.VO;

import java.time.LocalDate;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record EndingDateVO(

    @NotNull(message = "starting date is required")
    @Future(message = "ending date should be in the future")
    LocalDate endingDate
) {
}
package com.example.demo.Application.Tasks.Requests.Vo;

import java.time.LocalDate;

public  record TaskDatesVO(
    LocalDate startingDate,
    LocalDate endingDate     
) {
}


package com.example.demo.Application.Tasks.Requests.Vo;

import jakarta.validation.constraints.NotNull;

record ProjectIdVo(
    @NotNull(message="project id should not be null")
    String projectId
) {
}
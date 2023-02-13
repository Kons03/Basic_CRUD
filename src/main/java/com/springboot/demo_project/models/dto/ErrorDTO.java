package com.springboot.demo_project.models.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ErrorDTO {

    @Size(min = 1, max = 15)
    private String code;

    @Size(min = 1, max = 100)
    private String errorDetails;
}

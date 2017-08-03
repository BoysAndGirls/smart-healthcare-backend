package com.springboot.dto;

import lombok.Data;

@Data
public class SelectPersonInfo {
    private Integer id;
    private String name;
    private String language;
    private String specialty;
    private String education;
    private String cooperationType;
}

package com.springboot.demo_project.models.dto;

import com.springboot.demo_project.models.entity.RoleEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {

    private Long id;

    private String username;

    private String name;

    private String surname;

    private int age;

    private String email;

    private List<String> roles = new ArrayList<>();

}

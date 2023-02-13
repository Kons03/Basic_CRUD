package com.springboot.demo_project.service;

import com.springboot.demo_project.models.dto.UserDTO;
import com.springboot.demo_project.models.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<UserDTO> getUsers();

    UserDTO getUser(Long id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    void deleteUser(Long id);
}

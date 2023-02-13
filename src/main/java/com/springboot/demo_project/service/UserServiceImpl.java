package com.springboot.demo_project.service;

import com.springboot.demo_project.models.dto.UserDTO;
import com.springboot.demo_project.models.entity.RoleEntity;
import com.springboot.demo_project.models.entity.UserEntity;
import com.springboot.demo_project.repository.RoleRepository;
import com.springboot.demo_project.repository.UserRepository;
import com.springboot.demo_project.util.exception.UserAlreadyExistsException;
import com.springboot.demo_project.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserEntity mapToUserEntity(UserDTO userDto) {
        var userEntity = new UserEntity();
        var rolesDto = userDto.getRoles() == null
                ? null
                : roleRepository.findByIdIn(userDto.getRoles().stream()
                .map(Long::valueOf)
                .collect(Collectors.toList()));
        userEntity.setId(userDto.getId());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setName(userDto.getName());
        userEntity.setSurname(userDto.getSurname());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setAge(userDto.getAge());
        userEntity.setRoles(rolesDto);
        userEntity.setSalary(30000);
        return userEntity;
    }

    public UserDTO mapToUserDTO(UserEntity userEntity) {
        var userDto = new UserDTO();
        var rolesEntity = userEntity.getRoles() == null
                ? null
                : userEntity.getRoles().stream()
                .map(RoleEntity::getId)
                .map(String::valueOf)
                .collect(Collectors.toList());
        userDto.setId(userEntity.getId());
        userDto.setUsername(userEntity.getUsername());
        userDto.setName(userEntity.getName());
        userDto.setSurname(userEntity.getSurname());
        userDto.setEmail(userEntity.getEmail());
        userDto.setAge(userEntity.getAge());
        userDto.setRoles(rolesEntity);
        return userDto;
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(Long id) {
        return mapToUserDTO(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with this id is not found!")));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        var databaseUser = userRepository.existsUserEntityByUsername(userDTO.getUsername());
        if (databaseUser) {
            throw new UserAlreadyExistsException("User with this username already exists!");
        }
        userDTO.setId(null);
        var userEntity = mapToUserEntity(userDTO);
        return mapToUserDTO(userRepository.save(userEntity));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        var databaseUser = userRepository.findById(userDTO.getId());
        if (databaseUser.isEmpty()) {
            throw new UserNotFoundException("User with this id is not found!");
        }
        userDTO.setId(databaseUser.get().getId());
        var userEntity = mapToUserEntity(userDTO);
        return mapToUserDTO(userRepository.save(userEntity));
    }

    @Override
    public void deleteUser(Long id) {
        var userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with this id is not found!"));
        userRepository.delete(userEntity);
    }
}

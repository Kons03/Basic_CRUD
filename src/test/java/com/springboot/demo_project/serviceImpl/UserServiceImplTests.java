package com.springboot.demo_project.serviceImpl;

import com.springboot.demo_project.models.dto.UserDTO;
import com.springboot.demo_project.models.entity.RoleEntity;
import com.springboot.demo_project.models.entity.UserEntity;
import com.springboot.demo_project.repository.RoleRepository;
import com.springboot.demo_project.repository.UserRepository;
import com.springboot.demo_project.service.UserServiceImpl;
import com.springboot.demo_project.util.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetUser() {
        final var userDto = new UserDTO();
        final Long id = 1L;
        final String name = "name";
        final String surname = "surname";
        final int age = 20;
        final String email = "email@gmail.com";
        final String username = "1111";

        userDto.setId(id);
        userDto.setName(name);
        userDto.setSurname(surname);
        userDto.setAge(age);
        userDto.setEmail(email);
        userDto.setUsername(username);

        var userEntity = userService.mapToUserEntity(userDto);
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        var actualUser = userService.getUser(id);

        assertEquals(userDto, actualUser);
    }

    @Test
    void testGetUserNotFoundException() {
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.getUser(1L))
                .withMessage("User with this id is not found!");
    }

    @Test
    void testCreateUser() {
        final var userDto = new UserDTO();
        final var roleEntity = new RoleEntity();
        final Long userId = null;
        final Long roleId = 1L;
        final String name = "name";
        final String surname = "surname";
        final int age = 20;
        final String email = "email@gmail.com";
        final String username = "1111";

        userDto.setId(userId);
        userDto.setName(name);
        userDto.setSurname(surname);
        userDto.setAge(age);
        userDto.setEmail(email);
        userDto.setUsername(username);
        userDto.setRoles(Collections.emptyList());

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(roleEntity));
        roleEntity.setId(roleId);
        var userEntity = userService.mapToUserEntity(userDto);

        when(userRepository.existsUserEntityByUsername(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userEntity);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(userEntity));

        var actualUser = userService.createUser(userDto);

        assertEquals(userDto, actualUser);
    }

    @Test
    void testUpdateUser() {
        final var userDto = new UserDTO();
        final var roleEntity = new RoleEntity();
        final Long userId = null;
        final Long roleId = 1L;
        final String name = "name";
        final String surname = "surname";
        final int age = 20;
        final String email = "email@gmail.com";
        final String username = "1111";

        userDto.setId(userId);
        userDto.setName(name);
        userDto.setSurname(surname);
        userDto.setAge(age);
        userDto.setEmail(email);
        userDto.setUsername(username);
        userDto.setRoles(Collections.emptyList());

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(roleEntity));
        roleEntity.setId(roleId);
        var userEntity = userService.mapToUserEntity(userDto);

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(userEntity));
        when(userRepository.save(any())).thenReturn(userEntity);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(userEntity));

        var actualUser = userService.updateUser(userDto);

        assertEquals(userDto, actualUser);
    }

    @Test
    void testDeleteUser() {
        final var userEntity = new UserEntity();
        final Long id = 1L;

        userEntity.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        userService.deleteUser(id);

        verify(userRepository).delete(userEntity);
    }

    @Test
    void testDeleteUserNotFoundException() {
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userService.deleteUser(1L))
                .withMessage("User with this id is not found!");
    }
}

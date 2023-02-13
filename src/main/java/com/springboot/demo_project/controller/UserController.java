package com.springboot.demo_project.controller;

import com.springboot.demo_project.models.dto.UserDTO;
import com.springboot.demo_project.models.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springboot.demo_project.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/v2")
@Api("CRUD operations with UI - Swagger")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Client error"),
        @ApiResponse(responseCode = "500", description = "Server error")
})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    @Operation(summary = "Getting the list of users", description = "Getting the list of users")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        var users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    @Operation(summary = "Getting the user from list", description = "Getting the user from list")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(Long id) {
        var userDto = userService.getUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created")
    })
    @Operation(summary = "Creation of user", description = "Creation of user")
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        var createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    @Operation(summary = "Editing of user", description = "Editing of user")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO updatedUser,
                                              @PathVariable("id") Long id) {
        var updUser = userService.updateUser(updatedUser);
        return new ResponseEntity<>(updUser, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful")
    })
    @Operation(summary = "Deleting of user", description = "Deleting of user")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

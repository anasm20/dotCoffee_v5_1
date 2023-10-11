package com.waff.rest.demo.controller;
import java.util.List;
import com.waff.rest.demo.dto.UserDto;
import com.waff.rest.demo.model.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.waff.rest.demo.model.User;
import com.waff.rest.demo.service.UserService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/admin/user")
    // Handles GET request to retrieve all users.
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/user")
    // Handles POST request to create a new user.
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User created = userService.createUser(user).orElse(null);
        if (created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{id}")
    // Handles GET request to retrieve a user by its ID.
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/user/{id}")
    // Handles PUT request to update an existing user.
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserDto userDto) {        
        User user = modelMapper.map(userDto, User.class);
        User updated = userService.updateUser(user).orElse(null);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/user/{id}")
    // Handles DELETE request to delete a user by its ID.
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (userService.deleteUserById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/admin/user/user_type/{userType}")
    // Handles GET request to retrieve users by their user type.
    public ResponseEntity<List<User>> findUsersByType(@PathVariable UserRole userType) {
        return ResponseEntity.ok(userService.getUserByUserType(userType));
    }

    @GetMapping("/admin/user/role")
    // Handles GET request for finding all users with ROLE_ADMIN.
    public ResponseEntity<Void> isAdmin() {
        return ResponseEntity.ok().build();
    }
}

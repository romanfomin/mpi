package ru.itmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.exceptions.BadRequestException;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.User;
import ru.itmo.sevices.UserService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) throws BadRequestException, ResourceNotFoundException {
        if (!user.getId().equals(userId)) {
            throw new BadRequestException("User id mismatch");
        }

        userService.getById(userId);
        return userService.updateUser(user);
    }

}

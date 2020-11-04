package ru.itmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.models.ApplicationState;
import ru.itmo.models.Role;
import ru.itmo.sevices.ApplicationStateService;
import ru.itmo.sevices.RoleService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/applicationStates")
public class ApplicationStateController {

    @Autowired
    private ApplicationStateService applicationStateService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ApplicationState> getApplicationStates() {
        return applicationStateService.getApplicationStates();
    }
}

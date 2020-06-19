package ru.itmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.exceptions.BadRequestException;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.Script;
import ru.itmo.models.User;
import ru.itmo.sevices.ScriptService;
import ru.itmo.sevices.UserService;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/scripts")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Script> getScripts() {
        return scriptService.getScripts();
    }

    @GetMapping(value = "/{airDate}")
    @PreAuthorize("isAuthenticated()")
    public Script getScriptByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date airDate) throws ResourceNotFoundException {
        return scriptService.getByDate(airDate);
    }

    @PostMapping
    @PreAuthorize("hasRole('SCRIPTWRITER')")
    public Script saveScript(@RequestBody Script script) {
        return scriptService.save(script);
    }

    @PutMapping(value = "/{scriptId}")
    @PreAuthorize("hasRole('SCRIPTWRITER')")
    public Script updateScript(@PathVariable Long scriptId, @RequestBody Script script) throws BadRequestException, ResourceNotFoundException {
        if (!script.getId().equals(scriptId)) {
            throw new BadRequestException("Script id mismatch");
        }
        Script scriptById = scriptService.getById(script.getId());
        scriptById.setText(script.getText());
        return scriptService.save(scriptById);
    }
}

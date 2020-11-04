package ru.itmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.Application;
import ru.itmo.models.ApplicationState;
import ru.itmo.models.EApplicationState;
import ru.itmo.models.File;
import ru.itmo.sevices.ApplicationService;
import ru.itmo.sevices.ApplicationStateService;
import ru.itmo.sevices.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationStateService applicationStateService;

    @Autowired
    private FileService fileService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Application> getApplications() {
        return applicationService.getApplications();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADVERTISER')")
    public Application createApplication(Application application,
                                         @RequestParam(value = "attachments") List<MultipartFile> multipartFiles) throws IOException {
        List<File> files = new ArrayList<>();
        for (MultipartFile multipartFile: multipartFiles){
            files.add(fileService.save(multipartFile));
        }
        ApplicationState applicationState = applicationStateService.getApplicationState(EApplicationState.STATE_NEW);
        application.setState(applicationState);
        application.setFiles(files);
        application.setAppDate(new Date());
        return applicationService.save(application);
    }

    @PutMapping(value = "/{applicationId}/accept")
    @PreAuthorize("hasRole('MANAGER')")
    public Application acceptApplication(@PathVariable Long applicationId) throws ResourceNotFoundException {
        Application applicationById = applicationService.getById(applicationId);
        ApplicationState applicationState = applicationStateService.getApplicationState(EApplicationState.STATE_ACCEPTED);
        applicationById.setState(applicationState);
        return applicationService.save(applicationById);
    }

    @PutMapping(value = "/{applicationId}/cancel")
    @PreAuthorize("hasRole('MANAGER')")
    public Application cancelApplication(@PathVariable Long applicationId) throws ResourceNotFoundException {
        Application applicationById = applicationService.getById(applicationId);
        ApplicationState applicationState = applicationStateService.getApplicationState(EApplicationState.STATE_CANCELLED);
        applicationById.setState(applicationState);
        return applicationService.save(applicationById);
    }

    @PutMapping(value = "/{applicationId}/finish")
    @PreAuthorize("hasRole('MANAGER')")
    public Application finishApplication(@PathVariable Long applicationId) throws ResourceNotFoundException {
        Application applicationById = applicationService.getById(applicationId);
        ApplicationState applicationState = applicationStateService.getApplicationState(EApplicationState.STATE_DONE);
        applicationById.setState(applicationState);
        return applicationService.save(applicationById);
    }
}

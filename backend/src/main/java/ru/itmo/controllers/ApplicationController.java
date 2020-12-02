package ru.itmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.*;
import ru.itmo.sevices.ApplicationService;
import ru.itmo.sevices.ApplicationStateService;
import ru.itmo.sevices.ApplicationTypeService;
import ru.itmo.sevices.FileService;

import java.io.IOException;
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
    private ApplicationTypeService applicationTypeService;

    @Autowired
    private FileService fileService;

    @GetMapping
    @PreAuthorize("hasRole('ADVERTISER') or hasRole('OPERATOR') or hasRole('MANAGER') or hasRole('DECORATOR')")
    @Transactional
    public List<Application> getApplications(@RequestParam(value = "type") EApplicationType type) {
        return applicationService.getApplications(type);
    }

    @GetMapping(value = "/states")
    @PreAuthorize("isAuthenticated()")
    public List<ApplicationState> getApplicationStates() {
        return applicationStateService.getApplicationStates();
    }

    @GetMapping(value = "/types")
    @PreAuthorize("isAuthenticated()")
    public List<ApplicationType> getApplicationTypes() {
        return applicationTypeService.getApplicationTypes();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADVERTISER') or hasRole('OPERATOR')")
    @Transactional
    public Application createApplication(Application application,
                                         @RequestParam(value = "app_type") EApplicationType type,
                                         @RequestParam(value = "attachments") List<MultipartFile> multipartFiles) throws IOException, ResourceNotFoundException {
        List<File> files = fileService.saveFiles(multipartFiles, EFileType.TYPE_TASK);
        return applicationService.saveApplication(application, type, files);
    }

    @PutMapping(value = "/{applicationId}/state")
    @PreAuthorize("hasRole('MANAGER') or hasRole('DECORATOR')")
    public Application updateState(@PathVariable Long applicationId,
                                   @RequestParam(value = "state") EApplicationState state) throws ResourceNotFoundException {
        Application applicationById = applicationService.getById(applicationId);
        ApplicationState applicationState = applicationStateService.getApplicationState(state);
        applicationById.setState(applicationState);
        return applicationService.update(applicationById);
    }

    @PostMapping(value = "/{applicationId}/files",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public Application addFiles(@PathVariable Long applicationId,
                                @RequestParam(value = "file_type") EFileType type,
                                @RequestParam(value = "attachments") List<MultipartFile> multipartFiles) throws IOException, ResourceNotFoundException {
        Application applicationById = applicationService.getById(applicationId);
        List<File> files = fileService.saveFiles(multipartFiles, type);
        List<File> storedFiles = applicationById.getFiles();
        storedFiles.addAll(files);
        applicationById.setFiles(storedFiles);
        return applicationService.update(applicationById);
    }
}

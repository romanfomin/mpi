package ru.itmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/{fileId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<byte[]> getFile(@PathVariable Long fileId) throws ResourceNotFoundException {
        File file = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }
}

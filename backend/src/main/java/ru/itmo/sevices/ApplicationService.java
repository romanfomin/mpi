package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.*;
import ru.itmo.repositories.ApplicationRepository;

import java.util.Date;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationStateService applicationStateService;

    @Autowired
    private ApplicationTypeService applicationTypeService;

    public List<Application> getApplications(EApplicationType type) {
        return applicationRepository.findApplicationsByType_Name(type);
    }

    public Application getById(Long id) throws ResourceNotFoundException {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application with id=" + id + " not found"));
    }

    public Application update(Application application) {
        return applicationRepository.save(application);
    }

    public Application saveApplication(Application application,
                                       EApplicationType type,
                                       List<File> files) throws ResourceNotFoundException {
        ApplicationState applicationState = applicationStateService.getApplicationState(EApplicationState.STATE_NEW);
        ApplicationType applicationType = applicationTypeService.getApplicationType(type);
        application.setState(applicationState);
        application.setType(applicationType);
        application.setFiles(files);
        application.setAppDate(new Date());
        return applicationRepository.save(application);
    }
}

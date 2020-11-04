package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.Application;
import ru.itmo.repositories.ApplicationRepository;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getApplications() {
        return applicationRepository.findAll();
    }

    public Application getById(Long id) throws ResourceNotFoundException {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application with id=" + id + " not found"));
    }

    public Application save(Application application) {
        return applicationRepository.save(application);
    }
}

package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.ApplicationType;
import ru.itmo.models.EApplicationType;
import ru.itmo.repositories.ApplicationTypeRepository;

import java.util.List;

@Service
public class ApplicationTypeService {

    @Autowired
    private ApplicationTypeRepository applicationTypeRepository;

    public List<ApplicationType> getApplicationTypes() {
        return applicationTypeRepository.findAll();
    }

    public ApplicationType getApplicationType(EApplicationType type) throws ResourceNotFoundException {
        return applicationTypeRepository.findByName(type)
                .orElseThrow(() -> new ResourceNotFoundException("Error: ApplicationType is not found."));
    }
}

package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.models.ApplicationState;
import ru.itmo.models.EApplicationState;
import ru.itmo.models.Role;
import ru.itmo.repositories.ApplicationStateRepository;
import ru.itmo.repositories.RoleRepository;

import java.util.List;

@Service
public class ApplicationStateService {

    @Autowired
    private ApplicationStateRepository applicationStateRepository;

    public List<ApplicationState> getApplicationStates() {
        return applicationStateRepository.findAll();
    }

    public ApplicationState getApplicationState(EApplicationState state) {
        return applicationStateRepository.findByName(state)
                .orElseThrow(() -> new RuntimeException("Error: ApplicationState is not found."));
    }
}

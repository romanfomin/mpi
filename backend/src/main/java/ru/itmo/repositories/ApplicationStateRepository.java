package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.models.ApplicationState;
import ru.itmo.models.EApplicationState;
import ru.itmo.models.ERole;
import ru.itmo.models.Role;

import java.util.Optional;

@Repository
public interface ApplicationStateRepository extends JpaRepository<ApplicationState, Long> {
    Optional<ApplicationState> findByName(EApplicationState name);
}
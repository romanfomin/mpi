package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.models.ApplicationState;
import ru.itmo.models.ApplicationType;
import ru.itmo.models.EApplicationState;
import ru.itmo.models.EApplicationType;

import java.util.Optional;

@Repository
public interface ApplicationTypeRepository extends JpaRepository<ApplicationType, Long> {
    Optional<ApplicationType> findByName(EApplicationType name);
}
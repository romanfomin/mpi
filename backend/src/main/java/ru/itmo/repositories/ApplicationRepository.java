package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.models.Application;
import ru.itmo.models.EApplicationType;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    public List<Application> findApplicationsByType_Name(EApplicationType type);
}
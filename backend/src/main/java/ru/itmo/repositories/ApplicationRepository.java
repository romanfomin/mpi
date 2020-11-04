package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.models.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
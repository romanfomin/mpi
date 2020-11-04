package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.models.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
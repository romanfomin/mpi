package ru.itmo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.models.Script;

import java.util.Date;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    Script findByAirDate(Date date);
}
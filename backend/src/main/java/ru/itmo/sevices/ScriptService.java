package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.Script;
import ru.itmo.repositories.ScriptRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    public List<Script> getScripts() {
        return scriptRepository.findAll();
    }

    public Script getById(Long id) throws ResourceNotFoundException {
        Optional<Script> script = scriptRepository.findById(id);
        if (!script.isPresent()) {
            throw new ResourceNotFoundException("Script with id=" + id + " not found");
        }
        return script.get();
    }

    public Script getByDate(Date airDate) throws ResourceNotFoundException {
        Script script = scriptRepository.findByAirDate(airDate);
        if (script == null) {
            throw new ResourceNotFoundException("Script with date=" + airDate + " not found");
        }
        return script;
    }

    public Script save(Script script) {
        script.setLastModificationDate(new Date());
        return scriptRepository.save(script);
    }
}

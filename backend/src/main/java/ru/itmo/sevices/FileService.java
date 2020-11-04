package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.File;
import ru.itmo.repositories.FileRepository;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public File save(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileDB = new File(fileName, file.getContentType(), file.getBytes());

        return fileRepository.save(fileDB);
    }

    public File getFile(Long id) throws ResourceNotFoundException {
        return fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Error: File is not found."));
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}

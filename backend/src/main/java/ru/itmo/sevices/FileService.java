package ru.itmo.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.itmo.exceptions.ResourceNotFoundException;
import ru.itmo.models.EFileType;
import ru.itmo.models.File;
import ru.itmo.models.FileType;
import ru.itmo.repositories.FileRepository;
import ru.itmo.repositories.FileTypeRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileTypeRepository fileTypeRepository;

    public List<FileType> getFileTypes() {
        return fileTypeRepository.findAll();
    }

    public List<File> saveFiles(List<MultipartFile> multipartFiles, EFileType fileType) throws IOException, ResourceNotFoundException {
        if (multipartFiles == null) {
            return null;
        }
        List<File> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            files.add(save(multipartFile, getType(fileType)));
        }
        return files;
    }

    public File save(MultipartFile file, FileType fileType) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileDB = new File(fileName, file.getContentType(), fileType, file.getBytes());

        return fileRepository.save(fileDB);
    }

    public File getFile(Long id) throws ResourceNotFoundException {
        return fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Error: File is not found."));
    }

    public FileType getType(EFileType fileType) throws ResourceNotFoundException {
        return fileTypeRepository.findByName(fileType)
                .orElseThrow(() -> new ResourceNotFoundException("Error: FileType is not found."));
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}

package de.phoenix.wgtest.services;

import de.phoenix.wgtest.model.management.FileDB;
import de.phoenix.wgtest.repository.management.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file, Long childId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), childId);
        return fileDBRepository.save(fileDB);
    }

    public FileDB update(MultipartFile file, Long childId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB;
        Optional<FileDB> opt = fileDBRepository.findByChildId(childId);
        if (opt.isPresent()) {
            fileDB = opt.get();
            fileDB.setName(fileName);
            fileDB.setType(file.getContentType());
            fileDB.setData(file.getBytes());
        } else {
            fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(), childId);
        }
        return fileDBRepository.save(fileDB);
    }

    public FileDB getFile(Long childId) {
        Optional<FileDB> opt = fileDBRepository.findByChildId(childId);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public void deleteFile(Long childId) {
        Optional<FileDB> opt = fileDBRepository.findByChildId(childId);
        opt.ifPresent(fileDB -> fileDBRepository.delete(fileDB));
    }
}

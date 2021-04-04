package sviatoslav_slivinskyi_project_2.spring_application.service;

import sviatoslav_slivinskyi_project_2.spring_application.model.File;

import java.util.List;
import java.util.Optional;

public interface FileService {

    List<File> findAllFiles(String username);

    Optional<File> getOptionalFile(Long fileId);

    File getFile(Long fileId);

    File uploadFile(File file);

    void deleteFile(Long fileId);

}

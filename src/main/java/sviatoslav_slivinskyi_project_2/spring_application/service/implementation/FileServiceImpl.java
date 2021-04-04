package sviatoslav_slivinskyi_project_2.spring_application.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sviatoslav_slivinskyi_project_2.spring_application.model.File;
import sviatoslav_slivinskyi_project_2.spring_application.repository.FileRepository;
import sviatoslav_slivinskyi_project_2.spring_application.service.FileService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<File> findAllFiles(String username){
        return fileRepository.getAllByUserUsername(username);
    }

    @Override
    public Optional<File> getOptionalFile(Long fileId){
        return fileRepository.findById(fileId);
    }

    @Override
    public File getFile(Long fileId){
        return fileRepository.getOne(fileId);
    }

    @Override
    public File uploadFile(File file){
        return fileRepository.save(file);
    }

    @Override
    public void deleteFile(Long fileId){
        fileRepository.deleteById(fileId);
    }

}

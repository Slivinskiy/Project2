package sviatoslav_slivinskyi_project_2.spring_application.serviceTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sviatoslav_slivinskyi_project_2.spring_application.model.File;
import sviatoslav_slivinskyi_project_2.spring_application.service.FileService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class fileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    void uploadDownloadDeleteFileTest(){
        File file = new File();
        file.setFileName("Test File");
        file.setContentType("Content Type");

        File file1 = fileService.uploadFile(file);
        assertNotNull(file1);
        assertEquals(file.getFileName(), file1.getFileName());
        assertEquals(file1.getContentType(), "Content Type");
        assertTrue(file1.getFileId() > 0);

        File file2 = fileService.getFile(file1.getFileId());
        assertNotNull(file2);
        assertTrue(file2.getFileId() > 0);
        assertEquals(file2.getContentType(), file1.getContentType());
        assertEquals(file2.getFileName(), "Test File");

        Long fileId = file2.getFileId();
        fileService.deleteFile(fileId);
        assertEquals(fileService.getOptionalFile(fileId), Optional.empty());
    }

}

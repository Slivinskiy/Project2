package sviatoslav_slivinskyi_project_2.spring_application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sviatoslav_slivinskyi_project_2.spring_application.dto.FileDTO;
import sviatoslav_slivinskyi_project_2.spring_application.model.File;
import sviatoslav_slivinskyi_project_2.spring_application.service.FileService;
import sviatoslav_slivinskyi_project_2.spring_application.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    private static final int MaxUploadFileSize = 105242880;

    @GetMapping
    public String viewFiles(Authentication authentication, Model model){
        String username = authentication.getName();
        List<FileDTO> files = convertFileListToFileDTO(fileService.findAllFiles(username));
        model.addAttribute("files", files);
        return "files";
    }

    @GetMapping("/deleteFile/{fileId}")
    public String deleteFile(@ModelAttribute("file") FileDTO fileDTO, Model model){
        fileService.deleteFile(fileDTO.getFileId());
        if (fileService.getOptionalFile(fileDTO.getFileId()).isPresent()) {
            model.addAttribute("ErrorDeleteFile", true);
            LOGGER.error("An error occurs during deleting the file");
        } else {
            model.addAttribute("SuccessDeleteFile", true);
            LOGGER.info("The file was deleted");
        }
        return "result";
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity <byte[]> downloadFile(@PathVariable Long fileId){
        FileDTO fileDTO = convertFileToFileDTO(fileService.getFile(fileId));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDTO.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getFileName() + "\"")
                .body(fileDTO.getFileData());
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, @ModelAttribute("file") FileDTO fileDTO, Model model, Authentication authentication) throws IOException {

        if (fileUpload.getSize() > MaxUploadFileSize){
            model.addAttribute("FileTooLong", true);
        } else {
            String username = authentication.getName();
            fileDTO.setUser(userService.getUserByName(username));
            fileDTO.setFileData(fileUpload.getBytes());
            fileDTO.setFileName(fileUpload.getOriginalFilename());
            fileDTO.setFileSize(String.valueOf(fileUpload.getSize()));
            fileDTO.setContentType(fileUpload.getContentType());
            if (fileService.uploadFile(convertFileDTOToFile(fileDTO)) == null) {
                model.addAttribute("ErrorUploadFile", true);
                LOGGER.error("An error occurs during uploading the note");
            } else {
                model.addAttribute("SuccessUploadFile", true);
                LOGGER.info("The file was uploaded");
            }
        }
        return "result";
    }

    private FileDTO convertFileToFileDTO(File file){
        FileDTO fileDTO = new FileDTO();
        BeanUtils.copyProperties(file, fileDTO);
        return fileDTO;
    }

    private File convertFileDTOToFile(FileDTO fileDTO){
        File file = new File();
        BeanUtils.copyProperties(fileDTO, file);
        return file;
    }

    private List<FileDTO> convertFileListToFileDTO(List<File> files){
        List<FileDTO> fileDTOS = new ArrayList<>();
        files.forEach(file -> fileDTOS.add(convertFileToFileDTO(file)));
        return fileDTOS;
    }

}

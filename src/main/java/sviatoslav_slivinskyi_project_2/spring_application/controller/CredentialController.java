package sviatoslav_slivinskyi_project_2.spring_application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sviatoslav_slivinskyi_project_2.spring_application.dto.CredentialDTO;
import sviatoslav_slivinskyi_project_2.spring_application.model.Credential;
import sviatoslav_slivinskyi_project_2.spring_application.service.CredentialService;
import sviatoslav_slivinskyi_project_2.spring_application.service.EncryptionService;
import sviatoslav_slivinskyi_project_2.spring_application.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/credentials")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private UserService userService;

    private static final int MIN_SQL_RESULT = 0;

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialController.class);

    @GetMapping
    public String viewCredentials(Model model, Authentication authentication){
        String username = authentication.getName();
        List<CredentialDTO> credentials = convertCredentialListToCredentialDTO(credentialService.getAllUserCredentials(username));
        model.addAttribute("credentials", credentials);
        return "credentials";
    }

    @GetMapping("/newCredentials")
    public String showCredentialsForm(@ModelAttribute("credentials") CredentialDTO credentialDTO){
        return "credentials-form";
    }

    @GetMapping("/updateCredentials/{credentialId}")
    public String updateCredentialsForm(@ModelAttribute("credential") CredentialDTO credentialDTO, Model model){
        credentialDTO = convertCredentialToCredentialDTO(credentialService.getCredentialsById(credentialDTO.getCredentialId()));
        String decryptedPassword = encryptionService.decryptValue(credentialDTO.getPassword(), credentialDTO.getSecretKey());
        credentialDTO.setPassword(decryptedPassword);
        model.addAttribute("credentialsId",credentialDTO.getCredentialId());
        model.addAttribute("credentials", credentialDTO);
        return "credentials-form";
    }

    @GetMapping("/deleteCredentials/{credentialId}")
    public String deleteCredentials(@ModelAttribute("credentials") CredentialDTO credentialDTO, Model model){
        credentialService.deleteCredentials(credentialDTO.getCredentialId());
        if (credentialService.getOptionalCredentials(credentialDTO.getCredentialId()).isPresent()){
            model.addAttribute("ErrorDeleteCredentials", true);
            LOGGER.error("An error occurs during deleting credentials");
        } else {
            model.addAttribute("SuccessDeleteCredentials", true);
            LOGGER.info("Credentials were deleted");
        }
        return "result";
    }

    @PostMapping("/updateCredentials/{credentialId}")
    public String updateCredentials(@ModelAttribute("credentials") CredentialDTO credentialDTO, Model model){
        CredentialDTO credentialDTO1 = convertCredentialToCredentialDTO(credentialService.getCredentialsById(credentialDTO.getCredentialId()));
        String encryptedPassword = encryptionService.encryptValue(credentialDTO.getPassword(), credentialDTO1.getSecretKey());
        credentialDTO.setPassword(encryptedPassword);
        int result = credentialService.updateCredentials(convertCredentialDTOToCredential(credentialDTO));
        if (result < MIN_SQL_RESULT){
            model.addAttribute("ErrorUpdateCredentials", true);
            LOGGER.error("An error occurs during updating credentials");
        } else {
            model.addAttribute("SuccessUpdateCredentials", true);
            LOGGER.info("Credentials were updated");
        }
        return "result";
    }

    @PostMapping("/newCredentials")
    public String addCredentials(@ModelAttribute("credentials") CredentialDTO credentialDTO, Model model, Authentication authentication){
        String username = authentication.getName();
        credentialDTO.setUser(userService.getUserByName(username));
        CredentialDTO savedCredential = convertCredentialToCredentialDTO(credentialService.saveCredentials(convertCredentialDTOToCredential(credentialDTO)));

        if(savedCredential.getCredentialId() > MIN_SQL_RESULT){
            model.addAttribute("credentialsSuccess", true);
            LOGGER.info("Credentials were saved");
        } else {
            model.addAttribute("credentialsError", true);
            LOGGER.error("An error occurs during deleting credentials");
        }
        return "result";
    }

    private CredentialDTO convertCredentialToCredentialDTO(Credential credential){
        CredentialDTO credentialDTO = new CredentialDTO();
        BeanUtils.copyProperties(credential, credentialDTO);
        return credentialDTO;
    }

    private Credential convertCredentialDTOToCredential(CredentialDTO credentialDTO){
        Credential credential = new Credential();
        BeanUtils.copyProperties(credentialDTO, credential);
        return credential;
    }

    private List<CredentialDTO> convertCredentialListToCredentialDTO(List<Credential> credentials){
        List<CredentialDTO> credentialDTOS = new ArrayList<>();
        credentials.forEach(credential -> credentialDTOS.add(convertCredentialToCredentialDTO(credential)));
        return credentialDTOS;
    }

}

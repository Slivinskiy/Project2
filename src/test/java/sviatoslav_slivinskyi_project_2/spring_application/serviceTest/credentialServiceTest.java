package sviatoslav_slivinskyi_project_2.spring_application.serviceTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sviatoslav_slivinskyi_project_2.spring_application.model.Credential;
import sviatoslav_slivinskyi_project_2.spring_application.service.CredentialService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class credentialServiceTest {

    @Autowired
    private CredentialService credentialService;

    @Test
    void saveGetUpdateDeleteCredentials(){
        Credential credential = new Credential();
        credential.setUrl("youtube.com");
        credential.setUsername("test");
        credential.setPassword("test1");

        Credential credential1 = credentialService.saveCredentials(credential);
        assertNotEquals(credential.getPassword(), credential1.getPassword());
        assertNotNull(credential1);
        assertTrue(credential1.getCredentialId() > 0);
        assertEquals(credential1.getUrl(), credential.getUrl());
        assertEquals(credential1.getUsername(), "test");

        Credential credential2 = credentialService.getCredentialsById(credential1.getCredentialId());
        assertNotNull(credential2);
        assertTrue(credential2.getCredentialId() > 0);
        assertEquals(credential1.getUsername(), credential2.getUsername());

        credential2.setUsername("updated Username");
        int result = credentialService.updateCredentials(credential2);
        assertTrue(result > 0);
        assertNotEquals(credential1.getUsername(), credential2.getUsername());

        Long credentialsId = credential2.getCredentialId();
        credentialService.deleteCredentials(credentialsId);
        assertEquals(credentialService.getOptionalCredentials(credentialsId), Optional.empty());
    }
}

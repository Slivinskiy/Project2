package sviatoslav_slivinskyi_project_2.spring_application.service;

import sviatoslav_slivinskyi_project_2.spring_application.model.Credential;

import java.util.List;
import java.util.Optional;

public interface CredentialService {

    List<Credential> getAllUserCredentials(String username);

    Credential saveCredentials(Credential credential);

    void deleteCredentials(Long credentialId);

    Optional<Credential> getOptionalCredentials(Long credentialId);

    Credential getCredentialsById(Long credentialId);

    int updateCredentials(Credential credential);

}

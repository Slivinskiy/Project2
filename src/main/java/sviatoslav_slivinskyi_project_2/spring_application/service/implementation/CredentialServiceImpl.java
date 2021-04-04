package sviatoslav_slivinskyi_project_2.spring_application.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sviatoslav_slivinskyi_project_2.spring_application.model.Credential;
import sviatoslav_slivinskyi_project_2.spring_application.repository.CredentialRepository;
import sviatoslav_slivinskyi_project_2.spring_application.service.CredentialService;
import sviatoslav_slivinskyi_project_2.spring_application.service.EncryptionService;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private EncryptionService encryptionService;

    @Override
    public List<Credential> getAllUserCredentials(String username){
        return credentialRepository.getAllByUserUsername(username);
    }

    @Override
    public Credential saveCredentials(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        return credentialRepository.save(new Credential(credential.getUrl(), credential.getUsername(), encryptedPassword, encodedKey, credential.getUser()));
    }

    @Override
    public void deleteCredentials(Long credentialId){
        credentialRepository.deleteById(credentialId);
    }

    @Override
    public Optional<Credential> getOptionalCredentials(Long credentialId){
        return credentialRepository.findById(credentialId);
    }

    @Override
    public Credential getCredentialsById(Long credentialId){
        return credentialRepository.getOne(credentialId);
    }

    @Override
    public int updateCredentials(Credential credential){
        return credentialRepository.updateCredentials(credential.getUrl(), credential.getUsername(), credential.getPassword(), credential.getCredentialId());
    }
}

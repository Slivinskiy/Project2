package sviatoslav_slivinskyi_project_2.spring_application.service;

public interface EncryptionService {

    String encryptValue(String data, String key);

    String decryptValue(String data, String key);
}

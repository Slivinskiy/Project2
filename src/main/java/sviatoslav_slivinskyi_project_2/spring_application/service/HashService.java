package sviatoslav_slivinskyi_project_2.spring_application.service;

public interface HashService {
    String getHashedValue(String data, String salt);
}

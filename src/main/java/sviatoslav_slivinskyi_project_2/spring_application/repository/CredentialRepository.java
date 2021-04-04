package sviatoslav_slivinskyi_project_2.spring_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sviatoslav_slivinskyi_project_2.spring_application.model.Credential;

import java.util.List;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    List<Credential> getAllByUserUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Credential c set c.url= ?1, c.username= ?2, c.password= ?3 WHERE c.credentialId= ?4")
    int updateCredentials(String url, String username, String password, Long credentialId);
}

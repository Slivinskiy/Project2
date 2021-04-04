package sviatoslav_slivinskyi_project_2.spring_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sviatoslav_slivinskyi_project_2.spring_application.model.File;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> getAllByUserUsername(String username);
}

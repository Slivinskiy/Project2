package sviatoslav_slivinskyi_project_2.spring_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sviatoslav_slivinskyi_project_2.spring_application.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByUsername(String username);

}

package sviatoslav_slivinskyi_project_2.spring_application.service;

import sviatoslav_slivinskyi_project_2.spring_application.model.User;

public interface UserService {

    User insertUser(User user);

    User getUserByName(String username);
}

package sviatoslav_slivinskyi_project_2.spring_application.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sviatoslav_slivinskyi_project_2.spring_application.model.User;
import sviatoslav_slivinskyi_project_2.spring_application.repository.UserRepository;
import java.security.SecureRandom;
import java.util.Base64;
import sviatoslav_slivinskyi_project_2.spring_application.service.HashService;
import sviatoslav_slivinskyi_project_2.spring_application.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private HashService hashService;
    @Autowired
    private UserRepository userRepository;


    @Override
    public User insertUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userRepository.save(new User(user.getUsername(), hashedPassword, user.getFirstName(), user.getLastName(), encodedSalt));
    }

    @Override
    public User getUserByName(String username){
        return userRepository.getUserByUsername(username);
    }
}

package sviatoslav_slivinskyi_project_2.spring_application.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sviatoslav_slivinskyi_project_2.spring_application.dto.UserDTO;
import sviatoslav_slivinskyi_project_2.spring_application.model.User;
import sviatoslav_slivinskyi_project_2.spring_application.service.UserService;


@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LogManager.getLogger(SignUpController.class);

    private static final int MIN_SQL_RESULT = 0;

    @GetMapping
    public String signUpView(UserDTO userDTO, Model model){
        model.addAttribute("user", userDTO);
        return "signup";
    }

    @PostMapping
    public String signUpUser(@ModelAttribute("user") UserDTO userDTO, Model model){
        String signUpError = null;

        if (userService.getUserByName(userDTO.getUsername()) != null){
            signUpError = "Sorry, but this username already exists, choose another one.";
            LOGGER.info("username already exists");
        }
        if (userDTO.getFirstName().trim().isEmpty() || userDTO.getLastName().trim().isEmpty() || userDTO.getUsername().trim().isEmpty()){
            signUpError = "There can`t be empty fields";
            model.addAttribute("signUpError", signUpError);
            LOGGER.info("Trying to process empty value");
            return "signup";
        }
        if (userDTO.getPassword().length() < 7){
            signUpError = "Password should contain at least 7 characters";
            LOGGER.info("password too small");
        }
        if (signUpError == null){
            UserDTO savedUser = convertUserToUserDTO(userService.insertUser(convertUserDTOToUser(userDTO)));
            if (savedUser.getUserId() < MIN_SQL_RESULT){
                signUpError = "There was an error signing you up. Please try again.";
                LOGGER.error("Problem with SignUp");
            }
        }
        if (signUpError == null){
            model.addAttribute("signUpSuccess", true);
            LOGGER.info("Successful SignUp");
            return "login";
        } else {
            model.addAttribute("signUpError", signUpError);
            LOGGER.error("Problem with SignUp");
        }
        return "signup";
    }

    private UserDTO convertUserToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    private User convertUserDTOToUser(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}

package sviatoslav_slivinskyi_project_2.spring_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sviatoslav_slivinskyi_project_2.spring_application.dto.UserDTO;


@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String viewLogin(@ModelAttribute("user") UserDTO userDTO) {
        return "login";
    }

    @PostMapping
    public String loginUser(@ModelAttribute("user") UserDTO userDTO) {
        return "login";
    }
}

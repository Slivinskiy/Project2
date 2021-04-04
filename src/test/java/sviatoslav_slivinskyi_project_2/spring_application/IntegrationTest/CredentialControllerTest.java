package sviatoslav_slivinskyi_project_2.spring_application.IntegrationTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "k", password = "1111111")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void addCredentialsTest() throws Exception{
        mockMvc.perform(post("/credentials/newCredentials").param("url", "test.com").param("username", "sli").param("password", "1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("credentialsSuccess", true));
    }

    @Test
    @Order(2)
    public void viewCredentialsPageTest() throws Exception{
        mockMvc.perform(get("/credentials"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(3)
    public void editCredentialsTest() throws Exception{
        mockMvc.perform(post("/credentials/updateCredentials/1?").param("password", "2"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessUpdateCredentials", true));
    }

    @Test
    @Order(4)
    public void removeCredentialsTest() throws Exception{
        mockMvc.perform(get("/credentials/deleteCredentials/1?"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessDeleteCredentials", true));
    }

}

package sviatoslav_slivinskyi_project_2.spring_application.IntegrationTest;

import org.junit.jupiter.api.Test;
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
public class CredentialControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void viewCredentialsPageTest() throws Exception{
        mockMvc.perform(get("/credentials"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addCredentialsTest() throws Exception{
       mockMvc.perform(post("/credentials/newCredentials").param("url", "test.com").param("username", "sli").param("password", "1"))
               .andDo(print())
               .andExpect(authenticated())
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attribute("credentialsSuccess", true));
    }

    @Test
    public void updateCredentialsTest() throws Exception{
        mockMvc.perform(post("/credentials/updateCredentials/55?").param("password", "2"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessUpdateCredentials", true));
    }

    @Test
    public void deleteCredentialsTest() throws Exception{
        mockMvc.perform(get("/credentials/deleteCredentials/42?"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessDeleteCredentials", true));
    }

}

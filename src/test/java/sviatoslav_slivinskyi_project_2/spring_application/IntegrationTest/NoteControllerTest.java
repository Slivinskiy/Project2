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
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void addNoteTest() throws Exception{
        this.mockMvc.perform(post("/notes/newNote").param("noteTitle", "Test note").param("noteDescription","test description"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessSaveNote",true));
    }

    @Test
    @Order(2)
    public void viewNotesPageTest() throws Exception{
        this.mockMvc.perform(get("/notes"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    @Order(3)
    public void editNoteTest() throws Exception{
        this.mockMvc.perform(post("/notes/updateNote/1?").param("noteTitle", "Updated note"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessUpdateNote", true));
    }

    @Test
    @Order(4)
    public void removeNoteTest() throws Exception{
        this.mockMvc.perform(get("/notes/deleteNote/1?"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessDelete", true));
    }

}

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
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void viewNotesPageTest() throws Exception{
        this.mockMvc.perform(get("/notes"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addNoteTest() throws Exception{
        this.mockMvc.perform(post("/notes/newNote").param("noteTitle", "Test note").param("noteDescription","test description"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessSaveNote",true));
    }

    @Test
    public void updateNoteTest() throws Exception{
        this.mockMvc.perform(post("/notes/updateNote/50?").param("noteTitle", "Updated note"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessUpdateNote", true));
    }

    @Test
    public void deleteNoteTest() throws Exception{
        this.mockMvc.perform(get("/notes/deleteNote/38?"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessDelete", true));
    }

}

package sviatoslav_slivinskyi_project_2.spring_application.IntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "k", password = "1111111")
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void viewFilePageTest() throws Exception {
        this.mockMvc.perform(get("/files"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void uploadFileTest() throws Exception {
        this.mockMvc.perform(multipart("/files/uploadFile").file("fileUpload", "123".getBytes()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("SuccessUploadFile", true));
    }

    @Test
    public void downloadFileTest() throws Exception {
        this.mockMvc.perform(get("/files/downloadFile/59?"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void deleteFileTest() throws Exception {
       this.mockMvc.perform(get("/files/deleteFile/56?"))
               .andDo(print())
               .andExpect(status().is2xxSuccessful())
               .andExpect(model().attribute("SuccessDeleteFile", true));
    }

}

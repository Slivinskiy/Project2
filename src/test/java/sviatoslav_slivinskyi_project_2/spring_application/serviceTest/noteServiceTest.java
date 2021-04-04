package sviatoslav_slivinskyi_project_2.spring_application.serviceTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sviatoslav_slivinskyi_project_2.spring_application.model.Note;
import sviatoslav_slivinskyi_project_2.spring_application.service.NoteService;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class noteServiceTest {

    @Autowired
    private NoteService noteService;

    @Test
    void saveGetUpdateDeleteNoteTest(){
        Note note = new Note();
        note.setNoteTitle("Note for testing");
        note.setNoteDescription("Hope that all tests will past");

        Note note1 = noteService.saveNote(note);
        assertNotNull(note1);
        assertTrue(note1.getNoteId() > 0);
        assertEquals(note1.getNoteTitle(), "Note for testing");
        assertEquals(note1.getNoteDescription(), note.getNoteDescription());

        Note note2 = noteService.getNote(note1.getNoteId());
        assertNotNull(note2);
        assertEquals(note2.getNoteTitle(), note1.getNoteTitle());

        note2.setNoteTitle("note to update");
        int updateResult = noteService.updateNote(note2);
        assertTrue(updateResult > 0);
        assertNotEquals(note2.getNoteTitle(), note1.getNoteTitle());

        Long noteId = note2.getNoteId();
        noteService.deleteNote(noteId);
        assertEquals(noteService.getOptionalNote(noteId), Optional.empty());
    }
}

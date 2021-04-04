package sviatoslav_slivinskyi_project_2.spring_application.service;

import sviatoslav_slivinskyi_project_2.spring_application.model.Note;
import java.util.List;
import java.util.Optional;

public interface NoteService {

    List<Note> getAllNotes(String username);

    Note saveNote(Note note);

    void deleteNote(Long noteId);

    Note getNote(Long noteId);

    Optional<Note> getOptionalNote(Long noteId);

    int updateNote(Note n);
}

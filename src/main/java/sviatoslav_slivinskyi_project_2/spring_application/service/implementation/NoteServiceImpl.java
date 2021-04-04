package sviatoslav_slivinskyi_project_2.spring_application.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sviatoslav_slivinskyi_project_2.spring_application.model.Note;
import sviatoslav_slivinskyi_project_2.spring_application.repository.NoteRepository;
import sviatoslav_slivinskyi_project_2.spring_application.service.NoteService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<Note> getAllNotes(String username){
        return noteRepository.getAllByUserUsername(username);
    }

    @Override
    public Note saveNote(Note note){
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long noteId){
        noteRepository.deleteById(noteId);
    }

    @Override
    public Note getNote(Long noteId){
        return noteRepository.getOne(noteId);
    }

    @Override
    public Optional<Note> getOptionalNote(Long noteId){
        return noteRepository.findById(noteId);
    }

    @Override
    public int updateNote(Note n) {
        return noteRepository.updateNote(n.getNoteTitle(), n.getNoteDescription(), n.getNoteId());
    }
}

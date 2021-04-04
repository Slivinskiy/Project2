package sviatoslav_slivinskyi_project_2.spring_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sviatoslav_slivinskyi_project_2.spring_application.model.Note;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> getAllByUserUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Note n set n.noteTitle= ?1, n.noteDescription= ?2 WHERE n.noteId= ?3")
    int updateNote(String noteTitle, String noteDescription, Long noteId);
}

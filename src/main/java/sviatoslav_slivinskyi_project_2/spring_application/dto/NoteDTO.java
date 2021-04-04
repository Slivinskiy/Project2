package sviatoslav_slivinskyi_project_2.spring_application.dto;

import sviatoslav_slivinskyi_project_2.spring_application.model.User;

public class NoteDTO {
    private Long noteId;
    private String noteTitle;
    private String noteDescription;
    private User user;

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package sviatoslav_slivinskyi_project_2.spring_application.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sviatoslav_slivinskyi_project_2.spring_application.dto.NoteDTO;
import sviatoslav_slivinskyi_project_2.spring_application.model.Note;
import sviatoslav_slivinskyi_project_2.spring_application.model.User;
import sviatoslav_slivinskyi_project_2.spring_application.service.NoteService;
import sviatoslav_slivinskyi_project_2.spring_application.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    private static final int MIN_SQL_RESULT = 0;

    private static final Logger LOGGER = LogManager.getLogger(NoteController.class);

    @GetMapping
    public String viewNotes( Model model, Authentication authentication){
        String username = authentication.getName();
        List<NoteDTO> notes = convertNoteListToNoteDTO(noteService.getAllNotes(username));
        model.addAttribute("notes", notes);
        return "notes";
    }

    @GetMapping("/newNote")
    public String newNoteForm(@ModelAttribute("note") NoteDTO noteDTO){
        return "note-form";
    }

    @GetMapping("/updateNote/{noteId}")
    public String updateNoteForm(@ModelAttribute("note") NoteDTO noteDTO, Model model){
        noteDTO = convertNoteToNoteDTO(noteService.getNote(noteDTO.getNoteId()));
        model.addAttribute("noteid", noteDTO.getNoteId());
        model.addAttribute("note", noteDTO);
        return "note-form";
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@ModelAttribute("note") NoteDTO noteDTO, Model model){
        noteService.deleteNote(noteDTO.getNoteId());
        if (!noteService.getOptionalNote(noteDTO.getNoteId()).isPresent()){
            model.addAttribute("SuccessDelete", true);
            LOGGER.info("Note successfully deleted");
        } else {
            model.addAttribute("ErrorDelete", true);
            LOGGER.error("Note wasn`t deleted");
        }
        return "result";
    }

    @PostMapping("/newNote")
    public String addNote(@ModelAttribute("note") NoteDTO noteDTO, Authentication authentication, Model model) {
        String username = authentication.getName();
        noteDTO.setUser(userService.getUserByName(username));
        NoteDTO savedNote = convertNoteToNoteDTO(noteService.saveNote(convertNoteDTOToNote(noteDTO)));

        if (savedNote.getNoteId() > MIN_SQL_RESULT ) {
            model.addAttribute("SuccessSaveNote", true);
            LOGGER.info("Note was saved");
        } else {
            model.addAttribute("ErrorSaveNote", true);
            LOGGER.error("An error occurs during saving the note");
        }
        return "result";
    }

    @PostMapping("/updateNote/{noteId}")
    public String updateNote(@ModelAttribute("note") NoteDTO noteDTO, Model model){
        int result = noteService.updateNote(convertNoteDTOToNote(noteDTO));
        if (result < MIN_SQL_RESULT){
           model.addAttribute("ErrorUpdateNote", true);
           LOGGER.error("An error occurs during updating the note");
        } else {
            model.addAttribute("SuccessUpdateNote", true);
            LOGGER.info("Note was updated");
        }
        return "result";
    }

    private NoteDTO convertNoteToNoteDTO(Note note){
        NoteDTO noteDTO = new NoteDTO();
        BeanUtils.copyProperties(note, noteDTO);
        return noteDTO;
    }

    private Note convertNoteDTOToNote(NoteDTO noteDTO){
        Note note = new Note();
        BeanUtils.copyProperties(noteDTO, note);
        return note;
    }

    private List<NoteDTO> convertNoteListToNoteDTO(List<Note> notes){
        List<NoteDTO> noteDTOs = new ArrayList<>();
        notes.forEach(note -> noteDTOs.add(convertNoteToNoteDTO(note)));
        return noteDTOs;
    }

}

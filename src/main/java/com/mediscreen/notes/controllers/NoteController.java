package com.mediscreen.notes.controllers;
import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.exception.PatientNotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.services.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/note")
@Slf4j
public class NoteController {


    @Autowired
    private NoteService noteService;

    @Operation(
            description = "Create a note endPoint"
    )

    @PostMapping
    public Note createNote(@Valid @RequestBody Note note) throws PatientNotFoundException {
        log.info("note créer avec succée: {}", note);
        return noteService.createNote(note);
    }

    @Operation(
            description = "get the list of notes endPoint"
    )

    @GetMapping
    public List<Note> getNotes() {
        log.info("la list des notes trouvée");
        return noteService.getNotes();
    }

    @Operation(
            description = "get note by Id endPoint"
    )

    @GetMapping("/{id}")
    public Note getNote(@PathVariable String id) throws NoteNotFoundException {
        log.info("la note est trouvée via son :{}", id);
        return noteService.getById(id);
    }

    @Operation(
            description = "get the list of notes via a patientId endPoint"
    )
    @GetMapping("/patient/{patientId}")
    public List<Note> getNotesByPatientId(@PathVariable String patientId) {
        log.info("Chercher les notes via le : {}", patientId);
        return noteService.getNotesByPatientId(patientId);
    }

    @Operation(
            description = "update a note via the id endPoint"
    )
    @PutMapping("/update/{id}")
    public Note updateNote(@Valid @PathVariable String id, @RequestBody Note note) throws NoteNotFoundException, PatientNotFoundException {

        log.info("Mise à jour de la note via le : {}", id);
        return noteService.updateNote(id, note);
    }

    @Operation(
            description = "delete the note via it's id endPoint"
    )

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable String id) {
        log.info("la note est supprimer via le :{}", id);
        return noteService.deleteNote(id);
    }

    @Operation(
            description = "delete the note via the patientId endPoint"
    )

    @DeleteMapping("/patient/{patientId}")
    public void deleteNoteByPatientId(@PathVariable Integer patientId) {
        log.info("la note est supprimer avec son patient via :{}", patientId);
         noteService.deleteNoteByPatientId(patientId);
    }
}

package com.mediscreen.notes.controllers;

import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.services.NoteService;
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

    @PostMapping
    public Note createNote(@Valid @RequestBody Note note) {
        log.info("note créer avec succée: {}", note);
        return noteService.createNote(note);
    }

    @GetMapping
    public List<Note> getNotes() {
        return noteService.getNotes();
    }

    @GetMapping("/{id}")
    public Note getNote(@PathVariable String id) throws NoteNotFoundException {
        return noteService.getById(id);
    }


    @GetMapping("/patient/{patientId}")
    public List<Note> getNotesByPatientId(@PathVariable String patientId) {
        return noteService.getNotesByPatientId(patientId);
    }


    @PostMapping("/update/{id}")
    public Note updateNote(@Valid @PathVariable String id, @RequestBody Note note) throws NoteNotFoundException {
        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public String deleteNote(@PathVariable String id) {
        return noteService.deleteNote(id);
    }

    @DeleteMapping("/patient/{patientId}")
    public void deleteNoteByPatientId(@PathVariable Integer patientId) {
         noteService.deleteNoteByPatientId(patientId);
    }
}

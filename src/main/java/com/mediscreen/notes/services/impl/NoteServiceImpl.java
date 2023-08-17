package com.mediscreen.notes.services.impl;
import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.repositories.NoteRepository;
import com.mediscreen.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class NoteServiceImpl implements NoteService {


    @Autowired
    private NoteRepository noteRepository;


    public Note createNote(Note note) {
        note.setDate(LocalDate.now());
        note.setId(UUID.randomUUID().toString().substring(0, 5));
        return noteRepository.save(note);
    }

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    public Note getById(String id) throws NoteNotFoundException {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note non trouvé avec l'ID : " + id));

    }


    public Note updateNote(String id, Note noteDetails) throws NoteNotFoundException {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setPatientId(noteDetails.getPatientId());
                    note.setDate(noteDetails.getDate());
                    note.setContent(noteDetails.getContent());
                    return noteRepository.save(note);

                }).orElseThrow(() -> new NoteNotFoundException("Note non trouvé avec l'ID :  " + id));
    }


    @Override
    public List<Note> getNotesByPatientId(String patientId) {
        return noteRepository.findByPatientId(patientId);
    }

    public String deleteNote(String id) {
        noteRepository.deleteById(id);
        return "Note supprimée";
    }
    @Override
    public void deleteNoteByPatientId(Integer patientId) {
        noteRepository.deleteByPatientId(String.valueOf(patientId));
    }
}
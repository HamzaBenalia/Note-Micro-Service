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

    /**
     * this method is for creating a note on in a DB
     * @param note
     * @return : it returns a saved note
     */


    public Note createNote(Note note) {
        note.setDate(LocalDate.now());
        note.setId(UUID.randomUUID().toString().substring(0, 5));
        return noteRepository.save(note);
    }

    /**
     * This method is for listing all the notes created
     * @return : it returns a list of notes
     */

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }


    /**
     * This method is to retrieve a note via a note id
     * @param id
     * @return it returns a note
     * @throws NoteNotFoundException
     */

    public Note getById(String id) throws NoteNotFoundException {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note non trouvé avec l'ID : " + id));

    }

    /**
     * This methode is for updating a given note via the id
     * @param id
     * @param noteDetails
     * @return
     * @throws NoteNotFoundException
     */


    public Note updateNote(String id, Note noteDetails) throws NoteNotFoundException {
        return noteRepository.findById(id)
                .map(note -> {
                    note.setPatientId(noteDetails.getPatientId());
                    note.setDate(noteDetails.getDate());
                    note.setContent(noteDetails.getContent());
                    return noteRepository.save(note);

                }).orElseThrow(() -> new NoteNotFoundException("Note non trouvé avec l'ID :  " + id));
    }

    /**
     * This method is for getting all the notes via a patient id
     * @param patientId
     * @return
     */

    @Override
    public List<Note> getNotesByPatientId(String patientId) {
        return noteRepository.findByPatientId(patientId);
    }

    /**
     * This method is for deleting a note via an id
     * @param id
     * @return
     */
    @Override
    public String deleteNote(String id) {
        noteRepository.deleteById(id);
        return "Note supprimée";
    }

    /**
     * This method is for deleting a note that is attached to a patient via a patient id
     * @param patientId
     */
    @Override
    public void deleteNoteByPatientId(Integer patientId) {
        noteRepository.deleteByPatientId(String.valueOf(patientId));
    }
}
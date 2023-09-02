package com.mediscreen.notes.services;

import com.mediscreen.notes.client.Patient;
import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.exception.PatientNotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.proxy.PatientClient;
import com.mediscreen.notes.repositories.NoteRepository;
import com.mediscreen.notes.services.impl.NoteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
    @Mock
    private PatientClient patientClient;

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;


    @Test
    public void createNoteTest() throws Exception {
        Patient patient = new Patient(1, "hamza", "ben", "16/02/1988", "Homme", "Toulouse", "0000000000");
        Note note = new Note();
        note.setPatientId("1");
        note.setDate(LocalDate.now());
        note.setContent("content");
        note.setId("1");
        when(noteRepository.save(any(Note.class))).thenReturn(note);
        when(patientClient.getPatient(any())).thenReturn(Optional.of(patient));


        Note result = noteService.createNote(note);

        assertEquals(note, result);
        verify(noteRepository, times(1)).save(note);
    }


    @Test
    void getNotesTest() {
        List<Note> notes = new ArrayList<>();
        when(noteRepository.findAll()).thenReturn(notes);

        List<Note> result = noteService.getNotes();

        assertEquals(notes, result);
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void getByIdTest() throws NoteNotFoundException {
        String id = "idTest";
        Note note = new Note();
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));

        Note result = noteService.getById(id);

        assertEquals(note, result);
        verify(noteRepository, times(1)).findById(id);
    }

    @Test
    public void updateNoteTest() throws Exception {
        Patient patient = new Patient(1, "hamza", "ben", "16/02/1988", "Homme", "Toulouse", "0000000000");
        when(patientClient.getPatient(any())).thenReturn(Optional.of(patient));
        String id = "idTest";
        Note note = new Note();
        note.setPatientId("1");
        note.setDate(LocalDate.now());
        note.setContent("content");
        note.setId("1");
        Note noteDetails = new Note();
        noteDetails.setId("1");
        noteDetails.setContent("new content");
        noteDetails.setDate(LocalDate.now());
        noteDetails.setPatientId("1");
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        when(noteRepository.save(note)).thenReturn(note);

        Note result = noteService.updateNote(id, noteDetails);

        assertEquals(note, result);
        verify(noteRepository, times(1)).findById(id);
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    public void testGetNotesByPatientId() {
        // Given
        String patientId = "patient123";
        List<Note> expectedNotes = Arrays.asList(new Note(), new Note());
        when(noteRepository.findByPatientId(patientId)).thenReturn(expectedNotes);

        List<Note> result = noteService.getNotesByPatientId(patientId);

        assertEquals(expectedNotes, result, "The notes returned by the service should match the ones from the repository.");
        verify(noteRepository).findByPatientId(patientId);
    }


    @Test
    void deleteNoteTest() {
        String id = "idTest";

        doNothing().when(noteRepository).deleteById(id);

        String result = noteService.deleteNote(id);

        assertEquals("Note supprimée", result);
        verify(noteRepository, times(1)).deleteById(id);
    }


    @Test
    public void testDeleteNoteByPatientId() {
        Integer patientId = 1;
        noteService.deleteNoteByPatientId(patientId);
        verify(noteRepository, times(1)).deleteByPatientId(String.valueOf(patientId));
    }


    @Test
    public void testUpdateNoteThrowsPatientNotFoundException() {
        when(patientClient.getPatient(anyInt())).thenReturn(Optional.empty());

        Note noteDetails = new Note();
        noteDetails.setPatientId("123");

        try {
            noteService.updateNote("someId", noteDetails);
            fail("Expected PatientNotFoundException to be thrown");
        } catch (PatientNotFoundException e) {
            assertEquals("patient with id 123 not found", e.getMessage());
        } catch (NoteNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateNoteThrowsPatientNotFoundException() {
        when(patientClient.getPatient(anyInt())).thenReturn(Optional.empty());

        Note newNote = new Note();
        newNote.setPatientId("123");

        try {
            noteService.createNote(newNote);
            fail("Expected PatientNotFoundException to be thrown");
        } catch (PatientNotFoundException e) {
            assertEquals("patient with id 123 not found", e.getMessage());
        }
    }

}

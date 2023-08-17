package com.mediscreen.notes.services;

import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.repositories.NoteRepository;
import com.mediscreen.notes.services.impl.NoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
public class TestNoteService {


    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNoteTest() {
        Note note = new Note(); // Remplissez avec des données appropriées
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note result = noteService.createNote(note);

        assertEquals(note, result);
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    public void testCreateNote() {
        // Given
        Note note = new Note();
        when(noteRepository.save(note)).thenReturn(note);

        // When
        Note result = noteService.createNote(note);

        // Then
        assertEquals(LocalDate.now(), result.getDate(), "The note's date should be set to today.");
        assertEquals(5, result.getId().length(), "The ID should have a length of 5.");
        verify(noteRepository).save(note);
    }


    @Test
    void getNotesTest() {
        List<Note> notes = new ArrayList<>(); // Remplissez avec des données appropriées
        when(noteRepository.findAll()).thenReturn(notes);

        List<Note> result = noteService.getNotes();

        assertEquals(notes, result);
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void getByIdTest() throws NoteNotFoundException {
        String id = "idTest";
        Note note = new Note(); // Remplissez avec des données appropriées
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));

        Note result = noteService.getById(id);

        assertEquals(note, result);
        verify(noteRepository, times(1)).findById(id);
    }

    @Test
    void updateNoteTest() throws NoteNotFoundException {
        String id = "idTest";
        Note noteDetails = new Note(); // Remplissez avec des données appropriées
        Note note = new Note(); // Remplissez avec des données appropriées
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

        // When
        List<Note> result = noteService.getNotesByPatientId(patientId);

        // Then
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

}

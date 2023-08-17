package com.mediscreen.notes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.services.NoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TestNoteController {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void createNoteTest() throws Exception {
        Note note = new Note();
        note.setId("1");
        note.setContent("ceci est la première note");
        note.setPatientId("1");

        when(noteService.createNote(any(Note.class))).thenReturn(note);

        mockMvc.perform(MockMvcRequestBuilders.post("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(note)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(note)));

        verify(noteService, times(1)).createNote(any(Note.class));
    }

    @Test
    void getNotesTest() throws Exception {
        List<Note> notes = new ArrayList<>();
        when(noteService.getNotes()).thenReturn(notes);

        mockMvc.perform(MockMvcRequestBuilders.get("/note")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(notes)));

        verify(noteService, times(1)).getNotes();
    }

    @Test
    void getNoteTest() throws Exception {
        String id = "idTest";
        Note note = new Note(); // Remplissez avec des données appropriées
        when(noteService.getById(id)).thenReturn(note);

        mockMvc.perform(MockMvcRequestBuilders.get("/note/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(note)));

        verify(noteService, times(1)).getById(id);
    }

    @Test
    void getNoteTestThrowsException() throws Exception {
        String id = "someId";
        when(noteService.getById(id)).thenThrow(new NoteNotFoundException("Note not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/note/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Note not found"))); // This assumes that your error message is included in the response. Adjust if your response structure is different.

    }


    @Test
    void updateNoteTest() throws Exception {
        String id = "idTest";
        Note note = new Note(); // Remplissez avec des données appropriées
        when(noteService.updateNote(id, note)).thenReturn(note);

        mockMvc.perform(MockMvcRequestBuilders.post("/note/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(note)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(note)));

        verify(noteService, times(1)).updateNote(id, note);
    }

    @Test
    void deleteNoteTest() throws Exception {
        String id = "idTest";
        String result = "Note supprimée";
        when(noteService.deleteNote(id)).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.delete("/note/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(result));

        verify(noteService, times(1)).deleteNote(id);
    }
}

package com.mediscreen.notes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.services.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;
    @Autowired
    private ObjectMapper mapper;

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
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(note)));

        verify(noteService, times(1)).createNote(any(Note.class));
    }

    @Test
    void getNotesTest() throws Exception {
        List<Note> notes = new ArrayList<>();
        when(noteService.getNotes()).thenReturn(notes);

        mockMvc.perform(get("/note")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(notes)));

        verify(noteService, times(1)).getNotes();
    }

    @Test
    void getNoteTest() throws Exception {
        String id = "idTest";
        Note note = new Note();
        when(noteService.getById(id)).thenReturn(note);

        mockMvc.perform(get("/note/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(note)));

        verify(noteService, times(1)).getById(id);
    }

    @Test
    void getNoteTestThrowsException() throws Exception {
        String id = "someId";
        when(noteService.getById(id)).thenThrow(new NoteNotFoundException("Note not found"));

        mockMvc.perform(get("/note/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Note not found"))); // This assumes that your error message is included in the response. Adjust if your response structure is different.

    }


    @Test
    void updateNoteTest() throws Exception {
        String id = "idTest";
        Note note = new Note();
        note.setPatientId("1");
        note.setDate(LocalDate.now());
        note.setContent("content");
        note.setId("1");
        when(noteService.updateNote(id, note)).thenReturn(note);
        mockMvc.perform(MockMvcRequestBuilders.put("/note/update/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(note)));

        verify(noteService, times(1)).updateNote(id, note);
    }

    @Test
    void deleteNoteTest() throws Exception {
        String id = "idTest";
        String result = "Note supprimée";
        when(noteService.deleteNote(id)).thenReturn(result);

        mockMvc.perform(delete("/note/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(result));

        verify(noteService, times(1)).deleteNote(id);
    }

    @Test
    public void testGetNotesByPatientIdSuccess() throws Exception {
        String patientId = "12345";
        when(noteService.getNotesByPatientId(patientId)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/note/patient/" + patientId))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNoteByPatientIdSuccess() throws Exception {
        Integer patientId = 12345;
        mockMvc.perform(delete("/note/patient/" + patientId))
                .andExpect(status().isOk());
    }

}

package com.mediscreen.notes.services;

import com.mediscreen.notes.exception.NoteNotFoundException;
import com.mediscreen.notes.exception.PatientNotFoundException;
import com.mediscreen.notes.model.Note;

import java.util.List;

public interface NoteService {


    public Note createNote(Note note) throws PatientNotFoundException;

    public List<Note> getNotes();

    public Note getById(String id) throws NoteNotFoundException;


    public Note updateNote(String id, Note noteDetails) throws NoteNotFoundException, PatientNotFoundException;

    public String deleteNote(String id);

    List<Note> getNotesByPatientId(String patientId);

    void deleteNoteByPatientId(Integer patientId);
}

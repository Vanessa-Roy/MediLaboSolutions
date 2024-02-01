package com.medilabosolutions.noteService;

import com.medilabosolutions.noteService.model.Note;
import com.medilabosolutions.noteService.repository.NoteRepository;
import com.medilabosolutions.noteService.service.NoteServiceDefaultImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteServiceDefaultImpl noteService;

    @Test
    void getNotesByIdPatientShouldCallFindAllByIdPatientRepositoryTest() {
        when(noteRepository.findAllByPatientId(anyLong())).thenReturn(new ArrayList<>());

        noteService.getNotesByPatientId(anyLong());

        verify(noteRepository, Mockito.times(1)).findAllByPatientId(anyLong());
    }

    @Test
    void createNoteShouldCallSaveRepositoryTest() throws Exception {
        Note noteTest = new Note();
        when(noteRepository.save(noteTest)).thenReturn(noteTest);

        noteService.createNote(noteTest);

        verify(noteRepository, Mockito.times(1)).save(noteTest);
    }

    @Test
    void createNullNoteShouldNotCallSaveRepositoryTest() {
        Note noteTest = null;

        Exception exception = assertThrows(Exception.class, () -> noteService.createNote(noteTest));

        assertEquals("Note to create is null", exception.getMessage());
        verify(noteRepository, Mockito.never()).save(any(Note.class));
    }

    @Test
    void createNoteWithAnInternalServerErrorShouldNotCallSaveRepositoryTest() {
        Note noteTest = new Note();

        when(noteRepository.save(noteTest)).thenThrow(HttpServerErrorException.InternalServerError.class);

        assertThrows(Exception.class, () -> noteService.createNote(noteTest));
    }
}

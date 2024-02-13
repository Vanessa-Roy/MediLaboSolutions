package com.medilabosolutions.noteService;

import com.medilabosolutions.noteService.mapper.NoteMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class NoteMapperTest {

    private final NoteMapper noteMapper = new NoteMapper();

    @Test
    public void toNoteWithANullNoteDtoShouldReturnNullTest() {
        assertNull(noteMapper.toNote(null));
    }

    @Test
    public void fromNoteWithANullNoteShouldReturnNullTest() {
        assertNull(noteMapper.fromNote(null));
    }
}

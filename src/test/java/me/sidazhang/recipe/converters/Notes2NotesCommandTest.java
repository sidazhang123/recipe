package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.NotesCommand;
import me.sidazhang.recipe.models.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class Notes2NotesCommandTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String RECIPE_NOTES = "Notes";
    Notes2NotesCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new Notes2NotesCommand();
    }

    @Test
    public void convert() throws Exception {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand notesCommand = converter.convert(notes);

        //then
        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }

    @Test
    public void testNull() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Notes()));
    }
}
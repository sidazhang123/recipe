package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.NotesCommand;
import me.sidazhang.recipe.models.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommand2NotesTest {

    private static final String ID_VALUE = "1";
    private static final String RECIPE_NOTES = "Notes";
    private NotesCommand2Notes converter;

    @Before
    public void setUp() {
        converter = new NotesCommand2Notes();

    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    public void convert() {
        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes notes = converter.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }

}
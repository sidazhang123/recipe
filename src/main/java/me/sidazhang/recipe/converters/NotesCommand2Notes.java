package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.NotesCommand;
import me.sidazhang.recipe.models.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommand2Notes implements Converter<NotesCommand, Notes> {
    @Nullable
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if (notesCommand == null) {
            return null;
        }
        final Notes notes = new Notes();
        notes.setRecipeNotes(notesCommand.getRecipeNotes());
        notes.setId(notesCommand.getId());
        return notes;
    }
}

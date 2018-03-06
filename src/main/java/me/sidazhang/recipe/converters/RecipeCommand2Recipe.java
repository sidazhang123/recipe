package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.models.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommand2Recipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommand2Category categoryCommand2Category;
    private final IngredientCommand2Ingredient ingredientCommand2Ingredient;
    private final NotesCommand2Notes notesCommand2Notes;

    public RecipeCommand2Recipe(CategoryCommand2Category categoryCommand2Category,
                                IngredientCommand2Ingredient ingredientCommand2Ingredient,
                                NotesCommand2Notes notesCommand2Notes) {
        this.categoryCommand2Category = categoryCommand2Category;
        this.ingredientCommand2Ingredient = ingredientCommand2Ingredient;
        this.notesCommand2Notes = notesCommand2Notes;
    }

    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setDirection(recipeCommand.getDirection());
        recipe.setServings(recipeCommand.getServings());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setNotes(notesCommand2Notes.convert(recipeCommand.getNotes()));

        if (recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0) {
            recipeCommand.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryCommand2Category.convert(category)));
        }

        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
            recipeCommand.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientCommand2Ingredient.convert(ingredient)));
        }

        return recipe;
    }
}

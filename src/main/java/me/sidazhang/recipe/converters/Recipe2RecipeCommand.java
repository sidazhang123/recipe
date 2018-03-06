package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.models.Category;
import me.sidazhang.recipe.models.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class Recipe2RecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final Category2CategoryCommand category2CategoryCommand;
    private final Ingredient2IngredientCommand ingredient2IngredientCommand;
    private final Notes2NotesCommand notes2NotesCommand;

    public Recipe2RecipeCommand(Category2CategoryCommand category2CategoryCommand, Ingredient2IngredientCommand ingredient2IngredientCommand,
                                Notes2NotesCommand notes2NotesCommand) {
        this.category2CategoryCommand = category2CategoryCommand;
        this.ingredient2IngredientCommand = ingredient2IngredientCommand;
        this.notes2NotesCommand = notes2NotesCommand;
    }

    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setDirection(recipe.getDirection());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setNotes(notes2NotesCommand.convert(recipe.getNotes()));

        if (recipe.getCategories() != null && recipe.getCategories().size() > 0) {
            recipe.getCategories()
                    .forEach((Category category) -> recipeCommand.getCategories().add(category2CategoryCommand.convert(category)));
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0) {
            recipe.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingredient2IngredientCommand.convert(ingredient)));
        }

        return recipeCommand;
    }
}
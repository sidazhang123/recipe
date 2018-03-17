package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.models.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(String id);

    RecipeCommand createRecipe();

    void deleteById(String id);
}

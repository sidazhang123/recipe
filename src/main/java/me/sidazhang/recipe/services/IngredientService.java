package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByIngredientIdAndRecipeId(String recipeId, String ingredientId) throws Exception;

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) throws Exception;

    void deleteById(String recipeId, String ingredientId) throws Exception;

    IngredientCommand createIngredient(String recipeId);
}

package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByIngredientIdAndRecipeId(Long recipeId, Long ingredientId) throws Exception;

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) throws Exception;

    void deleteById(Long recipeId, Long ingredientId) throws Exception;
}

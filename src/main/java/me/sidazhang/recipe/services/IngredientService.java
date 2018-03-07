package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findByIngredientIdAndRecipeId(Long recipeId, Long ingredientId) throws Exception;
}

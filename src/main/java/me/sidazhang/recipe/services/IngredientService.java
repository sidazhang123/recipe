package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<IngredientCommand> findByIngredientIdAndRecipeId(String recipeId, String ingredientId) throws Exception;

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) throws Exception;

    Mono<Void> deleteById(String recipeId, String ingredientId) throws Exception;

    IngredientCommand createIngredient(String recipeId);
}

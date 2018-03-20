package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.models.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<RecipeCommand> findCommandById(String id);

    RecipeCommand createRecipe();

    Mono<Void> deleteById(String id);
}

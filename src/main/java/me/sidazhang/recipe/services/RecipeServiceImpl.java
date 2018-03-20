package me.sidazhang.recipe.services;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.converters.Recipe2RecipeCommand;
import me.sidazhang.recipe.converters.RecipeCommand2Recipe;
import me.sidazhang.recipe.exceptions.NotFoundException;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.reactive.RecipeReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommand2Recipe recipeCommand2Recipe;
    private final Recipe2RecipeCommand recipe2RecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommand2Recipe recipeCommand2Recipe, Recipe2RecipeCommand recipe2RecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipe2RecipeCommand = recipe2RecipeCommand;
        this.recipeCommand2Recipe = recipeCommand2Recipe;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        Optional<Recipe> recipeOptional = recipeReactiveRepository.findById(id).blockOptional();
        if (!recipeOptional.isPresent()) {
            throw new NotFoundException("Recipe with ID value " + id);
        }
        return Mono.just(recipeOptional.get());
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {

        return recipeReactiveRepository.save(recipeCommand2Recipe.convert(recipeCommand))
                .map(recipe2RecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeReactiveRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipe2RecipeCommand.convert(recipe);

                    recipeCommand.getIngredients().forEach(rc -> {
                        rc.setRecipeId(recipeCommand.getId());
                    });

                    return recipeCommand;
                });
    }


    @Override
    public RecipeCommand createRecipe() {
        return saveRecipeCommand(new RecipeCommand()).block();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        recipeReactiveRepository.deleteById(id).block();
        return Mono.empty();
    }
}

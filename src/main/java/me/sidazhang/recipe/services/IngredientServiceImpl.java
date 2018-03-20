package me.sidazhang.recipe.services;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.converters.Ingredient2IngredientCommand;
import me.sidazhang.recipe.converters.IngredientCommand2Ingredient;
import me.sidazhang.recipe.exceptions.NotFoundException;
import me.sidazhang.recipe.models.Ingredient;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.models.UnitOfMeasure;
import me.sidazhang.recipe.repositories.reactive.RecipeReactiveRepository;
import me.sidazhang.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
    private final Ingredient2IngredientCommand ingredient2IngredientCommand;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final IngredientCommand2Ingredient ingredientCommand2Ingredient;


    public IngredientServiceImpl(Ingredient2IngredientCommand ingredient2IngredientCommand,
                                 RecipeReactiveRepository recipeReactiveRepository,
                                 UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
                                 IngredientCommand2Ingredient ingredientCommand2Ingredient) {
        this.ingredient2IngredientCommand = ingredient2IngredientCommand;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.ingredientCommand2Ingredient = ingredientCommand2Ingredient;

    }

    @Override
    public Mono<IngredientCommand> findByIngredientIdAndRecipeId(String recipeId, String ingredientId) {
        return recipeReactiveRepository
                .findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientCommand command = ingredient2IngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });
    }


    @Override
    //models are combined in persistent layer but incoming commands are singletons,
    //and converters process them individually. So need to pick out the updated properties manually
    //and put them into the combined models
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {
        //command->RecipeID->Recipe
        log.warn(ingredientCommand.getRecipeId());
        Optional<Recipe> recipeOptional = recipeReactiveRepository.findById(ingredientCommand.getRecipeId()).blockOptional();
        if (!recipeOptional.isPresent()) {
            log.warn("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return Mono.just(new IngredientCommand());
        } else {
            // ->the ingredient(matches command.Id)
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            // -> set command.props to this ingredient  || attach this command if not exist
            if (ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setUom(unitOfMeasureReactiveRepository.findById(ingredientCommand.getUom().getId())
                        .blockOptional().orElseThrow(() ->
                                new RuntimeException("cannot find uom" + ingredientCommand.getUom().getId())));

            } else {
                log.warn("Creating new ingredient");
                Ingredient ingredient = ingredientCommand2Ingredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);

            }
            // ->save RECIPE
            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();
            // -> return find RECIPE.ingredient that matches command's id and convert to command
            Optional<Ingredient> ingredientOptional1 = savedRecipe.getIngredients().stream()
                    .filter(ingredient1 -> ingredient1.getId().equals(ingredientCommand.getId())).findFirst();
            if (!ingredientOptional1.isPresent()) {
                log.warn("recipe's ingredients do not contain command");
                ingredientOptional1 = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }
            IngredientCommand ingredientCommand1 = ingredient2IngredientCommand.convert(ingredientOptional1.get());
            ingredientCommand1.setRecipeId(recipe.getId());
            return Mono.just(ingredientCommand1);
        }
    }

    @Override
    public Mono<Void> deleteById(String recipeId, String ingredientId) {
        Optional<Recipe> optionalRecipe = recipeReactiveRepository.findById(recipeId).blockOptional();
        if (!optionalRecipe.isPresent()) {
            throw new NotFoundException("Recipe with ID value " + recipeId);
        } else {
            Recipe recipe = optionalRecipe.get();
            Optional<Ingredient> ingredient2Del = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();
            if (!ingredient2Del.isPresent()) {
                throw new NotFoundException("Ingredient with ID value " + ingredientId);
            } else {
//                ingredient2Del.get().setRecipe(null);
                recipe.getIngredients().remove(ingredient2Del.get());
                recipeReactiveRepository.save(recipe).block();
            }
        }
        return Mono.empty();
    }

    @Override
    public IngredientCommand createIngredient(String recipeId) {
        Optional<Recipe> optionalRecipe = recipeReactiveRepository.findById(recipeId).blockOptional();
        if (optionalRecipe.isPresent()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setUom(new UnitOfMeasure());
            IngredientCommand ingredientCommand = ingredient2IngredientCommand.convert(ingredient);
            ingredientCommand.setRecipeId(recipeId);
            return Mono.just(ingredientCommand).block();
        } else {
            throw new NotFoundException("Recipe with ID value " + recipeId);
        }


    }

}


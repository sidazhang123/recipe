package me.sidazhang.recipe.services;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.converters.Ingredient2IngredientCommand;
import me.sidazhang.recipe.converters.IngredientCommand2Ingredient;
import me.sidazhang.recipe.exceptions.NotFoundException;
import me.sidazhang.recipe.models.Ingredient;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.models.UnitOfMeasure;
import me.sidazhang.recipe.repositories.RecipeRepository;
import me.sidazhang.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
    private final Ingredient2IngredientCommand ingredient2IngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommand2Ingredient ingredientCommand2Ingredient;

    public IngredientServiceImpl(Ingredient2IngredientCommand ingredient2IngredientCommand,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommand2Ingredient ingredientCommand2Ingredient) {
        this.ingredient2IngredientCommand = ingredient2IngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommand2Ingredient = ingredientCommand2Ingredient;
    }

    @Override
    public IngredientCommand findByIngredientIdAndRecipeId(String recipeId, String ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {

            log.error("recipe id not found. Id: " + recipeId);
            throw new NotFoundException("Recipe with ID value " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient2IngredientCommand::convert).findFirst();

        if (!ingredientCommandOptional.isPresent()) {

            log.error("Ingredient id not found: " + ingredientId);
            throw new NotFoundException("Ingredient with ID value " + ingredientId);
        }
        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        ingredientCommand.setRecipeId(recipeId);

        return ingredientCommand;
    }


    @Override
    //models are combined in persistent layer but incoming commands are singletons,
    //and converters process them individually. So need to pick out the updated properties manually
    //and put them into the combined models
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        //command->RecipeID->Recipe
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (!recipeOptional.isPresent()) {
            log.warn("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
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
                ingredient.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("cannot find uom" + ingredientCommand.getUom().getId())));
            } else {
                log.warn("Creating new ingredient");
                Ingredient ingredient = ingredientCommand2Ingredient.convert(ingredientCommand);
                recipe.addIngredient(ingredient);

            }
            // ->save RECIPE
            Recipe savedRecipe = recipeRepository.save(recipe);
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
            return ingredientCommand1;
        }
    }

    @Override
    public void deleteById(String recipeId, String ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
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
                recipeRepository.save(recipe);
            }
        }
    }

    @Override
    public IngredientCommand createIngredient(String recipeId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if (optionalRecipe.isPresent()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setUom(new UnitOfMeasure());
            IngredientCommand ingredientCommand = ingredient2IngredientCommand.convert(ingredient);
            ingredientCommand.setRecipeId(recipeId);
            return ingredientCommand;
        } else {
            throw new NotFoundException("Recipe with ID value " + recipeId);
        }


    }

}


package me.sidazhang.recipe.services;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.converters.Ingredient2IngredientCommand;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {
    final Ingredient2IngredientCommand ingredient2IngredientCommand;
    final RecipeRepository recipeRepository;

    public IngredientServiceImpl(Ingredient2IngredientCommand ingredient2IngredientCommand,
                                 RecipeRepository recipeRepository) {
        this.ingredient2IngredientCommand = ingredient2IngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByIngredientIdAndRecipeId(Long recipeId, Long ingredientId) throws Exception {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
            throw new Exception();
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient2IngredientCommand::convert).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
            throw new Exception();
        }

        return ingredientCommandOptional.get();
    }
}

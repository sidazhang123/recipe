package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.models.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class Ingredient2IngredientCommand implements Converter<Ingredient, IngredientCommand> {
    private final UnitOfMeasure2UnitOfMeasureCommand unitOfMeasure2UnitOfMeasureCommand;

    public Ingredient2IngredientCommand(UnitOfMeasure2UnitOfMeasureCommand unitOfMeasure2UnitOfMeasureCommand) {
        this.unitOfMeasure2UnitOfMeasureCommand = unitOfMeasure2UnitOfMeasureCommand;
    }

    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setUom(unitOfMeasure2UnitOfMeasureCommand.convert(ingredient.getUom()));
        return ingredientCommand;
    }
}

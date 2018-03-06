package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.models.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommand2Ingredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommand2UnitOfMeasure unitOfMeasureCommand2UnitOfMeasure;

    public IngredientCommand2Ingredient(UnitOfMeasureCommand2UnitOfMeasure unitOfMeasureCommand2UnitOfMeasure) {
        this.unitOfMeasureCommand2UnitOfMeasure = unitOfMeasureCommand2UnitOfMeasure;
    }

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) {
            return null;
        }
        final Ingredient ingredient = new Ingredient();
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setId(ingredientCommand.getId());
        ingredient.setUom(unitOfMeasureCommand2UnitOfMeasure.convert(ingredientCommand.getUom()));
        return ingredient;
    }
}

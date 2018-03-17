package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.models.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommand2IngredientTest {


    private static final BigDecimal AMOUNT = new BigDecimal(1);
    private static final String DESCRIPTION = "Cheeseburger";
    private static final String ID_VALUE = "1";
    private static final String UOM_ID = "2";

    private IngredientCommand2Ingredient ingredientCommand2Ingredient;

    @Before
    public void setUp() {
        ingredientCommand2Ingredient = new IngredientCommand2Ingredient(new UnitOfMeasureCommand2UnitOfMeasure());
    }

    @Test
    public void testNullObject() {
        assertNull(ingredientCommand2Ingredient.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(ingredientCommand2Ingredient.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        command.setUom(unitOfMeasureCommand);

        //when
        Ingredient ingredient = ingredientCommand2Ingredient.convert(command);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUom().getId());
    }

    @Test
    public void convertWithNullUOM() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);


        //when
        Ingredient ingredient = ingredientCommand2Ingredient.convert(command);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());

    }

}
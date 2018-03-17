package me.sidazhang.recipe.converters;

import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Recipe2RecipeCommandTest {

    private static final String RECIPE_ID = "1";
    private static final Integer COOK_TIME = Integer.valueOf("5");
    private static final Integer PREP_TIME = Integer.valueOf("7");
    private static final String DESCRIPTION = "My Recipe";
    private static final String DIRECTIONS = "Directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final Integer SERVINGS = Integer.valueOf("3");
    private static final String SOURCE = "Source";
    private static final String URL = "Some URL";
    private static final String CAT_ID_1 = "1";
    private static final String CAT_ID2 = "2";
    private static final String INGRED_ID_1 = "3";
    private static final String INGRED_ID_2 = "4";
    private static final String NOTES_ID = "9";
    private Recipe2RecipeCommand recipe2RecipeCommand;

    @Before
    public void setUp() {
        recipe2RecipeCommand = new Recipe2RecipeCommand(
                new Category2CategoryCommand(),
                new Ingredient2IngredientCommand(new UnitOfMeasure2UnitOfMeasureCommand()),
                new Notes2NotesCommand());
    }

    @Test
    public void testNullObject() {
        assertNull(recipe2RecipeCommand.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(recipe2RecipeCommand.convert(new Recipe()));
    }

    @Test
    public void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirection(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        Category category = new Category();
        category.setId(CAT_ID_1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        //when
        RecipeCommand recipeCommand = recipe2RecipeCommand.convert(recipe);

        //then
        assertNotNull(recipeCommand);
        assertEquals(RECIPE_ID, recipeCommand.getId());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(DIRECTIONS, recipeCommand.getDirection());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());

    }

}
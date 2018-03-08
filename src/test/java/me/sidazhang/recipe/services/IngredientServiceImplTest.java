package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.converters.Ingredient2IngredientCommand;
import me.sidazhang.recipe.converters.IngredientCommand2Ingredient;
import me.sidazhang.recipe.converters.UnitOfMeasure2UnitOfMeasureCommand;
import me.sidazhang.recipe.converters.UnitOfMeasureCommand2UnitOfMeasure;
import me.sidazhang.recipe.models.Ingredient;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.RecipeRepository;
import me.sidazhang.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {


    @Mock
    RecipeRepository recipeRepository;

    IngredientService ingredientService;
    IngredientCommand2Ingredient ingredientCommand2Ingredient;
    Ingredient2IngredientCommand ingredient2IngredientCommand;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredient2IngredientCommand = new Ingredient2IngredientCommand(new UnitOfMeasure2UnitOfMeasureCommand());
        this.ingredientCommand2Ingredient = new IngredientCommand2Ingredient(new UnitOfMeasureCommand2UnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredient2IngredientCommand, recipeRepository,
                unitOfMeasureRepository, ingredientCommand2Ingredient);
    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    public void findByIngredientIdAndRecipeId() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByIngredientIdAndRecipeId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);


        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void testDeleteById() throws Exception {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteById(1L, 3L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}
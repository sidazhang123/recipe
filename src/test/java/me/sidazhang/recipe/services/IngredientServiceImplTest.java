package me.sidazhang.recipe.services;

import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.converters.Ingredient2IngredientCommand;
import me.sidazhang.recipe.converters.IngredientCommand2Ingredient;
import me.sidazhang.recipe.converters.UnitOfMeasure2UnitOfMeasureCommand;
import me.sidazhang.recipe.converters.UnitOfMeasureCommand2UnitOfMeasure;
import me.sidazhang.recipe.models.Ingredient;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.reactive.RecipeReactiveRepository;
import me.sidazhang.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {


    @Mock
    private RecipeReactiveRepository recipeReactiveRepository;

    private IngredientService ingredientService;
    private IngredientCommand2Ingredient ingredientCommand2Ingredient;
    private Ingredient2IngredientCommand ingredient2IngredientCommand;
    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredient2IngredientCommand = new Ingredient2IngredientCommand(new UnitOfMeasure2UnitOfMeasureCommand());
        this.ingredientCommand2Ingredient = new IngredientCommand2Ingredient(new UnitOfMeasureCommand2UnitOfMeasure());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredient2IngredientCommand, recipeReactiveRepository,
                unitOfMeasureReactiveRepository, ingredientCommand2Ingredient);
    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    public void findByIngredientIdAndRecipeId() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("1");

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.justOrEmpty(recipeOptional));

        //then
        IngredientCommand ingredientCommand = ingredientService.findByIngredientIdAndRecipeId("1", "3").block();

        //when
        assertEquals(String.valueOf("3"), ingredientCommand.getId());
        assertEquals(String.valueOf("1"), ingredientCommand.getRecipeId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("3");
        ingredientCommand.setRecipeId("2");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");


        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.justOrEmpty(recipeOptional));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand).block();

        //then
        assertEquals(String.valueOf("3"), savedCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }

    @Test
    public void testDeleteById() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Ingredient ingredient = new Ingredient();
        ingredient.setId("3");
        recipe.addIngredient(ingredient);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.justOrEmpty(recipeOptional));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

        //when
        ingredientService.deleteById("1", "3");

        //then
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }
}
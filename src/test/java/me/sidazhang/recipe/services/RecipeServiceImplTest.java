package me.sidazhang.recipe.services;

import me.sidazhang.recipe.converters.Recipe2RecipeCommand;
import me.sidazhang.recipe.converters.RecipeCommand2Recipe;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeCommand2Recipe recipeCommand2Recipe;
    @Mock
    Recipe2RecipeCommand recipe2RecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommand2Recipe, recipe2RecipeCommand);


    }

    @Test
    public void getRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        Recipe recipe1 = recipeService.findById(1L);
        assertNotNull("findById returns NULL", recipe1);
        assertEquals(recipe, recipe1);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        when(recipeRepository.findAll()).thenReturn(recipes);
        assertEquals(1, recipeService.getRecipes().size());
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void deleteById() throws Exception {
        recipeService.deleteById(2L);
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}
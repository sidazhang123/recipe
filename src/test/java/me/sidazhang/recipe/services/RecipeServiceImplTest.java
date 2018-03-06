package me.sidazhang.recipe.services;

import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);


    }

    @Test
    public void name() {
    }

    @Test
    public void getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        when(recipeRepository.findAll()).thenReturn(recipes);
        assertEquals(1, recipeService.getRecipes().size());
        verify(recipeRepository, times(1)).findAll();
    }
}
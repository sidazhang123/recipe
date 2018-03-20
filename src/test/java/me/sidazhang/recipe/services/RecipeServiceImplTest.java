package me.sidazhang.recipe.services;

import me.sidazhang.recipe.converters.Recipe2RecipeCommand;
import me.sidazhang.recipe.converters.RecipeCommand2Recipe;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.reactive.RecipeReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    private RecipeServiceImpl recipeService;
    @Mock
    private RecipeReactiveRepository recipeReactiveRepository;
    @Mock
    private RecipeCommand2Recipe recipeCommand2Recipe;
    @Mock
    private Recipe2RecipeCommand recipe2RecipeCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommand2Recipe, recipe2RecipeCommand);


    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId("1");
        Optional<Recipe> optionalRecipe = Optional.of(recipe);
        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.justOrEmpty(optionalRecipe));
        Recipe recipe1 = recipeService.findById("1").block();
        assertNotNull("findById returns NULL", recipe1);
        assertEquals(recipe, recipe1);
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        when(recipeReactiveRepository.findAll()).thenReturn(Flux.just(new Recipe()));
        assertEquals(1, recipeService.getRecipes().collectList().block().size());
        verify(recipeReactiveRepository, times(1)).findAll();
        verify(recipeReactiveRepository, never()).findById(anyString());
    }

    @Test
    public void deleteById() {
        when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());
        recipeService.deleteById("2");
        verify(recipeReactiveRepository, times(1)).deleteById(anyString());
    }
}
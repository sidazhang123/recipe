package me.sidazhang.recipe.controllers;

import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.services.IngredientService;
import me.sidazhang.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recipeService, ingredientService);

        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findByIngredientIdAndRecipeId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }
}
package me.sidazhang.recipe.controllers;

import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.services.IngredientService;
import me.sidazhang.recipe.services.RecipeService;
import me.sidazhang.recipe.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;


    private IngredientController ingredientController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);

        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));

        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findCommandById(anyString());
    }

    @Test
    public void testShowIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findByIngredientIdAndRecipeId(anyString(), anyString())).thenReturn(Mono.just(ingredientCommand));
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(ingredientService.findByIngredientIdAndRecipeId(anyString(), anyString())).thenReturn(Mono.just(ingredientCommand));
        when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");

        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(Mono.just(command));

        //then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "xxx")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }

    @Test
    public void newIngredientForm() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("1");
        when(ingredientService.createIngredient(anyString())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
        verify(ingredientService, times(1)).createIngredient(anyString());
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        when(ingredientService.deleteById(anyString(), anyString())).thenReturn(Mono.empty());
        mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));
        verify(ingredientService, times(1)).deleteById(anyString(), anyString());
    }
}
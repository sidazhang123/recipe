package me.sidazhang.recipe.controllers;

import me.sidazhang.recipe.services.IngredientService;
import me.sidazhang.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/ingredient/list";
    }

    @RequestMapping("/recipe/{id1}/ingredient/{id2}/show")
    public String listIngredients(@PathVariable Long id1, @PathVariable Long id2, Model model) throws Exception {
        model.addAttribute("ingredient", ingredientService.findByIngredientIdAndRecipeId(id1, id2));
        return "recipe/ingredient/show";
    }
}

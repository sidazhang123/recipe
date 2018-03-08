package me.sidazhang.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.services.IngredientService;
import me.sidazhang.recipe.services.RecipeService;
import me.sidazhang.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/ingredient/list";
    }

    @RequestMapping("/recipe/{id1}/ingredient/{id2}/show")
    public String showIngredient(@PathVariable Long id1, @PathVariable Long id2, Model model) throws Exception {
        model.addAttribute("ingredient", ingredientService.findByIngredientIdAndRecipeId(id1, id2));
        return "recipe/ingredient/show";
    }

    @RequestMapping("/recipe/{id1}/ingredient/{id2}/update")
    public String updateIngredient(@PathVariable Long id1, @PathVariable Long id2, Model model) throws Exception {
        model.addAttribute("ingredient", ingredientService.findByIngredientIdAndRecipeId(id1, id2));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @RequestMapping("/recipe/{id}/ingredient/new")
    public String newIngredient(@PathVariable Long id, Model model) throws Exception {
        if (recipeService.findCommandById(id) == null) {
            throw new Exception("have not found recipeId. check the url ");
        } else {
            IngredientCommand ingredientCommand = new IngredientCommand();
            ingredientCommand.setRecipeId(id);
            ingredientCommand.setUom(new UnitOfMeasureCommand());//???
            model.addAttribute("ingredient", ingredientCommand);
            model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
            return "/recipe/ingredient/ingredientform";
        }
    }

    @RequestMapping("recipe/{id1}/ingredient/{id2}/delete")
    public String deleteIngredient(@PathVariable Long id1, @PathVariable Long id2) throws Exception {
        ingredientService.deleteById(id1, id2);
        return "redirect:/recipe/" + id1.toString() + "/ingredients";
    }

    @RequestMapping(value = "recipe/{recipeId}/ingredient", method = RequestMethod.POST) //{} auto bind
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) throws Exception {
        IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved recipe id:" + ingredientCommand.getRecipeId());
        log.debug("saved ingredient id:" + ingredientCommand.getId());

        return "redirect:/recipe/" + ingredientCommand.getRecipeId() + "/ingredient/" + ingredientCommand.getId() + "/show";
    }
}

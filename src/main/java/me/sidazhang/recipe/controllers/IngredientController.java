package me.sidazhang.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.commands.IngredientCommand;
import me.sidazhang.recipe.commands.UnitOfMeasureCommand;
import me.sidazhang.recipe.services.IngredientService;
import me.sidazhang.recipe.services.RecipeService;
import me.sidazhang.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private WebDataBinder webDataBinder;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/ingredient/list";
    }

    @RequestMapping("/recipe/{id1}/ingredient/{id2}/show")
    public String showIngredient(@PathVariable String id1, @PathVariable String id2, Model model) throws Exception {
        model.addAttribute("ingredient", ingredientService.findByIngredientIdAndRecipeId(id1, id2));
        return "recipe/ingredient/show";
    }

    @RequestMapping("/recipe/{id1}/ingredient/{id2}/update")
    public String updateIngredient(@PathVariable String id1, @PathVariable String id2, Model model) throws Exception {
        model.addAttribute("ingredient", ingredientService.findByIngredientIdAndRecipeId(id1, id2).block());
        return "recipe/ingredient/ingredientform";
    }

    @RequestMapping("/recipe/{id}/ingredient/new")
    public String newIngredient(@PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.createIngredient(id));
        return "/recipe/ingredient/ingredientform";

    }

    @RequestMapping("recipe/{id1}/ingredient/{id2}/delete")
    public String deleteIngredient(@PathVariable String id1, @PathVariable String id2) throws Exception {
        ingredientService.deleteById(id1, id2).block();
        return "redirect:/recipe/" + id1 + "/ingredients";
    }

    @RequestMapping(value = "recipe/{recipeId}/ingredient", method = RequestMethod.POST)
    public String saveOrUpdate(@ModelAttribute("ingredient") IngredientCommand command, Model model) throws Exception {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.warn(objectError.toString());
            });
            return "recipe/ingredient/ingredientform";

        }
        IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command).block();

        log.warn("saved recipe id:" + ingredientCommand.getRecipeId());
        log.warn("saved ingredient id:" + ingredientCommand.getId());

        return "redirect:/recipe/" + command.getRecipeId() + "/ingredient/" + ingredientCommand.getId() + "/show";
    }

    @ModelAttribute("uomList")
    public Flux<UnitOfMeasureCommand> applyUomList() {
        return unitOfMeasureService.listAllUoms();
    }

}

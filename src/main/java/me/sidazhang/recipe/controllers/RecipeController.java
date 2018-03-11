package me.sidazhang.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String create(Model model) {
        RecipeCommand recipeCommand = recipeService.createRecipe();
        log.warn("Controller:create");
        log.warn(String.valueOf(recipeCommand == null));
        model.addAttribute("recipe", recipeCommand);
        return "recipe/recipeform";
    }

    @RequestMapping("/recipe/{id}/update")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeform";
    }

    @RequestMapping(value = "/recipe", method = RequestMethod.POST)
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand recipeCommand1 = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + recipeCommand1.getId() + "/show";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String delete(@PathVariable String id) {
        log.debug("delete id {" + id + "}");
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}

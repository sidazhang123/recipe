package me.sidazhang.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.commands.RecipeCommand;
import me.sidazhang.recipe.exceptions.NotFoundException;
import me.sidazhang.recipe.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;

@Controller
@Slf4j
public class RecipeController {
    private RecipeService recipeService;
    private WebDataBinder webDataBinder;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable("id") String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String create(Model model) {
        model.addAttribute("recipe", recipeService.createRecipe());
        return "recipe/recipeform";
    }

    @RequestMapping("/recipe/{id}/update")
    public String update(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id).block());
        return "recipe/recipeform";
    }

    @RequestMapping(value = "/recipe", method = RequestMethod.POST)
    public String saveOrUpdate(@ModelAttribute("recipe") RecipeCommand recipeCommand) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.warn(objectError.toString());
            });
            return "recipe/recipeform";
        }
        RecipeCommand recipeCommand1 = recipeService.saveRecipeCommand(recipeCommand).block();
        return "redirect:/recipe/" + recipeCommand1.getId() + "/show";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String delete(@PathVariable String id) {
        log.warn("delete id {" + id + "}");
        recipeService.deleteById(id);
        return "redirect:/";
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model) {

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);

        return "errors/404";
    }


}
